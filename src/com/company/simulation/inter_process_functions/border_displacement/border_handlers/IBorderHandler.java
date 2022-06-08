package com.company.simulation.inter_process_functions.border_displacement.border_handlers;

import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public interface IBorderHandler {
    WaveFront generateNewWaveFront(ArrayList<WaveFront> prevWaveFronts, double speed);
}
