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

        //Создание нового волнового фронта на границе полупространства
        var borderWaveFront = Border.createBorderWaveFront();
        if (borderWaveFront != null) {
            wavePicture.add(borderWaveFront);
        }

        WavePictureComputations.sortCurrentWavePicture(wavePicture);
        WavePictureComputations.moveWaveFronts(wavePicture);
        WavePictureComputations.checkCollisions(wavePicture);

        if (borderWaveFront != null) {
            for (var waveFront: wavePicture) {
                System.out.println("A0 = " + waveFront.getA0());
                System.out.println("A1 = " + waveFront.getA1());
                System.out.println("A2 = " + waveFront.getA2());
                System.out.println("V = " + waveFront.getSpeed());
                System.out.println("X = " + waveFront.getCurrentX());
                System.out.println("U = " + waveFront.calculateDisplacement());
                System.out.println("T = " + waveFront.getStartTime());
                System.out.println("---");
            }
            System.out.println("--------------------------------");
        }

        WavePictureComputations.sortCurrentWavePicture(wavePicture);

        return wavePicture;
    }
}
