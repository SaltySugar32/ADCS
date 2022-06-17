package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.simulation.inter_process_functions.collision_handlers.collision_handlers_realisations.*;
import com.company.simulation.simulation_variables.wave_front.CollidedPairDescription;

import java.util.ArrayList;

public class CollisionSwitcher {

    static ArrayList<ICollisionHandler> collisionHandlers = new ArrayList<>();

    public static void initCollisionHandlers() {
        collisionHandlers.clear();
        collisionHandlers.add(new FirstCase());
        collisionHandlers.add(new SecondCase());
        collisionHandlers.add(new ThirdCase());
    }

    /**
     * Функция, производящая выбор, каким образом обработать точку пересечения двух слоёв деформации
     * @param collidedPair пара столкнувшихся волновых фронтов
     * @return ICollisionHandler обработчик места перехода слоёв деформации
     */
    public static ICollisionHandler switchWaveDisplacementHandler(CollidedPairDescription collidedPair) {

        for (var collisionHandler: collisionHandlers) {
            if (collisionHandler.isCorrectCase(collidedPair)) {
                return collisionHandler;
            }
        }

        return null;
    }
}

//Добавить всё в динамику
//Проход по каждому из случаев с различного рода проверками
//Если совпадает, то возвращаем, если не совпадает, то продолжаем, пока все случаи не кончатся