package com.company.simulation.inter_process_functions.collision_handlers.collision_handlers_realisations;

import com.company.simulation.inter_process_functions.layer_generators.shock_wave.ShockWavePositive;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.CollidedPairDescription;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;

public class CaseSecond implements ICollisionHandler {
    @Override
    public String shortDescription() {
        return "xiA+ -> Sigma+ => xiA- -> Sigma+";
    }

    @Override
    public String longDescription() {
        return "xiA+ -> Sigma+ => xiA- -> Sigma+";
    }

    @Override
    public boolean isCorrectCase(CollidedPairDescription collidedPair) {
        //Если скорость левого волнового фронта == скорости сжатия (быстрый волновой фронт), то продолжаем
        //Косвенно - если она положительная
        if (collidedPair.getFirstLayer().getSpeed() != SimulationGlobals.getCharacteristicsSpeedCompression())
            return false;

        //Если слева - деформируемый слой, то продолжаем
        if (collidedPair.getFirstLayer().getA1() + collidedPair.getFirstLayer().getA2() == 0.0)
            return false;

        //Если справа - не быстрый волновой фронт, то продолжаем
        if (collidedPair.getSecondLayer().getSpeed() == SimulationGlobals.getCharacteristicsSpeedCompression())
            return false;

        //Если справа - не медленный волновой фронт, то продолжаем
        if (collidedPair.getSecondLayer().getSpeed() == SimulationGlobals.getCharacteristicsSpeedStretching())
            return false;

        //Если справа скорость положительная, то продолжаем
        if (collidedPair.getSecondLayer().getSpeed() <= 0.0)
            return false;

        return true;
    }

    @Override
    public ArrayList<LayerDescription> calculateWaveFronts(CollidedPairDescription collidedPair) {
        //Результат операций
        var newLayers = new ArrayList<LayerDescription>();

        //Оболочка среды для создаваемых волновых фронтов
        var layerWrapper = new ArrayList<LayerDescription>();

        //--------ЛЕВЫЙ ВОЛНОВОЙ ФРОНТ--------

        newLayers.add(collidedPair.getFirstLayer());
        newLayers.get(0).setSpeed(0.0 - SimulationGlobals.getCharacteristicsSpeedCompression());
        newLayers.get(0).setWaveFrontStartTime(collidedPair.getCollisionTime());
        newLayers.get(0).setCurrentX(collidedPair.getCollisionX());
        newLayers.get(0).setWaveType(WaveType.SIMPLE_FRACTURE);

        //--------ПРАВЫЙ ВОЛНОВОЙ ФРОНТ---------
        //--------СЛОЙ ДЕФОРМАЦИИ ПО ЦЕНТРУ--------

        layerWrapper.add(collidedPair.getFirstLayer());
        layerWrapper.add(collidedPair.getThirdLayer());

        var rightLayer = ShockWavePositive.generatePositiveShockWave(
                layerWrapper,
                collidedPair.getCollisionX(),
                collidedPair.getCollisionTime(),
                Math.abs(collidedPair.getFirstLayer().getSpeed()),
                WaveType.SHOCK_WAVE);

        newLayers.add(rightLayer);

        return newLayers;
    }
}
