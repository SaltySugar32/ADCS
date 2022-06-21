package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.ProgramGlobals;
import com.company.simulation.inter_process_functions.layer_generators.SimpleFracture;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;
import java.util.LinkedList;

public class CollisionBorder {
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
                var layerWrapper = new ArrayList<LayerDescription>();

                var speed = 0.0 - newWavePicture.get(index).getSpeed();

                newWavePicture.get(index).setSpeed(0.0);
                newWavePicture.get(index).setA2(0.0);

                layerWrapper.add(newWavePicture.get(index));
                layerWrapper.add(newWavePicture.get(index + 1));

                var newLayer = SimpleFracture.generateNewLayer(layerWrapper, 0.0, SimulationTime.getSimulationTime(), speed, WaveType.SIMPLE_FRACTURE);
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
    //Пока примитивно
}