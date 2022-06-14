package com.company.simulation.inter_process_functions;

import com.company.simulation.inter_process_functions.border_handlers.Border;
import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

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
    public static ArrayList<LayerDescription> getResult(ArrayList<LayerDescription> prevWavePicture) {

        var wavePicture = new ArrayList<>(prevWavePicture);

        //Сдвиг времени
        SimulationTime.nextSimulationTime();

        //Создание множества новых волновых фронтов на границе полупространства
        wavePicture = Border.createBorderWaveFronts(wavePicture);

        //А нужно ли?
        WavePictureComputations.sortCurrentWavePicture(wavePicture);

        WavePictureComputations.moveWaveFronts(wavePicture);
        WavePictureComputations.checkCollisions(wavePicture);

        WavePictureComputations.sortCurrentWavePicture(wavePicture);

        return wavePicture;
    }
}
