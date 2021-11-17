package com.company.Simulation.SimulationStatuses;

import com.company.Simulation.SimulationVariables.WaveFront;

import java.util.ArrayList;

public class SimulationInterProcess {
    public static ArrayList<WaveFront> getResult(ArrayList<WaveFront> prevWavePicture){
        ArrayList<WaveFront> wavePicture = new ArrayList<>(prevWavePicture);
        System.out.print("A");
        return wavePicture;
    }
}
