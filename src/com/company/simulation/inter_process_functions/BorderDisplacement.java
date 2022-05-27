package com.company.simulation.inter_process_functions;

import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.border_displacement.LinearFunction;
import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.simulation.simulation_variables.wave_front.DenoteFactor;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public class BorderDisplacement {
    /**
     * Функция, создающая набор линейных функций воздействия на границу материала.
     * @param coordinates Множество координат, где coordinates[0] = x = t, coordinates[1] = y = u
     */
    public static void initBorderDisplacementFunctions(double[][] coordinates, DenoteFactor denoteFactor) {
        ArrayList<LinearFunction> borderDisplacementFunctions = new ArrayList<>();
        borderDisplacementFunctions.add(new LinearFunction(0,0,0));

        //Берём каждый следующий после нулевого индекса индекс и на их основе генерируем последовательность
        // коэффициентов линейных функций
        //coordinates[0][index] = x = currentT
        //coordinates[1][index] = y = currentU
        for (int index = 0; index < coordinates[0].length - 1; index++) {
            var currentT = coordinates[0][index];
            var currentU = denoteFactor.toMillimeters(coordinates[1][index]);
            var endT = coordinates[0][index + 1];
            var endU = denoteFactor.toMillimeters(coordinates[1][index + 1]);

            //k = следующее значение перемещения минус значение перемещения в момент разрыва,
            // делённое на следующее время минус текущее время перегиба.
            //То есть k = (endU(endT) - currentU(currentT)) / (endT - currentT)
            var k = (endU -  currentU) / (endT - currentT);

            borderDisplacementFunctions.add(new LinearFunction(k, currentU, currentT));
        }

        SimulationGlobals.setBorderDisplacementFunctions(borderDisplacementFunctions);
    }

    /**
     * Функция, отображающая текущее смещение границы материала
     * @return double Значение смещения границы материала
     */
    public static double getCurrentBorderDisplacement() {
        for (LinearFunction linearFunction: SimulationGlobals.getBorderDisplacementFunctions()) {
            if (linearFunction.getStartTime() < SimulationTime.getSimulationTime()) {
                return linearFunction.calculateBorderDisplacement(SimulationTime.getSimulationTime());
            }
        }

        return 0;
    }

    /**
     * Функция, которая проверяет, должен ли был за текущий шаг времени появиться новый волновой фронт.
     * @return boolean true, если должен был, false, если не должен был
     */
    public static LinearFunction getJumpDiscontinuityFunction() {
        for (var linearFunction: SimulationGlobals.getBorderDisplacementFunctions()) {
            if (linearFunction.getStartTime() > SimulationTime.getSimulationTime() - SimulationTime.getSimulationTimeDelta()) {
                if (linearFunction.getStartTime() < SimulationTime.getSimulationTime()) {
                    return linearFunction;
                }
                else
                    return null;
            }
        }

        return null;
    }

    /**
     * Функция, добавляющая новый волновой фронт в волновую картину,
     * если за такт времени должно было произойти его появление
     * на границе волновой картины.
     * @return WaveFront новый волновой фронт
     */
    public static WaveFront createBorderWaveFront() {
        var linearFunction = getJumpDiscontinuityFunction();

        if (linearFunction == null)
            return null;

        var A1 = linearFunction.getK();
        var A2 = 0 - (linearFunction.getK() / linearFunction.getB());
        var A0 = 0;

        for (double time = linearFunction.getStartTime();
             time < SimulationTime.getSimulationTime();
             time += SimulationTime.getSimulationTimeHiPrecisionDelta()) {
            A0 += A1 * SimulationTime.getSimulationTimeHiPrecisionDelta();
        }
        System.out.println(A0);

        return new WaveFront(A1, A2, A0, DenoteFactor.MILLI);
    }

}
