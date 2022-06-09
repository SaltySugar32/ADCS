package com.company.simulation.inter_process_functions;

import com.company.simulation.simulation_variables.wave_front.LayerDescription;

public class WaveFrontComputations {
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

    public static double calculatePreciseTime(LayerDescription firstLayerDescription, LayerDescription secondLayerDescription) {

        return 0;
    }
}
