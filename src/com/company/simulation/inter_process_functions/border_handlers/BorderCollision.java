package com.company.simulation.inter_process_functions.border_handlers;

import com.company.ProgramGlobals;
import com.company.simulation.inter_process_functions.layer_generators.SimpleFracture;
import com.company.simulation.simulation_variables.SimulationTime;
import com.company.simulation.simulation_types.layer_description.LayerDescription;
import com.company.simulation.simulation_types.enums.WaveType;

import java.util.ArrayList;

public class BorderCollision {
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
                double collisionTime = SimulationTime.getSimulationTime() - deltaT;

                //Резервируем новую скорость волнового фронта
                var newSpeed = 0.0 - newWavePicture.get(index).getSpeed();

                //Выставляем параметры так, чтобы это выглядело как граничное воздействие
                newWavePicture.get(index).setSpeed(0.0);
                newWavePicture.get(index).setA2(0.0);

                //Добавляем в оболочку параметры среды левее и правее нового слоя деформации
                layerWrapper.add(newWavePicture.get(index));
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
                newLayer.setCurrentX(deltaT * newLayer.getSpeed());

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
                        System.out.println("U = " + waveFront.calculateLayerDisplacement());
                        System.out.println("T = " + waveFront.getLayerStartTime());
                        System.out.println("---");
                    }
                    System.out.println("---------------------------------");
                }
            }
        }

        return newWavePicture;
    }
}