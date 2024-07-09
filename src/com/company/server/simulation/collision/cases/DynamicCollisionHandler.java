package com.company.server.simulation.collision.cases;

import com.company.client.gui.Database.CollisionDesc;
import com.company.server.runtime.vars.SimGlobals;
import com.company.server.simulation.collision.CollidedPairDescription;
import com.company.server.simulation.types.LayerDescription;

import java.util.ArrayList;

public class DynamicCollisionHandler implements ICollisionHandler{
    private CollisionDesc collisionDesc;
    public DynamicCollisionHandler(CollisionDesc col){
        this.collisionDesc = col;
    }

    @Override
    public String shortDescription() {
        return collisionDesc.shortDescription;
    }

    @Override
    public String longDescription() {
        return null;
    }
    @Override
    public boolean isCorrectCase(CollidedPairDescription collidedPair) {
        // Пример проверки для случая. Нужно адаптировать под точные условия.
        return (checkFirstLayer(collidedPair) && checkSecondLayer(collidedPair));
    }

    private boolean checkFirstLayer(CollidedPairDescription collidedPair){
        if (collisionDesc.firstLayer.contains("sigma")){
            //Если слева - не быстрый волновой фронт, то продолжаем
            if (collidedPair.getFirstLayer().getSpeed() == SimGlobals.getCharacteristicsSpeedCompression())
                return false;

            //Если слева - не медленный волновой фронт, то продолжаем
            if (collidedPair.getFirstLayer().getSpeed() == SimGlobals.getCharacteristicsSpeedStretching())
                return false;
        }

        if (collisionDesc.firstLayer.contains("xi")){
            //xi(a)
            //Если скорость левого волнового фронта == скорости сжатия (быстрый волновой фронт), то продолжаем
            //Косвенно - если она положительная
            if (collidedPair.getFirstLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedCompression())
                return false;

            //Если слева - деформируемый слой, то продолжаем
            if (collidedPair.getFirstLayer().getA1() + collidedPair.getFirstLayer().getA2() == 0.0)
                return false;

            //xi(-a)
            //Если слева - быстрый волновой фронт, то продолжаем
            if (collidedPair.getFirstLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedCompression())
                return false;

            //Если слева - деформируемый слой, то продолжаем
            if (collidedPair.getFirstLayer().getA1() + collidedPair.getFirstLayer().getA2() == 0.0)
                return false;

        }

        if (collisionDesc.firstLayer.contains("gamma")){

        }
        return true;
    }

    private boolean checkSecondLayer(CollidedPairDescription collidedPair){
        if (collisionDesc.secondLayer.contains("sigma")){
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

        if (collisionDesc.secondLayer.contains("xi")){
            //xi(a)
            //Если скорость правого волнового фронта == скорости растяжения (быстрый волновой фронт), то продолжаем
            //Косвенно - если она положительная
            if (collidedPair.getSecondLayer().getSpeed() != 0.0 - SimGlobals.getCharacteristicsSpeedCompression())
                return false;

            //Если справа - деформируемый слой, то продолжаем
            if (collidedPair.getThirdLayer().getA1() + collidedPair.getThirdLayer().getA2() == 0.0)
                return false;

            //xi(b)
            //Если скорость правого волнового фронта == скорости растяжения (быстрый волновой фронт), то продолжаем
            //Косвенно - если она положительная
            if (collidedPair.getSecondLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedStretching())
                return false;

            //Если справа - деформируемый слой, то продолжаем
            if (collidedPair.getThirdLayer().getA1() + collidedPair.getThirdLayer().getA2() == 0.0)
                return false;

        }

        if (collisionDesc.secondLayer.contains("gamma")){
            //gamma(b)
            //Если скорость правого волнового фронта == скорости растяжения (быстрый волновой фронт), то продолжаем
            //Косвенно - если она положительная
            if (collidedPair.getSecondLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedStretching())
                return false;

            //Если справа - недеформируемый слой, то продолжаем
            if (collidedPair.getThirdLayer().getA1() + collidedPair.getThirdLayer().getA2() != 0.0)
                return false;

        }

        if(collisionDesc.secondLayer.contains("O")){

        }
        return true;
    }

    @Override
    public ArrayList<LayerDescription> calculateWaveFronts(CollidedPairDescription collidedPair) {
        // Пример обработки для случая. Нужно адаптировать под точные условия.
        ArrayList<LayerDescription> newLayers = new ArrayList<>();
        // Использовать collisionDesc для создания новых слоев
        for (String resultLayer : collisionDesc.resultLayers) {
            //LayerDescription layer = new LayerDescription(resultLayer);
            // Настройка layer на основе resultLayer и collidedPair
            //newLayers.add(layer);
        }
        return newLayers;
    }
}
