package com.company.server.simulation.collision.cases;

import com.company.server.simulation.collision.functions.SimpleFracture;
import com.company.server.simulation.enums.WaveType;
import com.company.server.runtime.vars.SimGlobals;
import com.company.server.simulation.collision.CollidedPairDescription;
import com.company.server.simulation.types.LayerDescription;

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
        if (collidedPair.getFirstLayer().getSpeed() == SimGlobals.getCharacteristicsSpeedCompression())
            return false;

        //Если слева - не медленный волновой фронт, то продолжаем
        if (collidedPair.getFirstLayer().getSpeed() == SimGlobals.getCharacteristicsSpeedStretching())
            return false;

        //Если слева скорость положительная, то продолжаем
        if (collidedPair.getFirstLayer().getSpeed() <= 0.0)
            return false;

        //Если скорость правого волнового фронта == скорости растяжения (быстрый волновой фронт), то продолжаем
        //Косвенно - если она положительная
        if (collidedPair.getSecondLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedStretching())
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
                WaveType.HALF_SIGNOTON
        );
        rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());

        newLayers.add(rightLayer);

        return newLayers;
    }
}
