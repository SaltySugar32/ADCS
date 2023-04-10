package com.company.server.functions.border_handlers;

import com.company.ProgramGlobals;
import com.company.server.enums.WaveType;
import com.company.server.functions.layer_generators.SimpleFracture;
import com.company.server.types.BorderDescription;
import com.company.server.vars.SimGlobals;
import com.company.server.vars.SimTime;
import com.company.server.types.LayerDescription;

import java.util.ArrayList;

public class BorderCollision {

    /**
     * Функция, возвращающая текущее граничное воздействие на материал
     * @return double значение текущего граничного воздействия
     */
    public static BorderDescription getCurrentBorderDescription() {
        //Проходим по всем линейным функциям
        for (var index = 1; index < SimGlobals.getBorderDisplacementFunctions().size(); index++) {

            //Если начальное время больше времени симуляции без дельты
            if (SimGlobals.getBorderDisplacementFunctions().get(index).startTime() >= SimTime.getSimulationTime()) {
                return SimGlobals.getBorderDisplacementFunctions().get(index - 1);
            }
            if (index == SimGlobals.getBorderDisplacementFunctions().size() - 1) {
                return SimGlobals.getBorderDisplacementFunctions().get(index);
            }
        }

        return null;
    }
    /**
     * Проходимся по каждому волновому фронту.
     * Если волновой фронт находится за границей материала после его перемещения, то обрабатываем
     * (но как? Скорость просто берём отрицательную?)
     * После этого проверяем на типичные столкновения
     */
    public static ArrayList<LayerDescription> checkBorderCollision(ArrayList<LayerDescription> currentWavePicture) {
        var newWavePicture = new ArrayList<>(currentWavePicture);

        for (int index = 0; index < newWavePicture.size() - 1; index++) {
            if (newWavePicture.get(index).getCurrentX() > 0.0)
                break;

            if (newWavePicture.get(index).getCurrentX() <= 0.0) {
                //Создаём оболочку столкновения
                var layerWrapper = new ArrayList<LayerDescription>();

                //Вычисляем время, в которое произошло столкновение с границей
                double deltaT = newWavePicture.get(index).getCurrentX() / newWavePicture.get(index).getSpeed();
                double collisionTime = SimTime.getSimulationTime() - deltaT;

                //Резервируем новую скорость волнового фронта
                var newSpeed = 0.0 - newWavePicture.get(index).getSpeed();

                //Берём текущее граничное воздействие
                BorderDescription currentBorderDescription = getCurrentBorderDescription();

                if (currentBorderDescription == null) {
                    return newWavePicture;
                }

                LayerDescription border = new LayerDescription(
                        currentBorderDescription.b(),
                        currentBorderDescription.k(),
                        0.0,
                        currentBorderDescription.startTime(),
                        WaveType.NULL
                );

                //Добавляем в оболочку параметры среды левее и правее нового слоя деформации
                layerWrapper.add(border);
                layerWrapper.add(newWavePicture.get(index + 1));

                //Создаём новый волновой фронт
                var newLayer = SimpleFracture.generateNewLayer(
                        layerWrapper,
                        0.0,
                        collisionTime,
                        newSpeed,
                        WaveType.SIMPLE_FRACTURE
                );

                //Устанавливаем текущую координату нового волнового фронта
                newLayer.setCurrentX((SimTime.getSimulationTimeDelta() - deltaT) * newLayer.getSpeed());

                //Напрямую изменяем параметры данного волнового фронта
                newWavePicture.set(index, newLayer);

                if (ProgramGlobals.getLogLevel() == 3 || ProgramGlobals.getLogLevel() == 99) {
                    System.out.println("-----CREATED ON BORDER------");
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
            }
        }

        return newWavePicture;
    }
}