package com.company.simulation.inter_process_functions;

import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;
import java.util.Comparator;

public class WavePictureComputations {

    static Comparator<LayerDescription> comparator = Comparator.comparingDouble(LayerDescription::getCurrentX);

    public static void sortCurrentWavePicture(ArrayList<LayerDescription> currentWavePicture) {
        currentWavePicture.sort(comparator);
    }

    public static void moveWaveFronts(ArrayList<LayerDescription> currentWavePicture) {
        for (LayerDescription layerDescription : currentWavePicture) {
            layerDescription.moveWaveFront(SimulationTime.getSimulationTimeDelta());
        }
    }

    public static void checkCollisions(ArrayList<LayerDescription> currentWavePicture) {

        if (currentWavePicture.size() > 1) {
            for (int index = 0; index < currentWavePicture.size() - 1; index++) {
                if (WaveFrontComputations
                        .checkIfTwoWavesCollided(currentWavePicture.get(index),
                                currentWavePicture.get(index + 1))) {

                    Double collisionTime = WaveFrontComputations
                            .calculatePreciseTime(currentWavePicture.get(index),
                                    currentWavePicture.get(index + 1));
                }
            }
        }

    }

}
