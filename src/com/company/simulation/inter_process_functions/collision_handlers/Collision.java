package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class Collision {
    /**
     * Функция, возвращающая ответ на вопрос,
     * больше ли текущая координата бывшего левым волнового фронта
     * относительно бывшего правым.
     * @param firstLayerDescription левый волновой фронт
     * @param secondLayerDescription правый волновой фронт
     * @return boolean true, если слева координата больше, иначе false
     */
    public static boolean checkIfTwoWavesCollided(LayerDescription firstLayerDescription, LayerDescription secondLayerDescription) {
        return firstLayerDescription.getCurrentX() > secondLayerDescription.getCurrentX();
    }

    /**
     * Функция, вычисляющая точное время столкновения двух волновых фронтов.
     * @param firstLayerDescription левый волновой фронт
     * @param secondLayerDescription правый волновой фронт
     * @return double точное время столкновения
     */
    public static double calculatePreciseTime(LayerDescription firstLayerDescription, LayerDescription secondLayerDescription) {

        return 0;
    }


    /**
     * Функция, проверяющая, что в волновой картине произошло столкновение.
     * @param currentWavePicture Текущая волновая картина
     */
    public static void checkCollisions(ArrayList<LayerDescription> currentWavePicture) {

        if (currentWavePicture.size() > 1) {
            for (int index = 0; index < currentWavePicture.size() - 1; index++) {
                if (checkIfTwoWavesCollided(currentWavePicture.get(index),
                                currentWavePicture.get(index + 1))) {

                    Double collisionTime = calculatePreciseTime(currentWavePicture.get(index),
                                    currentWavePicture.get(index + 1));
                }
            }
        }

    }

    /**
     * Функция, создающая новый волновой фронт в волновой картине на основе информации о слоях деформации.
     * @param layerDescriptions набор характеристик слоёв деформации
     * @return ArrayList<WaveFront> Набор волновых фронтов
     */
    public static ArrayList<LayerDescription> calculateWavePicture(ArrayList<LayerDescription> layerDescriptions) {
        var wavePicture = new ArrayList<LayerDescription>(layerDescriptions);

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
