package com.company.simulation.inter_process_functions.border_handlers;

import com.company.ProgramGlobals;
import com.company.simulation.simulation_types.enums.DenoteFactor;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_types.layer_description.BorderDescription;
import com.company.simulation.simulation_variables.SimulationTime;
import com.company.simulation.simulation_types.enums.SimulationTimePow;
import com.company.simulation.simulation_types.layer_description.LayerDescription;
import com.company.simulation.simulation_types.enums.WaveType;

import java.util.ArrayList;
import java.util.LinkedList;

public class Border {
    /**
     * Функция, создающая набор линейных функций воздействия на границу материала.
     *
     * @param coordinates Множество координат, где coordinates[0] = x = t, coordinates[1] = y = u
     */
    public static void initBorderDisplacementFunctions(double[][] coordinates, DenoteFactor denoteFactorU, SimulationTimePow simulationTimePow) {
        ArrayList<BorderDescription> borderDisplacementFunctions = new ArrayList<>();

        //Берём каждый следующий после нулевого индекса индекс и на их основе генерируем последовательность
        // коэффициентов линейных функций
        //coordinates[0][index] = x = currentT
        //coordinates[1][index] = y = currentU
        for (int index = 0; index < coordinates[0].length - 1; index++) {
            double currentT = coordinates[0][index] * simulationTimePow.getPow();
            double currentU = denoteFactorU.toMillis(coordinates[1][index]);
            double endT = coordinates[0][index + 1] * simulationTimePow.getPow();
            double endU = denoteFactorU.toMillis(coordinates[1][index + 1]);

            //k = следующее значение перемещения минус значение перемещения в момент разрыва,
            // делённое на следующее время минус текущее время перегиба.
            //То есть k = (endU(endT) - currentU(currentT)) / (endT - currentT)
            double k = (endU - currentU) / (endT - currentT);

            if (ProgramGlobals.getLogLevel() == 1) {
                System.out.println("Time = " + currentT);
                System.out.println("k = " + k);
                System.out.println("b = " + currentU);
            }

            borderDisplacementFunctions.add(new BorderDescription(k, currentU, currentT));
        }

        SimulationGlobals.setBorderDisplacementFunctions(borderDisplacementFunctions);
    }

    /**
     * Функция, возвращающая текущее смещение границы материала
     *
     * @return double Значение смещения границы материала
     */
    public static double getCurrentBorderDisplacementOld() {
        for (int index = SimulationGlobals.getBorderDisplacementFunctions().size() - 1; index >= 0; index--) {
            if (SimulationGlobals.getBorderDisplacementFunctions().get(index).startTime()
                    < SimulationTime.getSimulationTime()) {
                return SimulationGlobals.getBorderDisplacementFunctions().get(index)
                        .calculateBorderDisplacement(SimulationTime.getSimulationTime());
            }
        }

        return 0;
    }

    /**
     * Функция, возвращающая текущее граничное воздействие на материал
     * @return double значение текущего граничного воздействия
     */
    public static double getCurrentBorderDisplacement() {
        if (SimulationGlobals.getCurrentWavePicture().size() == 0) {
            return 0;
        }

        return SimulationGlobals.getCurrentWavePicture().get(0).calculateZeroDisplacement();
    }

    /**
     * Функция, которая проверяет, должен ли был за текущий шаг времени появиться новый волновой фронт.
     *
     * @return boolean true, если должен был, false, если не должен был
     */
    public static ArrayList<BorderDescription> getJumpDiscontinuityFunctions() {
        var linearFunctions = new ArrayList<BorderDescription>();

        //Проходим по всем линейным функциям
        for (var linearFunction : SimulationGlobals.getBorderDisplacementFunctions()) {
            //Если начальное время больше времени симуляции без дельты
            if (linearFunction.startTime() >= SimulationTime.getSimulationTime() - SimulationTime.getSimulationTimeDelta()) {
                //Если начальное время меньше времени симуляции
                if (linearFunction.startTime() < SimulationTime.getSimulationTime()) {
                    linearFunctions.add(linearFunction);
                } else
                    return linearFunctions;
            }
        }

        return linearFunctions;
    }

