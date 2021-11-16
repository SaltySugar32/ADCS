package com.company.Simulation.SimulationWorkspace;

import com.company.WaveClasses.WaveFront;

import java.util.ArrayList;

public class SimulationInterProcess {
    public static ArrayList<WaveFront> getResult(ArrayList<WaveFront> prevWavePicture){
        ArrayList<WaveFront> wavePicture = new ArrayList();
        prevWavePicture.forEach((item) -> {wavePicture.add(item);});
        return wavePicture;
    }
}
