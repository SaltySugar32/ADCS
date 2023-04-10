package com.company.server.functions.collision_handlers;

import com.company.server.enums.LastError;
import com.company.server.enums.WaveType;
import com.company.ProgramGlobals;
import com.company.server.vars.SimTime;
import com.company.server.types.CollidedPairDescription;
import com.company.server.types.LayerDescription;

import java.util.ArrayList;
import java.util.Comparator;

public class Collision {
    /**
     * Функция, сравнивающая пару слоёв деформации на основе времени их появления.
     */
    static Comparator<CollidedPairDescription> comparator = Comparator.comparingDouble(CollidedPairDescription::getCollisionTime);

    /**
     * Функция, возвращающая ответ на вопрос,
     * больше ли текущая координата бывшего левым волнового фронта
     * относительно бывшего правым.
     *
     * @param firstLayerDescription  левый волновой фронт
     * @param secondLayerDescription правый волновой фронт
     * @return boolean true, если слева координата больше, иначе false
     */
    public static boolean checkIfTwoWavesCollided(LayerDescription firstLayerDescription, LayerDescription secondLayerDescription) {
        return firstLayerDescription.getCurrentX() > secondLayerDescription.getCurrentX();
    }

    /**
     * Функция, вычисляющая, какие пары волновых фронтов столкнулись друг с другом.
     *
     * @param currentWavePicture Текущая волновая картина
     */
    public static ArrayList<CollidedPairDescription> searchForCollisions(ArrayList<LayerDescription> currentWavePicture) {
        var collidedPairs = new ArrayList<CollidedPairDescription>();

        //Суть проверки состоит в следующем: если два волновых фронта,
        // располагающихся в массиве, по порядку соответствующему предыдущей волновой картине,
        // имеют координаты такие, что координата левого волнового фронта выше координаты
        // правого волнового фронта, то такие волновые фронты можно считать пересёкшимися
        for (int index = 0; index < currentWavePicture.size() - 1; index++) {

            //Если два волновых фронта столкнулись, то...
            if (checkIfTwoWavesCollided(currentWavePicture.get(index),
                    currentWavePicture.get(index + 1))) {

                //Если появились в один момент, то отклоняем
                if (currentWavePicture.get(index).getWaveFrontStartTime() == currentWavePicture.get(index + 1).getWaveFrontStartTime()) {
                    continue;
                }

                //Добавляем новую оболочку для пары волновых фронтов
                collidedPairs.add(new CollidedPairDescription());

                //Добавляем пару волновых фронтов в оболочку
                var collidedPair = collidedPairs.get(collidedPairs.size() - 1);
                collidedPair.setFirstLayer(currentWavePicture.get(index));
                collidedPair.setSecondLayer(currentWavePicture.get(index + 1));

                //Если речь про столкновение с самым дальним волновым фронтом, то добавляем нулевое описание среды справа
                if (index == currentWavePicture.size() - 2) {
                    collidedPair.setThirdLayer(new LayerDescription(0.0, 0.0, 0.0, 0.0, WaveType.HALF_SIGNOTON));
                } else {
                    //Иначе используем существующее описание среды справа
                    collidedPair.setThirdLayer(currentWavePicture.get(index + 2));
                }

                //Вычисляем время произошедшего столкновения
                //dT = (X+ - X-) / (V- - V+)
                double collisionDeltaTime = (collidedPair.getFirstLayer().getCurrentX()
                        - collidedPair.getSecondLayer().getCurrentX())
                        / (collidedPair.getFirstLayer().getSpeed()
                        - collidedPair.getSecondLayer().getSpeed());

                collidedPair.setCollisionTime(
                        SimTime.getSimulationTime() - collisionDeltaTime
                );

                //Если один из волновых фронтов был создан позже,
                // чем они могли столкнуться, то отменяем все изменения
                if (collidedPair.getFirstLayer().getLayerStartTime() >= collidedPair.getCollisionTime()
                        || collidedPair.getSecondLayer().getLayerStartTime() >= collidedPair.getCollisionTime()) {
                    collidedPairs.remove(collidedPair);
                    continue;
                }

                //Вычисляем координату произошедшего столкновения
                //X = X+ - V+ * dT
                double collisionX = collidedPair.getFirstLayer().getCurrentX()
                        - collidedPair.getFirstLayer().getSpeed()
                        * (collisionDeltaTime);

                //Если место столкновение двух волновых фронтов находится в отрицательной области, то игнорируем
                if (collisionX < 0.0) {
                    collidedPairs.remove(collidedPair);
                    continue;
                }

                collidedPair.setCollisionX(collisionX);

            }

        }

        return collidedPairs;
    }

