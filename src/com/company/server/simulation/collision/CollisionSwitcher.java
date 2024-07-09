package com.company.server.simulation.collision;

import com.company.ProgramGlobals;
import com.company.client.gui.Database.CollisionDesc;
import com.company.client.gui.Database.DBHandler;
import com.company.server.simulation.collision.cases.*;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

public class CollisionSwitcher {

    static ArrayList<ICollisionHandler> collisionHandlers = new ArrayList<>();
    //К.О.С.Т.Ы.Л.И. - Креативно Основанная Система Творческого вЫхлопа в виде Ленивой Инициализации
    public static void initCollisionHandlers_new(){
        collisionHandlers.clear();
        DBHandler.getAllCollisions();
        for (CollisionDesc col:DBHandler.collissionDescs) {
            //collisionHandlers.add(new CollisionHandler(col));
            System.out.println(col.shortDescription);
            collisionHandlers.add(new DynamicCollisionHandler(col));
        }

    }
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