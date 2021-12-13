package com.company.Simulation.SimulationFunctions;

import com.company.Simulation.SimulationVariables.SimulationGlobals;
import com.company.Simulation.SimulationVariables.WaveFront;

import java.util.ArrayList;

public class WavePictureComputations {

    public static void sortCurrentWavePicture(ArrayList<WaveFront> currentWavePicture) {
        currentWavePicture.sort(SimulationGlobals.getComparator());
    }

    public static boolean checkCurrentWavePicture(ArrayList<WaveFront> currentWavePicture) {
        sortCurrentWavePicture(currentWavePicture);
        return false;
        //Что-то тут должно происходить
    }

    public static boolean updateCurrentWavePicture(ArrayList<WaveFront> currentWavePicture) {
        if (!checkCurrentWavePicture(currentWavePicture))
            return false;
        /*The cake is a lie
        * double cake1 = WaveFrontComputations.computeCharSpeed(1);
        * double cake2 = WaveFrontComputations.computeTension(1);
        * double cake3 = WaveFrontComputations.computeNewWaveFrontSpeed(1,1,1,1,1);
        */
        return true;
    }
}
