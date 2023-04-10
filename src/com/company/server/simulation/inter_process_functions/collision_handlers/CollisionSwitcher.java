package com.company.server.simulation.inter_process_functions.collision_handlers;

import com.company.ProgramGlobals;
import com.company.server.simulation.inter_process_functions.collision_handlers.collision_handlers_realisations.*;
import com.company.server.simulation.simulation_types.layer_description.CollidedPairDescription;

import java.util.ArrayList;

public class CollisionSwitcher {

    static ArrayList<ICollisionHandler> collisionHandlers = new ArrayList<>();

    //К.О.С.Т.Ы.Л.И. - Креативно Основанная Система Творческого вЫхлопа в виде Ленивой Инициализации
    public static void initCollisionHandlers() {
        collisionHandlers.clear();
        collisionHandlers.add(new CaseFirst());
        collisionHandlers.add(new CaseSecond());
        collisionHandlers.add(new CaseThird());
        collisionHandlers.add(new CaseFourth());
    }

    /**
     * Функция, производящая выбор, каким образом обработать точку пересечения двух слоёв деформации,
     * методом перебора всех обработчиков столкновений.
     * @param collidedPair пара столкнувшихся волновых фронтов
     * @return ICollisionHandler обработчик места перехода слоёв деформации
     */
    public static ICollisionHandler switchWaveDisplacementHandler(CollidedPairDescription collidedPair) {

        for (var collisionHandler: collisionHandlers) {
            if (collisionHandler.isCorrectCase(collidedPair)) {
                if (ProgramGlobals.getLogLevel() == 2 || ProgramGlobals.getLogLevel() == 3 || ProgramGlobals.getLogLevel() == 99) {
                    System.out.println(collisionHandler.shortDescription());
                }
                return collisionHandler;
            }
        }

        return null;
    }
}

//Добавить всё в динамику
//Проход по каждому из случаев с различного рода проверками
//Если совпадает, то возвращаем, если не совпадает, то продолжаем, пока все случаи не кончатся