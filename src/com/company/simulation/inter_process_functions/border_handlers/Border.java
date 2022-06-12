package com.company.simulation.inter_process_functions.border_handlers;

import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.border_displacement.LinearFunction;
import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.simulation.simulation_variables.simulation_time.SimulationTimePow;
import com.company.simulation.simulation_variables.wave_front.DenoteFactor;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class Border {
    /**
     * Функция, создающая набор линейных функций воздействия на границу материала.
     *
     * @param coordinates Множество координат, где coordinates[0] = x = t, coordinates[1] = y = u
     */
    public static void initBorderDisplacementFunctions(double[][] coordinates, DenoteFactor denoteFactorU, SimulationTimePow simulationTimePow) {
        ArrayList<LinearFunction> borderDisplacementFunctions = new ArrayList<>();

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

            System.out.println("Time = " + currentT);
            System.out.println("k = " + k);
            System.out.println("b = " + currentU);

            borderDisplacementFunctions.add(new LinearFunction(k, currentU, currentT));
        }

        SimulationGlobals.setBorderDisplacementFunctions(borderDisplacementFunctions);
    }

    /**
     * Функция, возвращающая текущее смещение границы материала
     *
     * @return double Значение смещения границы материала
     */
    public static double getCurrentBorderDisplacement() {
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
     * Функция, которая проверяет, должен ли был за текущий шаг времени появиться новый волновой фронт.
     *
     * @return boolean true, если должен был, false, если не должен был
     */
    public static LinearFunction getJumpDiscontinuityFunction() {
        //Проходим по всем линейным функциям
        for (var linearFunction : SimulationGlobals.getBorderDisplacementFunctions()) {
            //Если начальное время больше времени симуляции без дельты
            if (linearFunction.startTime() >= SimulationTime.getSimulationTime() - SimulationTime.getSimulationTimeDelta()) {
                //Если начальное время меньше времени симуляции
                if (linearFunction.startTime() < SimulationTime.getSimulationTime()) {
                    return linearFunction;
                } else
                    return null;
            }
        }

        return null;
    }

    /**
     * Функция, добавляющая новый волновой фронт в волновую картину,
     * если за такт времени должно было произойти его появление
     * на границе волновой картины.
     *
     * @return WaveFront новый волновой фронт
     */
    public static LayerDescription createBorderWaveFront() {
        var linearFunction = getJumpDiscontinuityFunction();

        if (linearFunction == null)
            return null;

        var waveFrontWrapper = new ArrayList<LayerDescription>();

        waveFrontWrapper.add(new LayerDescription(
                linearFunction.b(),
                linearFunction.k(),
                -linearFunction.k(),
                linearFunction.startTime()
        ));

        if (SimulationGlobals.getCurrentWavePicture().size() != 0) {
            waveFrontWrapper.add(SimulationGlobals.getCurrentWavePicture().get(0));
        }

        var newWaveFront = BorderSwitcher.generateNewWaveFront(waveFrontWrapper);

        if (newWaveFront == null) {
            return null;
        }

        //Смещение волнового фронта в обратном направлении,
        //дабы потом вместе со всеми фронтами обработать его смещение.
        //Начальное время минус время в предыдущий момент времени.
        double deltaTime = linearFunction.startTime() -
                (SimulationTime.getSimulationTime() - SimulationTime.getSimulationTimeDelta());

        newWaveFront.setCurrentX(newWaveFront.getCurrentX() - newWaveFront.getSpeed() * deltaTime);

        return newWaveFront;
    }

}
