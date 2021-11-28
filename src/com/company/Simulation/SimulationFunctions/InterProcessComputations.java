package com.company.Simulation.SimulationFunctions;

import com.company.Simulation.SimulationVariables.WaveFront;

import java.util.ArrayList;

public class InterProcessComputations {

    //Основная функция для выполнения операций, происходящих внутри симуляции
    public static ArrayList<WaveFront> getResult(ArrayList<WaveFront> prevWavePicture){
        ArrayList<WaveFront> wavePicture = new ArrayList<>(prevWavePicture);
        //System.out.print("A");
        return wavePicture;
    }
}
