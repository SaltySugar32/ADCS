package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.simulation.inter_process_functions.collision_handlers.collision_handlers_realisations.*;
import com.company.simulation.simulation_variables.wave_front.CollidedPairDescription;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class CollisionSwitcher {
    static ICollisionHandler edgeCase = new EdgeCase();
    static ICollisionHandler equalsCase = new EqualsCase();
    static ICollisionHandler commonCase = new CommonCase();

    /**
     * Функция, производящая выбор, каким образом обработать точку пересечения двух слоёв деформации
     * @param collidedPair пара столкнувшихся волновых фронтов
     * @return ICollisionHandler обработчик места перехода слоёв деформации
     */
    public static ICollisionHandler switchWaveDisplacementHandler(CollidedPairDescription collidedPair) {


        return null;
    }
}
