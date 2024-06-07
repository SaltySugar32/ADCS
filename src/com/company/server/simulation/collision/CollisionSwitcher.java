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

    static ArrayList<ICollisionHandler> collisionHandlers_old = new ArrayList<>();
    static ArrayList<CollisionHandler> collisionHandlers = new ArrayList<>();
    //К.О.С.Т.Ы.Л.И. - Креативно Основанная Система Творческого вЫхлопа в виде Ленивой Инициализации
    public static void initCollisionHandlers(){
        DBHandler.getAllCollisions();
        for (CollisionDesc col:DBHandler.collissionDescs) {
            collisionHandlers.add(new CollisionHandler(col));
            System.out.println(col.shortDescription);
        }

    }
    public static void initCollisionHandlers_old() {
        collisionHandlers_old.clear();
        collisionHandlers_old.add(new CaseFirst());
        collisionHandlers_old.add(new CaseSecond());
        collisionHandlers_old.add(new CaseThird());
        collisionHandlers_old.add(new CaseFourth());
    }

    /**
     * Функция, производящая выбор, каким образом обработать точку пересечения двух слоёв деформации,
     * методом перебора всех обработчиков столкновений.
     * @param collidedPair пара столкнувшихся волновых фронтов
     * @return ICollisionHandler обработчик места перехода слоёв деформации
     */
    public static CollisionHandler switchWaveDisplacementHandler(CollidedPairDescription collidedPair) {

        for (var collisionHandler: collisionHandlers) {
            if (collisionHandler.isCorrectCase(collidedPair)) {
                if (ProgramGlobals.getLogLevel() == 2 || ProgramGlobals.getLogLevel() == 3 || ProgramGlobals.getLogLevel() == 99) {
                    System.out.println(collisionHandler.collision.shortDescription);
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