    /**
     * Функция, создающая новый волновой фронт в волновой картине на основе информации о слоях деформации.
     * Если алгоритм не может обработать столкновение пары волновых фронтов, то изменяется статус ошибки
     * и происходит возврат последней волновой картины.
     *
     * @param prevWavePicture набор характеристик слоёв деформации
     * @return ArrayList новая волновая картина
     */
    public static ArrayList<LayerDescription> calculateCollisions(ArrayList<LayerDescription> prevWavePicture) {
        var collisionPairs = searchForCollisions(prevWavePicture);

        if (collisionPairs.size() == 0) {
            return prevWavePicture;
        }

        var currentWavePicture = new ArrayList<>(prevWavePicture);

        //Пока не находятся пары, которые могли бы столкнуться
        while (collisionPairs.size() != 0) {
            var trashWaves = new ArrayList<LayerDescription>();
            var newWaves = new ArrayList<LayerDescription>();

            //Сортируем в порядке возрастания времени столкновения
            collisionPairs.sort(comparator);

            for (var collisionPair: collisionPairs) {
                System.out.println(collisionPair.getCollisionTime());
            }

            var newWavePicture = new ArrayList<LayerDescription>();

            for (var collisionPair : collisionPairs) {

                if (trashWaves.contains(collisionPair.getFirstLayer())
                        || trashWaves.contains(collisionPair.getSecondLayer()))
                    continue;

                var waveDisplacementType = CollisionSwitcher.switchWaveDisplacementHandler(collisionPair);

                if (waveDisplacementType == null) {
                    ProgramGlobals.setLastErrorType(LastError.ERROR_COLLISION_1);
                    ProgramGlobals.getLastErrorType().setAdditionalText(String.valueOf(collisionPair.getCollisionX()));
                    System.out.println(ProgramGlobals.getLastErrorType().getErrorText());
                    return prevWavePicture;
                }

                var newLayers = waveDisplacementType.calculateWaveFronts(collisionPair);

                //Добавляем новые волновые фронты в множество новосозданных волновых фронтов
                newWaves.addAll(newLayers);

                //Добавляем старые волновые фронты в множество волновых фронтов, подлежащих удалению
                trashWaves.add(collisionPair.getFirstLayer());
                trashWaves.add(collisionPair.getSecondLayer());
            }

            int index = 0;

            //Добавляем старые не удалённые волновые фронты и новые в одну волновую картину
            for (var newWave : newWaves) {

                while (currentWavePicture.get(index).getCurrentX() < newWave.getCurrentX()) {

                    //Если волновой фронт из удалённых, то не добавляем
                    if (trashWaves.contains(currentWavePicture.get(index))) {
                        index++;
                        continue;
                    }

                    //Добавляем старый волновой фронт
                    newWavePicture.add(currentWavePicture.get(index));
                    index++;
                }
                //Добавляем новый волновой фронт
                newWavePicture.add(newWave);
            }

            //Добавляем оставшиеся волновые фронты
            while (index < currentWavePicture.size()) {
                //Если волновой фронт из удалённых, то не добавляем
                if (trashWaves.contains(currentWavePicture.get(index))) {
                    index++;
                    continue;
                }

                //Добавляем старый волновой фронт
                newWavePicture.add(currentWavePicture.get(index));
                index++;
            }

            if (ProgramGlobals.getLogLevel() == 2 || ProgramGlobals.getLogLevel() == 99) {
                System.out.println("-----Created with collision------");
                for (var waveFront : newWavePicture) {
                    System.out.println("A0 = " + waveFront.getA0());
                    System.out.println("A1 = " + waveFront.getA1());
                    System.out.println("A2 = " + waveFront.getA2());
                    System.out.println("V = " + waveFront.getSpeed());
                    System.out.println("X = " + waveFront.getCurrentX());
                    System.out.println("T = " + waveFront.getLayerStartTime());
                    System.out.println("TW = " + waveFront.getWaveFrontStartTime());
                    System.out.println("---");
                }
                System.out.println("---------------------------------");
            }

            //Сохраняем новую волновую картину как текущую
            currentWavePicture = newWavePicture;

            //Ищем новые столкновения
            collisionPairs = searchForCollisions(currentWavePicture);
        }

        return currentWavePicture;
    }
}

// V Создаём список пар
// V Если в паре один из элементов был создан после времени столкновения, то игнорируем пару
// V Сортируем список пар
// V Пока количество всех пар не равно нулю, не заканчиваем
// V Проходим по всем парам
// V Если в паре один из элементов является удалённым, то удаляем данную пару
// V Обрабатываем столкновение для данной пары, добавляем новые волновые фронты в список новых волновых фронтов
// V Добавляем старые волновые фронты в список удаляемых
// V Конец цикла
// V Проходим по всем новосоздаваемым волновым фронтам
// V Пропускаем все волновые фронты из волновой картины, помеченные на удаление
// V Добавляем все элементы из предыдущей волновой картины, меньшие по координате, чем текущий новосоздаваемый
// V Конец цикла