    /**
     * Функция, добавляющая множество новых волновых фронтов в волновую картину,
     * если за такт времени должно было произойти их появление
     * на границе волновой картины.
     *
     * @return WaveFront новый волновой фронт
     */
    public static ArrayList<LayerDescription> createBorderWaveFronts(ArrayList<LayerDescription> currentWavePicture) {
        //Получаем список всех возможных новых волновых фронтов,
        // которые должны появиться на границе за прошедшую дельту времени
        var linearFunctions = getJumpDiscontinuityFunctions();

        //Если новых волновых фронтов нет, то возвращаем предыдущую волновую картину
        if (linearFunctions.size() == 0)
            return currentWavePicture;

        //Создаём новую волновую картину на основе данных из старой
        var newWavePicture = new LinkedList<>(currentWavePicture);

        //Проходимся по всем волновым фронтам, которые мы должны были получить
        for (var linearFunction : linearFunctions) {
            //Оболочка пары волновых фронтов - граничное воздействие и ближайший к границе полупространства
            var waveFrontWrapper = new ArrayList<LayerDescription>();

            /*
             * Добавляем граничное воздействие
             * Здесь A2 = -k для определения знака деформации
             */
            waveFrontWrapper.add(new LayerDescription(
                    linearFunction.b(),
                    linearFunction.k(),
                    -linearFunction.k(),
                    linearFunction.startTime(),
                    WaveType.NULL
            ));

            //Если волновая картина не пуста, то добавляем в оболочку ближайший к границе полупространства
            if (newWavePicture.size() != 0) {
                waveFrontWrapper.add(newWavePicture.peek());

                if (ProgramGlobals.getLogLevel() == 1) {
                    System.out.println("PEEEEEEEEEEK");
                    //WTF WITH THIS EXCEPTIONS!?
                    System.out.println("A0 = " + newWavePicture.peek().getA0());
                    System.out.println("A1 = " + newWavePicture.peek().getA1());
                    System.out.println("A2 = " + newWavePicture.peek().getA2());
                    System.out.println("V = " + newWavePicture.peek().getSpeed());
                    System.out.println("X = " + newWavePicture.peek().getCurrentX());
                    System.out.println("U = " + newWavePicture.peek().calculateLayerDisplacement());
                    System.out.println("T = " + newWavePicture.peek().getLayerStartTime());
                    System.out.println("^^^^^^^^^^^^^");
                }
            }

            //Создаём на основе пары волновых фронтов новый волновой фронт
            var newLayerDescription = BorderSwitcher.generateNewWaveFront(waveFrontWrapper);

            //Если не создан, то повторяем цикл
            if (newLayerDescription == null)
                continue;

            //Смещение волнового фронта в обратном направлении,
            //дабы потом вместе со всеми фронтами обработать его смещение.
            //Начальное время минус время в предыдущий момент времени.
            double deltaTime = linearFunction.startTime() -
                    (SimulationTime.getSimulationTime() - SimulationTime.getSimulationTimeDelta());

            //Если создалось больше одного волнового фронта, то сначала вычисляем и пушим более быстрый
            if (newLayerDescription.size() == 2) {
                //Вычисляем координату волнового фронта до текущего шага времени
                newLayerDescription.get(1)
                        .setCurrentX(newLayerDescription.get(1).getCurrentX()
                                - newLayerDescription.get(1).getSpeed() * deltaTime);

                //Добавляем новый волновой фронт в картину мира
                newWavePicture.push(newLayerDescription.get(1));
            }

            //Вычисляем координату волнового фронта до текущего шага времени
            newLayerDescription.get(0)
                    .setCurrentX(newLayerDescription.get(0).getCurrentX()
                            - newLayerDescription.get(0).getSpeed() * deltaTime);

            //Добавляем новый волновой фронт в картину мира
            newWavePicture.push(newLayerDescription.get(0));
        }

        if (ProgramGlobals.getLogLevel() == 1 || ProgramGlobals.getLogLevel() == 99) {
            System.out.println("------Created new on border------");
            for (var waveFront : newWavePicture) {
                System.out.println("A0 = " + waveFront.getA0());
                System.out.println("A1 = " + waveFront.getA1());
                System.out.println("A2 = " + waveFront.getA2());
                System.out.println("V = " + waveFront.getSpeed());
                System.out.println("X = " + waveFront.getCurrentX());
                System.out.println("U = " + waveFront.calculateLayerDisplacement());
                System.out.println("T = " + waveFront.getLayerStartTime());
                System.out.println("---");
            }
            System.out.println("---------------------------------");
        }

        return new ArrayList<>(newWavePicture);
    }

}
