package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.ProgramGlobals;
import com.company.LastError;
import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.simulation.simulation_variables.wave_front.CollidedPairDescription;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;
import java.util.Comparator;

public class Collision {
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
     * Функция, проверяющая, что в волновой картине произошло столкновение.
     *
     * @param currentWavePicture Текущая волновая картина
     */
    public static ArrayList<CollidedPairDescription> searchForCollisions(ArrayList<LayerDescription> currentWavePicture) {
        var collidedPairs = new ArrayList<CollidedPairDescription>();

        //Суть проверки состоит в следующем: если два волновых фронта,
        // располагающихся в массиве, по порядку соответствующему предыдущей волновой картине,
        // имеют координаты такие, что координата левого волнового фронта выше координаты
        // правого волнового фронта, то такие волновые фронты можно считать пересёкшимися
        if (currentWavePicture.size() > 1) {
            for (int index = 0; index < currentWavePicture.size() - 1; index++) {

                //Если два волновых фронта столкнулись, то...
                if (checkIfTwoWavesCollided(currentWavePicture.get(index),
                        currentWavePicture.get(index + 1))) {

                    //Добавляем новую оболочку для пары волновых фронтов
                    collidedPairs.add(new CollidedPairDescription());

                    //Добавляем пару волновых фронтов в оболочку
                    var collidedPair = collidedPairs.get(collidedPairs.size() - 1);
                    collidedPair.setFirstLayer(currentWavePicture.get(index));
                    collidedPair.setSecondLayer(currentWavePicture.get(index + 1));

                    //Вычисляем время произошедшего столкновения
                    //dT = (X+ - X-) / (V+ - V-)
                    double collisionDeltaTime = (collidedPair.getFirstLayer().getCurrentX()
                            - collidedPair.getSecondLayer().getCurrentX())
                            / (collidedPair.getFirstLayer().getSpeed()
                            - collidedPair.getSecondLayer().getSpeed());

                    collidedPair.setCollisionTime(
                            SimulationTime.getSimulationTime() - collisionDeltaTime
                    );

                    //Если один из волновых фронтов был создан позже,
                    // чем они могли столкнуться, то отменяем все изменения
                    if (collidedPair.getFirstLayer().getStartTime() > collidedPair.getCollisionTime()
                            || collidedPair.getSecondLayer().getStartTime() > collidedPair.getCollisionTime()) {
                        collidedPairs.remove(collidedPair);
                        continue;
                    }

                    //Вычисляем координату произошедшего столкновения
                    //X = X+ - V+ * dT
                    double collisionX = collidedPair.getFirstLayer().getCurrentX()
                            - collidedPair.getFirstLayer().getSpeed()
                            * (collidedPair.getCollisionTime());

                    collidedPair.setCollisionX(collisionX);

                }

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
     * @return ArrayList<WaveFront> Набор волновых фронтов
     */
    public static ArrayList<LayerDescription> calculateCollisions(ArrayList<LayerDescription> prevWavePicture) {
        var collisionPairs = searchForCollisions(prevWavePicture);

        if (collisionPairs.size() == 0) {
            return prevWavePicture;
        }

        var currentWavePicture = new ArrayList<>(prevWavePicture);
        var trashWaves = new ArrayList<LayerDescription>();
        var newWaves = new ArrayList<LayerDescription>();

        //Пока не находятся пары, которые могли бы столкнуться
        while (collisionPairs.size() != 0) {

            //Сортируем в порядке возрастания времени столкновения
            collisionPairs.sort(comparator);

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

            currentWavePicture = newWavePicture;
            collisionPairs = searchForCollisions(prevWavePicture);
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

