package com.company.simulation.inter_process_functions.collision_handlers.collision_handler_realisations;

import com.company.simulation.inter_process_functions.collision_handlers.ICollisionHandler;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public class NullCollisionHandler implements ICollisionHandler {

    @Override
    public ArrayList<WaveFront> generateNewWaveFronts(ArrayList<WaveFront> prevWaveFronts) {
        return null;
    }
}
