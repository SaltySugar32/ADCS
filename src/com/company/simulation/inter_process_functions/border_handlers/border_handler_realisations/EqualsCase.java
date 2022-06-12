package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.simulation.inter_process_functions.border_handlers.IBorderHandler;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class EqualsCase implements IBorderHandler {
    @Override
    public LayerDescription generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed) {

        double A0L = prevLayerDescriptions.get(0).getA0();
        double A1L = prevLayerDescriptions.get(0).getA1();
        double A2L = 0.0 - prevLayerDescriptions.get(0).getA1() / speed;
        double startTL = prevLayerDescriptions.get(0).getStartTime();

        double A0R = prevLayerDescriptions.get(1).getA0();
        double A1R = prevLayerDescriptions.get(1).getA1();
        double A2R = prevLayerDescriptions.get(1).getA2();
        double startTR = prevLayerDescriptions.get(1).getStartTime();

        //Частный случай при CL = 0, xL = 0, Xi = 0
        double A2i = (A1R + A2R * speed - A1L) / (speed);
        double A1i = (A1L + 0.0);
        double A0i = A0R + A1R * (startTL - startTR);

        LayerDescription newLayerDescription = new LayerDescription(A0i, A1i, A2i, startTL);

        newLayerDescription.setSpeed(speed);

        return newLayerDescription;
    }
}
