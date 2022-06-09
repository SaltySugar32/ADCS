package com.company.simulation.inter_process_functions.collision_handlers.collision_handlers_realisations;

import com.company.simulation.inter_process_functions.collision_handlers.ICollisionHandler;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public class EqualsCase implements ICollisionHandler {
    @Override
    public WaveFront calculateWaveFront(ArrayList<LayerDescription> layerDescriptions) {
        return new WaveFront(layerDescriptions.get(0).getCurrentX(), 0.0);
    }
}
