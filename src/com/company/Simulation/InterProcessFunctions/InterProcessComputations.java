package com.company.Simulation.InterProcessFunctions;

import com.company.Simulation.SimulationVariables.SimulationTime;
import com.company.Simulation.SimulationVariables.WaveFront;

import java.util.ArrayList;

public class InterProcessComputations {

    /**
     * Основная функция для выполнения операций, происходящих внутри симуляции
     * <li>Изменение текущего времени</li>
     * <li>Обновление координат</li>
     * <li>Проверка столкновений (вычисление в случае такового)</li>
     */
    public static ArrayList<WaveFront> getResult(ArrayList<WaveFront> prevWavePicture) {
        ArrayList<WaveFront> wavePicture = new ArrayList<>(prevWavePicture);
        SimulationTime.nextSimulationTime();
        WavePictureComputations.moveWaveFronts(wavePicture);
        WavePictureComputations.checkCollisions(wavePicture);
        //WavePictureComputations.sortCurrentWavePicture(wavePicture);

        return wavePicture;
    }
}
