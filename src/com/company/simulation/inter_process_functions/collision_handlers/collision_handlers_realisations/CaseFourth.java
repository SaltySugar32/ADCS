package com.company.simulation.inter_process_functions.collision_handlers.collision_handlers_realisations;

import com.company.simulation.inter_process_functions.layer_generators.SimpleFracture;
import com.company.simulation.inter_process_functions.layer_generators.shock_wave.ShockWavePositive;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.CollidedPairDescription;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;

public class CaseFourth implements ICollisionHandler {
    @Override
    public String shortDescription() {
        return "Sigma+ -> gammaB+ => xiA- -> gammaA+";
    }

    @Override
    public String longDescription() {
        return "Sigma+ -> gammaB+ => xiA- -> gammaA+";
    }

    @Override
    public boolean isCorrectCase(CollidedPairDescription collidedPair) {
        //Если слева - не быстрый волновой фронт, то продолжаем
        if (collidedPair.getFirstLayer().getSpeed() == SimulationGlobals.getCharacteristicsSpeedCompression())
            return false;

        //Если слева - не медленный волновой фронт, то продолжаем
        if (collidedPair.getFirstLayer().getSpeed() == SimulationGlobals.getCharacteristicsSpeedStretching())
            return false;

        //Если слева скорость положительная, то продолжаем
        if (collidedPair.getFirstLayer().getSpeed() <= 0.0)
            return false;

        //Если скорость правого волнового фронта == скорости растяжения (быстрый волновой фронт), то продолжаем
        //Косвенно - если она положительная
        if (collidedPair.getSecondLayer().getSpeed() != SimulationGlobals.getCharacteristicsSpeedStretching())
            return false;

        //Если справа - недеформируемый слой, то продолжаем
        if (collidedPair.getThirdLayer().getA1() + collidedPair.getThirdLayer().getA2() != 0.0)
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

        var rightLayer = SimpleFracture.generateFastPositive(
                layerWrapper,
                collidedPair.getCollisionX(),
                collidedPair.getCollisionTime(),
                WaveType.HALF_SIGNOTON
        );

        newLayers.add(rightLayer);

        return newLayers;
    }
}
