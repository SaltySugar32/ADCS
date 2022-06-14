package com.company.simulation.inter_process_functions.collision_handlers.collision_handlers_realisations;

import com.company.simulation.inter_process_functions.collision_handlers.ICollisionHandler;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class EqualsCase implements ICollisionHandler {
    @Override
    public ArrayList<LayerDescription> calculateWaveFront(ArrayList<LayerDescription> layerDescriptions) {
        return new ArrayList<>();
    }
}
