package com.company.Simulation.InterProcessFunctions;

import com.company.Simulation.SimulationVariables.WaveFront;

public class WaveFrontComputations {
    public static boolean checkIfTwoWavesCollided(WaveFront firstWaveFront, WaveFront secondWaveFront) {
        return firstWaveFront.getCurrentX() > secondWaveFront.getCurrentX();
    }
}
