package com.company.server.simulation.inter_process_functions;

import com.company.server.simulation.simulation_variables.SimulationTime;
import com.company.server.simulation.simulation_types.layer_description.LayerDescription;

import java.util.ArrayList;
import java.util.Comparator;

public class WavePictureComputations {

    static Comparator<LayerDescription> comparator = Comparator.comparingDouble(LayerDescription::getCurrentX);

    /**
     * Функция сортировки всего массива волновых фронтов по координате x
     */
    public static void sortCurrentWavePicture(ArrayList<LayerDescription> currentWavePicture) {
        currentWavePicture.sort(comparator);
    }

    /**
     * Функция, передвигающая все волновые фронты вперёд на скорость, помноженную на изменение времени
     */
    public static void moveWaveFronts(ArrayList<LayerDescription> currentWavePicture) {
        for (LayerDescription layerDescription : currentWavePicture) {
            layerDescription.moveWaveFront(SimulationTime.getSimulationTimeDelta());
        }
    }

}
