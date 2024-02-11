package com.company.server.runtime.functions;

import com.company.server.simulation.border.Border;
import com.company.server.simulation.border.BorderCollision;
import com.company.server.simulation.collision.Collision;
import com.company.server.runtime.vars.SimTime;
import com.company.server.simulation.types.LayerDescription;

import java.util.ArrayList;

public class InterProcessComputations {

    /**
     * Основная функция для выполнения операций, происходящих внутри симуляции
     * <li>Изменение текущего времени</li>
     * <li>Добавление нового волнового фронта на границе</li>
     * <li>Смещение координат</li>
     * <li>Обработка столкновений пар волновых фронтов</li>
     * <li>Обработка столкновений с границей полупространства</li>
     * <li>Сортировка волновых фронтов по координатам</li>
     */
    public static ArrayList<LayerDescription> getResult(ArrayList<LayerDescription> prevWavePicture) {

        var wavePicture = new ArrayList<>(prevWavePicture);

        //Сдвиг времени
        SimTime.nextSimulationTime();

        //Создание множества новых волновых фронтов на границе полупространства
        wavePicture = Border.createBorderWaveFronts(wavePicture);

        //Вообще говоря, сортировка мне тут не нужна
        //WavePictureComputations.sortCurrentWavePicture(wavePicture);

        //Двигаем волновые фронты
        WavePictureComputations.moveWaveFronts(wavePicture);

        //Вычисляем столкновения в волновой картине
        wavePicture = Collision.calculateCollisions(wavePicture);

        //Сортируем волновые фронты по их координатам
        //Вообще говоря, тоже должно быть бесполезно
        //WavePictureComputations.sortCurrentWavePicture(wavePicture);

        //Проверяем столкновения волновых фронтов с границей полупространства
        wavePicture = BorderCollision.checkBorderCollision(wavePicture);

        return wavePicture;
    }
}
