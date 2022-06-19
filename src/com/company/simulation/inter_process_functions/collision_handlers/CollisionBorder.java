package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class CollisionBorder {
    /**
     * Проходимся по каждому волновому фронту.
     * Если волновой фронт находится за границей материала после его перемещения, то обрабатываем
     * (но как? Скорость просто берём отрицательную?)
     * После этого проверяем на типичные столкновения
     */
    public static void checkBorderCollision(ArrayList<LayerDescription> currentWavePicture) {
        for (var layerDescription: currentWavePicture) {
            if (layerDescription.getCurrentX() > 0.0)
                return;

            if (layerDescription.getCurrentX() < 0.0) {
                //Напрямую изменяем параметры данного волнового фронта

            }
        }
    }
}