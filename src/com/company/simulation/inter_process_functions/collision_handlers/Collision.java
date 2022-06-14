package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class Collision {

    /**
     * Функция, создающая новый волновой фронт в волновой картине на основе информации о слоях деформации.
     * @param layerDescriptions набор характеристик слоёв деформации
     * @return ArrayList<WaveFront> Набор волновых фронтов
     */
    public static ArrayList<LayerDescription> calculateWavePicture(ArrayList<LayerDescription> layerDescriptions) {
        var wavePicture = new ArrayList<LayerDescription>();

        for (int index = 0; index < layerDescriptions.size(); index ++) {
            var localLairPair = new ArrayList<LayerDescription>();

            localLairPair.add(layerDescriptions.get(0));
            if (index < layerDescriptions.size() - 1)
                localLairPair.add(layerDescriptions.get(1));

            var waveDisplacementType = CollisionSwitcher.switchWaveDisplacementHandler(localLairPair);

            var newLayers = waveDisplacementType.calculateWaveFront(localLairPair);

            wavePicture.addAll(newLayers);
        }

        return wavePicture;
    }
}
