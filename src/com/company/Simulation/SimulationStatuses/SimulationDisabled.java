package com.company.Simulation.SimulationStatuses;

import com.company.Simulation.SimulationVariables.WaveFront;

import java.util.ArrayList;

public class SimulationDisabled {

    public static ArrayList<WaveFront> getResult(ArrayList<WaveFront> prevWavePicture){
        ArrayList<WaveFront> wavePicture = new ArrayList<>(prevWavePicture);
        System.out.print("Disabled");
        return wavePicture;
    }
}
