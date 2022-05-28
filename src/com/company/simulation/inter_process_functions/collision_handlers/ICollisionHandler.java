package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public interface ICollisionHandler {
    public ArrayList<WaveFront> generateNewWaveFronts(ArrayList<WaveFront> prevWaveFronts);
}
