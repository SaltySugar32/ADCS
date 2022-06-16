package com.company.simulation.inter_process_functions;

import com.company.simulation.inter_process_functions.border_handlers.Border;
import com.company.simulation.inter_process_functions.collision_handlers.Collision;
import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class InterProcessComputations {

    /**
     * Основная функция для выполнения операций, происходящих внутри симуляции
     * <li>Изменение текущего времени</li>
     * <li>Добавление нового волнового фронта на границе</li>
     * <li>Смещение координат</li>
     * <li>Проверка столкновений (вычисление в случае такового)</li>
     * <li>Сортировка волновых фронтов по координатам</li>
     */
    public static ArrayList<LayerDescription> getResult(ArrayList<LayerDescription> prevWavePicture) {

        var wavePicture = new ArrayList<>(prevWavePicture);

        //Сдвиг времени
        SimulationTime.nextSimulationTime();

        //Создание множества новых волновых фронтов на границе полупространства
        wavePicture = Border.createBorderWaveFronts(wavePicture);

        //Вообще говоря, сортировка мне тут не нужна
        //WavePictureComputations.sortCurrentWavePicture(wavePicture);

        //Двигаем волновые фронты
        WavePictureComputations.moveWaveFronts(wavePicture);

        //Вычисляем столкновения в волновой картине
        wavePicture = Collision.calculateCollisions(wavePicture);

        //Сортируем волновые фронты по их координатам
        WavePictureComputations.sortCurrentWavePicture(wavePicture);

        return wavePicture;
    }
}
