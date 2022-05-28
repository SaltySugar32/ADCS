package com.company.simulation.inter_process_functions;

import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public class InterProcessComputations {

    /**
     * Основная функция для выполнения операций, происходящих внутри симуляции
     * <li>Добавление нового волнового фронта</li>
     * <li>Обновление координат</li>
     * <li>Изменение текущего времени</li>
     * <li>Проверка столкновений (вычисление в случае такового)</li>
     * <li>Обновление координат</li>
     */
    public static ArrayList<WaveFront> getResult(ArrayList<WaveFront> prevWavePicture) {

        var wavePicture = new ArrayList<>(prevWavePicture);

        //Сдвиг времени
        SimulationTime.nextSimulationTime();

        //Создание нового волнового фронта на границе полупространства
        var borderWaveFront = BorderDisplacement.createBorderWaveFront();
        if (borderWaveFront != null) {
            wavePicture.add(borderWaveFront);
            for (var waveFront: SimulationGlobals.getCurrentWavePicture()) {
                System.out.println(waveFront);
            }
            System.out.println("--------------------------------");
        }
        WavePictureComputations.sortCurrentWavePicture(wavePicture);
        WavePictureComputations.moveWaveFronts(wavePicture);
        WavePictureComputations.checkCollisions(wavePicture);

        WavePictureComputations.sortCurrentWavePicture(wavePicture);

        return wavePicture;
    }
}
