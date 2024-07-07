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

    public GeneralSolution(){
        SimulationTimePow simulationTimePow = SimulationTimePow.MILLISECONDS;

        this.borderDisplacementFunctions = initBorderDisplacementFunctions(testCoordinates, DenoteFactor.MILLI, simulationTimePow);

        for(BorderDescription bd: borderDisplacementFunctions)
            System.out.println(bd);

        // look createBorderWaveFronts in Border

        /*
        System.out.println("Wave Fronts: ");
        for (LayerDescription ld : layerDescriptions) {
            System.out.println(ld);
        }
         */
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

    private double[][] testCoordinates = {
            {0.0, 94.32387312186978, 267.1118530884808, 399.8330550918197, 563.4390651085141},
            {0.0, -1.6730401529636714, -3.068833652007648, -1.5200764818355639, 1.137667304015296}
    };
}
