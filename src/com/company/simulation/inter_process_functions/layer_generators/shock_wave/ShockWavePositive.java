package com.company.simulation.inter_process_functions.layer_generators.shock_wave;

import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;

public class ShockWavePositive {
    public static LayerDescription generatePositiveShockWave(ArrayList<LayerDescription> prevLayerDescriptions, WaveType waveType) {
        double speedR = (SimulationGlobals.getCharacteristicsSpeedCompression()
                + SimulationGlobals.getCharacteristicsSpeedStretching()) / 2;

        return generateNewLayer(prevLayerDescriptions, speedR, waveType);
    }

    public static LayerDescription generateNewLayer(ArrayList<LayerDescription> prevLayerDescriptions, double speedR, WaveType waveType) {

        double startTL = prevLayerDescriptions.get(0).getStartTime();
        double startTR = prevLayerDescriptions.get(1).getStartTime();

        double A0L = prevLayerDescriptions.get(0).getA0();
        double A1L = prevLayerDescriptions.get(0).getA1();
        double A2L = 0.0 - prevLayerDescriptions.get(0).getA1() / speedR;

        double A0R = prevLayerDescriptions.get(1).getA0();
        double A1R = prevLayerDescriptions.get(1).getA1();
        double A2R = prevLayerDescriptions.get(1).getA2();

        //Частный случай при CL = 0, xL = 0, Xi = 0
        double A2i = (A1R + A2R * speedR - A1L) / (speedR);
        double A1i = (A1L + 0.0);
        double A0i = A0R + A1R * (startTL - startTR);

        LayerDescription newLayerDescription = new LayerDescription(A0i, A1i, A2i, startTL, waveType);

        newLayerDescription.setSpeed(speedR);

        return newLayerDescription;
    }
}
