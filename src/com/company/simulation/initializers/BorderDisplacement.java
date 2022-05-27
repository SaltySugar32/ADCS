package com.company.simulation.initializers;

import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.border_displacement.LinearFunction;
import com.company.simulation.simulation_variables.simulation_time.SimulationTime;

import java.util.ArrayList;

public class BorderDisplacement {
    /**
     * Функция, создающая набор линейных функций воздействия на границу материала.
     * @param coordinates Множество координат, где coordinates[0] = x = t, coordinates[1] = y = u
     * @return ArrayList of LinearFunction - Множество линейных функций
     */
    public ArrayList<LinearFunction> initBorderDisplacementFunctions(double[][] coordinates) {
        ArrayList<LinearFunction> borderDisplacementFunctions = new ArrayList<>();
        borderDisplacementFunctions.add(new LinearFunction(0,0,0));

        //Берём каждый следующий после нулевого индекса индекс и на их основе генерируем последовательность
        // коэффициентов линейных функций
        //coordinates[0] = x = endT
        //coordinates[1] = y = endU
        for (int index = 0; index < coordinates.length - 1; index++) {

            //Время - момент начала нового разрыва первого рода
            //t = currentT
            double startTime = coordinates[0][index];

            //b = текущее значение перемещения границы за всё время до нового разрыва первого рода
            //b = currentU(currentT)
            double b = borderDisplacementFunctions.get(index)
                    .calculateBorderDisplacement(startTime);

            //k = следующее значение перемещения минус значение перемещения в момент разрыва,
            // делённое на следующее время минус текущее время перегиба.
            //То есть k = (endU(endT) - currentU(currentT)) / (endT - currentT)
            double k = (coordinates[1][index + 1] - b) / (coordinates[0][index + 1] - startTime);

            borderDisplacementFunctions.add(new LinearFunction(k, b, startTime));
        }

        return borderDisplacementFunctions;
    }
}
