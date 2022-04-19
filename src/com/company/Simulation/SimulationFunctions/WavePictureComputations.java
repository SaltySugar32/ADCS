package com.company.Simulation.SimulationFunctions;

import com.company.Simulation.SimulationVariables.SimulationGlobals;
import com.company.Simulation.SimulationVariables.SimulationTime;
import com.company.Simulation.SimulationVariables.WaveFront;

import java.util.ArrayList;
import java.util.Comparator;

public class WavePictureComputations {

    //Неверно - скорость - плохая характеристика для такого описания
    //TODO: сравнение волн
    static Comparator<WaveFront> comparator = Comparator.comparingDouble(WaveFront::getCurrentX);

    public static void sortCurrentWavePicture(ArrayList<WaveFront> currentWavePicture) {
        currentWavePicture.sort(comparator);
    }

    public static void checkCollisionsInWavePicture(ArrayList<WaveFront> currentWavePicture) {
        for (int iterator = 1; iterator < currentWavePicture.size(); iterator++) {
            if (WaveFrontComputations.checkIfTwoWavesCollided(currentWavePicture.get(iterator - 1), currentWavePicture.get(iterator))) {
                WaveFront waveFront = WaveFrontComputations.createNewWaveFront(currentWavePicture.get(iterator - 1), currentWavePicture.get(iterator));

                //TODO: вставка волнового фронта в список
            }
        }
    }

    public static boolean updateCurrentWavePicture(ArrayList<WaveFront> currentWavePicture) {
        checkCollisionsInWavePicture(currentWavePicture);
        sortCurrentWavePicture(currentWavePicture);

        return true;
    }
}
