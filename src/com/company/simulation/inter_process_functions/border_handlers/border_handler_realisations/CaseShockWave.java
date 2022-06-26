package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_types.layer_description.LayerDescription;
import com.company.simulation.simulation_types.enums.WaveType;

import java.util.ArrayList;

public class CaseShockWave implements IBorderHandler {

    double a = SimulationGlobals.getCharacteristicsSpeedCompression();
    double b = SimulationGlobals.getCharacteristicsSpeedStretching();

    /**
     * Вычисление скорости
     */
    double computeSpeedOld(double R, double prevK) {
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

        return left + right;
    }

    public ArrayList<LayerDescription> generateNewWaveFrontOld(ArrayList<LayerDescription> prevLayerDescriptions, double unused) {

        double startTL = prevLayerDescriptions.get(0).getLayerStartTime();
        double startTR = prevLayerDescriptions.get(1).getLayerStartTime();

        double A0R = prevLayerDescriptions.get(1).getA0();
        double A1R = prevLayerDescriptions.get(1).getA1();
        double A2R = prevLayerDescriptions.get(1).getA2();

        double A1L = prevLayerDescriptions.get(0).getA1();

        //k2 == prevK - состояние среды справа
        //k3 == nextK - состояние среды слева
        double R = computeR(A1R, A1L);

        double speedR = computeSpeedOld(R, A1R);

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

    double computeSpeed(double k1, double k2) {
        double left = (Math.pow(a, 2) - Math.pow(b, 2)) * k1;

        double rightFirst = Math.pow((Math.pow(a, 2) + Math.pow(b, 2)) * k1, 2);
        double rightSecond = 4 * Math.pow(a, 2) * Math.pow(b, 2) * k2 * (k2 - 2 * k1);
        double right = Math.sqrt(rightFirst + rightSecond);

        double upFirst = (left + right) / (2 * b * (k2 - k1));
        double upSecond = (left - right) / (2 * b * (k2 - k1));

        //System.out.println(" --- " + upFirst);
        //System.out.println(" --- " + upSecond);

        return upFirst;
    }

    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double unused) {

        double startTL = prevLayerDescriptions.get(0).getLayerStartTime();
        double startTR = prevLayerDescriptions.get(1).getLayerStartTime();

        double A0L = prevLayerDescriptions.get(0).getA0();
        double A1L = prevLayerDescriptions.get(0).getA1();
        double A2L = prevLayerDescriptions.get(0).getA2();
        double speedL = prevLayerDescriptions.get(0).getSpeed();

        double A0R = prevLayerDescriptions.get(1).getA0();
        double A1R = prevLayerDescriptions.get(1).getA1();
        double A2R = prevLayerDescriptions.get(1).getA2();

        double speedR = computeSpeed(A1R, A1L);
        double currentX = 0.0;
        WaveType waveType = WaveType.SHOCK_WAVE;

        double A2i = (A1R + A2R * speedR - A1L + A2L * speedL) / (speedR - speedL);
        double A1i = (A1L + A2L * speedL - A2i * speedL);
        double A0i = A0R + A1R * (startTL - startTR) - A2i * currentX;

        LayerDescription newLayerDescription = new LayerDescription(A0i, A1i, A2i, startTL, waveType);

        newLayerDescription.setCurrentX(currentX);
        newLayerDescription.setSpeed(speedR);

        var newLayers = new ArrayList<LayerDescription>();
        newLayers.add(newLayerDescription);

        return newLayers;
    }
}
