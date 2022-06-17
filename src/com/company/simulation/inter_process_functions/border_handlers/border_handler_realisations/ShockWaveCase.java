package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.simulation.inter_process_functions.border_handlers.IBorderHandler;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.DenoteFactor;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;

public class ShockWaveCase implements IBorderHandler {

    double a = DenoteFactor.METERS.toMillis(SimulationGlobals.getCharacteristicsSpeedCompression());
    double b = DenoteFactor.METERS.toMillis(SimulationGlobals.getCharacteristicsSpeedStretching());

    /**
     * Вычисление скорости
     */
    double computeSpeed(double R, double prevK) {
        return Math.sqrt(
                Math.pow(a, 2) - (Math.pow(a, 2) - Math.pow(b, 2))
                        * (prevK / b) / (prevK / b - R / a)
        );
    }

    double computeR(double prevK, double nextK) {
        double left = (a / b + b / a) * prevK;
        double rightFirst = Math.pow(((a / b + b / a) * prevK), 2);
        double rightSecond = 4 * nextK * (2 * prevK - nextK);
        double right = Math.sqrt(rightFirst - rightSecond);

        System.out.println("prevK = " + prevK + " nextK = " + nextK);
        System.out.println("left-left = " + left);
        System.out.println("left = " + rightFirst + " right = " + rightSecond);
        System.out.println("right-right = " + right);

        return left - right;
    }

    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double unused) {

        double startTL = prevLayerDescriptions.get(0).getStartTime();
        double startTR = prevLayerDescriptions.get(1).getStartTime();

        double A0R = prevLayerDescriptions.get(1).getA0();
        double A1R = prevLayerDescriptions.get(1).getA1();
        double A2R = prevLayerDescriptions.get(1).getA2();

        double A1L = prevLayerDescriptions.get(0).getA1();

        //k2 == prevK - состояние среды справа
        //k3 == nextK - состояние среды слева
        double R = computeR(A1R, A1L);

        double speedR = computeSpeed(R, A1R);

        System.out.println(a);
        System.out.println(b);
        System.out.println(R);

        //Частный случай при CL = 0, xL = 0, Xi = 0
        double A2i = 0.0 - R / a;
        double A1i = A1R * (1 - speedR / b) + R * speedR / a;
        double A0i = A0R + A1R * (startTL - startTR);

        LayerDescription newLayerDescription = new LayerDescription(A0i, A1i, A2i, startTL, WaveType.SHOCK_WAVE);

        newLayerDescription.setSpeed(speedR);
        var newLayers = new ArrayList<LayerDescription>();
        newLayers.add(newLayerDescription);

        return newLayers;
    }
}
