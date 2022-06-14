package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.simulation.inter_process_functions.border_handlers.IBorderHandler;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;

public class StopCase implements IBorderHandler {

    @Override
    public LayerDescription generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed) {
        var layerDescription = new LayerDescription(prevLayerDescriptions.get(0).getA0(), 0.0, 0.0, prevLayerDescriptions.get(0).getStartTime(), WaveType.GREEN);
        layerDescription.setSpeed(speed);
        return layerDescription;
    }
}
