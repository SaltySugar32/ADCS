package com.company.simulation.inter_process_functions;

import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;
import java.util.Comparator;

public class WavePictureComputations {

    static Comparator<WaveFront> comparator = Comparator.comparingDouble(WaveFront::getCurrentX);

    public static void sortCurrentWavePicture(ArrayList<WaveFront> currentWavePicture) {
        currentWavePicture.sort(comparator);
    }

    public static void moveWaveFronts(ArrayList<WaveFront> currentWavePicture) {
        for (WaveFront waveFront: currentWavePicture) {
            //Устанавливаем смещение относительно текущей координаты + скорости волнового фронта
            waveFront.setCurrentX(waveFront.getCurrentX() + waveFront.getA1() * SimulationTime.getSimulationTimeDelta());
        }
    }

    public static void checkCollisions(ArrayList<WaveFront> currentWavePicture) {

    }
}
