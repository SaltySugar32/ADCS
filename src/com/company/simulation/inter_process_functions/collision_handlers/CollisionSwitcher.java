package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.simulation.inter_process_functions.collision_handlers.collision_handlers_realisations.*;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class CollisionSwitcher {
    static ICollisionHandler edgeCase = new EdgeCase();
    static ICollisionHandler equalsCase = new EqualsCase();
    static ICollisionHandler commonCase = new CommonCase();

    /**
     * Функция, производящая выбор, каким образом обработать точку пересечения двух слоёв деформации
     * @param layerDescriptions один или два описания процессов в слое деформации
     * @return IWaveDisplacementHandler обработчик места перехода слоёв деформации
     */
    public static ICollisionHandler switchWaveDisplacementHandler(ArrayList<LayerDescription> layerDescriptions) {
        //Если подан на вход один волновой фронт, то создаём только
        if (layerDescriptions.size() == 1) {
            return edgeCase;
        }

        if (layerDescriptions.get(0).getA2() * layerDescriptions.get(1).getA2() < 0.0) {
            return commonCase;
        }

        return equalsCase;
    }
}
