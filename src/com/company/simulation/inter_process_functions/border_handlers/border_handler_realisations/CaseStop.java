package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;

public class CaseStop implements IBorderHandler {

    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed) {
        var newLayerDescription = new LayerDescription(prevLayerDescriptions.get(0).getA0(), 0.0, 0.0,
                prevLayerDescriptions.get(0).getLayerStartTime(), WaveType.HALF_SIGNOTON);
        newLayerDescription.setSpeed(speed);

        var newLayers = new ArrayList<LayerDescription>();
        newLayers.add(newLayerDescription);

        return newLayers;
    }
}
