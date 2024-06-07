package com.company.server.simulation.collision.cases;

import com.company.client.gui.Database.CollisionDesc;
import com.company.server.runtime.vars.SimGlobals;
import com.company.server.simulation.collision.CollidedPairDescription;
import com.company.server.simulation.collision.functions.ShockWave;
import com.company.server.simulation.collision.functions.SimpleFracture;
import com.company.server.simulation.enums.WaveType;
import com.company.server.simulation.types.LayerDescription;

import java.util.ArrayList;
import java.util.Objects;

public class CollisionHandler {
    public CollisionDesc collision;

    public CollisionHandler(CollisionDesc col) {
        collision = col;
    }

    public boolean isCorrectCase(CollidedPairDescription collidedPair) {
        if (collision.firstLayer.equals("sigma*")){
            //Если слева - не быстрый волновой фронт, то продолжаем
            if (collidedPair.getFirstLayer().getSpeed() == SimGlobals.getCharacteristicsSpeedCompression())
                return false;

            //Если слева - не медленный волновой фронт, то продолжаем
            if (collidedPair.getFirstLayer().getSpeed() == SimGlobals.getCharacteristicsSpeedStretching())
                return false;

            //Если слева скорость положительная, то продолжаем
            if (collidedPair.getFirstLayer().getSpeed() <= 0.0)
                return false;
        }
        if (collision.firstLayer.equals("xiA")){
            //Если скорость левого волнового фронта == скорости сжатия (быстрый волновой фронт), то продолжаем
            //Косвенно - если она положительная
            if (collidedPair.getFirstLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedCompression())
                return false;

            //Если слева - деформируемый слой, то продолжаем
            if (collidedPair.getFirstLayer().getA1() + collidedPair.getFirstLayer().getA2() == 0.0)
                return false;
        }
        if (collision.firstLayer.equals("xiA-")){
            //Если слева - быстрый волновой фронт, то продолжаем
            if (collidedPair.getFirstLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedCompression())
                return false;

            //Если слева - деформируемый слой, то продолжаем
            if (collidedPair.getFirstLayer().getA1() + collidedPair.getFirstLayer().getA2() == 0.0)
                return false;
        }
        if (collision.secondLayer.equals("xiB")){
            //Если скорость правого волнового фронта == скорости растяжения (быстрый волновой фронт), то продолжаем
            //Косвенно - если она положительная
            if (collidedPair.getSecondLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedStretching())
                return false;

            //Если справа - деформируемый слой, то продолжаем
            if (collidedPair.getThirdLayer().getA1() + collidedPair.getThirdLayer().getA2() == 0.0)
                return false;
        }
        if (collision.secondLayer.equals("gammaB")){
            //Если скорость правого волнового фронта == скорости растяжения (быстрый волновой фронт), то продолжаем
            //Косвенно - если она положительная
            if (collidedPair.getSecondLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedStretching())
                return false;

            //Если справа - недеформируемый слой, то продолжаем
            if (collidedPair.getThirdLayer().getA1() + collidedPair.getThirdLayer().getA2() != 0.0)
                return false;
        }
        if(collision.secondLayer.equals("sigma*")){
            //Если справа - не быстрый волновой фронт, то продолжаем
            if (collidedPair.getSecondLayer().getSpeed() == SimGlobals.getCharacteristicsSpeedCompression())
                return false;

            //Если справа - не медленный волновой фронт, то продолжаем
            if (collidedPair.getSecondLayer().getSpeed() == SimGlobals.getCharacteristicsSpeedStretching())
                return false;

            //Если справа скорость положительная, то продолжаем
            if (collidedPair.getSecondLayer().getSpeed() <= 0.0)
                return false;
        }
        if(collision.secondLayer.equals("xiA")){
            //Если скорость правого волнового фронта == скорости растяжения (быстрый волновой фронт), то продолжаем
            //Косвенно - если она положительная
            if (collidedPair.getSecondLayer().getSpeed() != 0.0 - SimGlobals.getCharacteristicsSpeedCompression())
                return false;

            //Если справа - деформируемый слой, то продолжаем
            if (collidedPair.getThirdLayer().getA1() + collidedPair.getThirdLayer().getA2() == 0.0)
                return false;
        }

        return true;
    }

    public ArrayList<LayerDescription> calculateWaveFronts(CollidedPairDescription collidedPair) {
        //Результат операций
        var newLayers = new ArrayList<LayerDescription>();

        //Оболочка среды для создаваемых волновых фронтов
        var layerWrapper = new ArrayList<LayerDescription>();

        //Добавляем простой разрыв
        newLayers.add(collidedPair.getFirstLayer());
        if (Objects.equals(collision.resultLayers.get(0), "xiA-")) {
            newLayers.get(0).setSpeed(0.0 - SimGlobals.getCharacteristicsSpeedCompression());
            newLayers.get(0).setWaveFrontStartTime(collidedPair.getCollisionTime());
            newLayers.get(0).setCurrentX(collidedPair.getCollisionX() + collidedPair.getDeltaTime() * newLayers.get(0).getSpeed());
            newLayers.get(0).setWaveType(WaveType.SIMPLE_FRACTURE);
        }
        else if (Objects.equals(collision.resultLayers.get(0), "xiA")) {
            newLayers.get(0).setSpeed(SimGlobals.getCharacteristicsSpeedCompression());
            newLayers.get(0).setWaveFrontStartTime(collidedPair.getCollisionTime());
            newLayers.get(0).setCurrentX(collidedPair.getCollisionX() + collidedPair.getDeltaTime() * newLayers.get(0).getSpeed());
            newLayers.get(0).setWaveType(WaveType.SIMPLE_FRACTURE);
        }

        //--------ПРАВЫЙ ВОЛНОВОЙ ФРОНТ---------
        //--------СЛОЙ ДЕФОРМАЦИИ ПО ЦЕНТРУ--------

        if (collision.resultLayers.size()<2) return newLayers;

        layerWrapper.add(collidedPair.getFirstLayer());
        layerWrapper.add(collidedPair.getThirdLayer());
        switch (collision.resultLayers.get(1)) {
            case ("sigma**"):
                var rightLayer = ShockWave.generatePositiveShockWave(
                        layerWrapper,
                        collidedPair.getCollisionX(),
                        collidedPair.getCollisionTime(),
                        Math.abs(collidedPair.getFirstLayer().getSpeed()),
                        WaveType.SHOCK_WAVE
                );
                rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());
                newLayers.add(rightLayer);
                break;

            case ("gammaA"):
                rightLayer = SimpleFracture.generateFastPositive(
                        layerWrapper,
                        collidedPair.getCollisionX(),
                        collidedPair.getCollisionTime(),
                        WaveType.HALF_SIGNOTON
                );
                rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());
                newLayers.add(rightLayer);
                break;

            case ("gammaA-"):
                rightLayer = SimpleFracture.generateFastNegative(
                        layerWrapper,
                        collidedPair.getCollisionX(),
                        collidedPair.getCollisionTime(),
                        WaveType.HALF_SIGNOTON
                );
                rightLayer.setCurrentX(rightLayer.getCurrentX() - collidedPair.getDeltaTime() * rightLayer.getSpeed());
                newLayers.add(rightLayer);
                break;

            case("xiA"):
                rightLayer = SimpleFracture.generateFastPositive(
                        layerWrapper,
                        collidedPair.getCollisionX(),
                        collidedPair.getCollisionTime(),
                        WaveType.SIMPLE_FRACTURE
                );
                rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());

                newLayers.add(rightLayer);
        }

        return newLayers;
    };
}
