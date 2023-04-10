package com.company.server.functions.collision_handlers.collision_handlers_realization;

import com.company.server.enums.WaveType;
import com.company.server.functions.layer_generators.SimpleFracture;
import com.company.server.vars.SimGlobals;
import com.company.server.types.CollidedPairDescription;
import com.company.server.types.LayerDescription;

import java.util.ArrayList;

public class CaseThird implements ICollisionHandler {
    @Override
    public String shortDescription() {
        return "xiA- -> xiA+ => xiA- -> xiA+";
    }

    @Override
    public String longDescription() {
        return "xiA- -> xiA+ => xiA- -> xiA+";
    }

    @Override
    public boolean isCorrectCase(CollidedPairDescription collidedPair) {
        //Если слева - быстрый волновой фронт, то продолжаем
        if (collidedPair.getFirstLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedCompression())
            return false;

        //Если слева - деформируемый слой, то продолжаем
        if (collidedPair.getFirstLayer().getA1() + collidedPair.getFirstLayer().getA2() == 0.0)
            return false;

        //Если скорость правого волнового фронта == скорости растяжения (быстрый волновой фронт), то продолжаем
        //Косвенно - если она положительная
        if (collidedPair.getSecondLayer().getSpeed() != 0.0 - SimGlobals.getCharacteristicsSpeedCompression())
            return false;

        //Если справа - деформируемый слой, то продолжаем
        if (collidedPair.getThirdLayer().getA1() + collidedPair.getThirdLayer().getA2() == 0.0)
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
        newLayers.get(0).setSpeed(0.0 - SimGlobals.getCharacteristicsSpeedCompression());
        newLayers.get(0).setWaveFrontStartTime(collidedPair.getCollisionTime());
        newLayers.get(0).setCurrentX(collidedPair.getCollisionX() + collidedPair.getDeltaTime() * newLayers.get(0).getSpeed());
        newLayers.get(0).setWaveType(WaveType.SIMPLE_FRACTURE);

        //--------ПРАВЫЙ ВОЛНОВОЙ ФРОНТ---------
        //--------СЛОЙ ДЕФОРМАЦИИ ПО ЦЕНТРУ--------

        layerWrapper.add(collidedPair.getFirstLayer());
        layerWrapper.add(collidedPair.getThirdLayer());

        var rightLayer = SimpleFracture.generateFastPositive(
                layerWrapper,
                collidedPair.getCollisionX(),
                collidedPair.getCollisionTime(),
                WaveType.SIMPLE_FRACTURE
        );
        rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());

        newLayers.add(rightLayer);

        return newLayers;
    }
}
