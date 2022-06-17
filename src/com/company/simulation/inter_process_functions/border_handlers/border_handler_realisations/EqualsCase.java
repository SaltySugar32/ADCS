package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.simulation.inter_process_functions.border_handlers.IBorderHandler;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;

public class EqualsCase implements IBorderHandler {
    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed) {

        //double speedR = prevLayerDescriptions.get(1).getSpeed();
        double speedR = speed + 0.0;

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

        LayerDescription newLayerDescription = new LayerDescription(A0i, A1i, A2i, startTL, WaveType.SIGNOTON);

        newLayerDescription.setSpeed(speedR);
        var newLayers = new ArrayList<LayerDescription>();
        newLayers.add(newLayerDescription);

        return newLayers;
    }
}
