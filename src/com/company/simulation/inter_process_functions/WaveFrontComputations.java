package com.company.simulation.inter_process_functions;

import com.company.simulation.simulation_variables.wave_front.WaveFront;

public class WaveFrontComputations {
    /**
     * Функция, возвращающая ответ на вопрос,
     * больше ли текущая координата бывшего левым волнового фронта
     * относительно бывшего правым.
     * @param firstWaveFront левый волновой фронт
     * @param secondWaveFront правый волновой фронт
     * @return boolean true, если слева координата больше, иначе false
     */
    public static boolean checkIfTwoWavesCollided(WaveFront firstWaveFront, WaveFront secondWaveFront) {
        return firstWaveFront.getCurrentX() > secondWaveFront.getCurrentX();
    }

    public static double calculatePreciseTime(WaveFront firstWaveFront, WaveFront secondWaveFront) {

        return 0;
    }
}
