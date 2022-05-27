package com.company.simulation.inter_process_functions;

import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

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
