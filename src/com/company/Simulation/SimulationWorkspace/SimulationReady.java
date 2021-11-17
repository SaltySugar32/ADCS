package com.company.Simulation.SimulationWorkspace;

import com.company.Simulation.WaveClasses.WaveFront;

import java.util.ArrayList;

public class SimulationReady {
    public static ArrayList<WaveFront> getResult(ArrayList<WaveFront> prevWavePicture){
        ArrayList<WaveFront> wavePicture = new ArrayList<>(prevWavePicture);
        return wavePicture;
    }
}
