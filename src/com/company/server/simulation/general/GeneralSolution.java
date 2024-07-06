package com.company.server.simulation.general;

import com.company.ProgramGlobals;
import com.company.client.gui.DataHandler;
import com.company.client.gui.GUIGlobals;
import com.company.server.runtime.enums.DenoteFactor;
import com.company.server.runtime.enums.SimulationTimePow;
import com.company.server.runtime.vars.SimGlobals;
import com.company.server.simulation.border.BorderDescription;
import com.company.server.simulation.types.LayerDescription;

import java.util.ArrayList;

// Общее решение задачи
public class GeneralSolution {
    private ArrayList<BorderDescription> borderDisplacementFunctions;
    private ArrayList<LayerDescription> layerDescriptions;

    GeneralSolution(){
        SimulationTimePow simulationTimePow;
        switch (DataHandler.unitOfTime){
            case "мкс" -> simulationTimePow = SimulationTimePow.MICROSECONDS;
            case "нс" -> simulationTimePow = SimulationTimePow.NANOSECONDS;
            default -> simulationTimePow = SimulationTimePow.MILLISECONDS;
        }

        this.borderDisplacementFunctions = initBorderDisplacementFunctions(DataHandler.lin_appr_array, DenoteFactor.MILLI, simulationTimePow);
    }

    private ArrayList<BorderDescription> initBorderDisplacementFunctions(double[][] coordinates, DenoteFactor denoteFactorU, SimulationTimePow simulationTimePow) {
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

            borderDisplacementFunctions.add(new BorderDescription(k, currentU, currentT));
        }

        return borderDisplacementFunctions;
    }
}
