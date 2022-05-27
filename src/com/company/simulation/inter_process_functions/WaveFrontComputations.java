package com.company.simulation.inter_process_functions;

import com.company.simulation.simulation_variables.wave_front.WaveFront;

public class WaveFrontComputations {
    public static boolean checkIfTwoWavesCollided(WaveFront firstWaveFront, WaveFront secondWaveFront) {
        return firstWaveFront.getCurrentX() > secondWaveFront.getCurrentX();
    }
}
