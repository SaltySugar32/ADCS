package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.simulation.inter_process_functions.border_handlers.IBorderHandler;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;
import java.util.Collection;

public class EdgeCase implements IBorderHandler {
    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed) {

        double A1L = prevLayerDescriptions.get(0).getA1();
        double startTL = prevLayerDescriptions.get(0).getStartTime();

        //Частный случай - справа 0
        double A2i = (- A1L) / (speed);
        double A1i = (A1L + 0.0);
        double A0i = 0.0;

        LayerDescription newLayerDescription = new LayerDescription(A0i, A1i, A2i, startTL, WaveType.HALF_SIGNOTON);

        newLayerDescription.setSpeed(speed);
        var newLayers = new ArrayList<LayerDescription>();
        newLayers.add(newLayerDescription);

        return newLayers;
    }
}
