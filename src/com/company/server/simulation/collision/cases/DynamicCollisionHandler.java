package com.company.server.simulation.collision.cases;

import com.company.client.gui.Database.CollisionDesc;
import com.company.server.runtime.vars.SimGlobals;
import com.company.server.simulation.collision.CollidedPairDescription;
import com.company.server.simulation.collision.functions.ShockWave;
import com.company.server.simulation.collision.functions.SimpleFracture;
import com.company.server.simulation.enums.WaveType;
import com.company.server.simulation.types.LayerDescription;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String extractSpeedString(String input) {
        if (input == null || input.isEmpty()) {
            return "";
        }

        int startIndex = input.indexOf('(');
        int endIndex = input.indexOf(')', startIndex + 1);

        if (startIndex != -1 && endIndex != -1) {
            return input.substring(startIndex + 1, endIndex);
        } else {
            return "";
        }
    }

    private boolean checkFirstLayer(CollidedPairDescription collidedPair){
        String speedString = extractSpeedString(collisionDesc.firstLayer);

        if (collisionDesc.firstLayer.contains("sigma")){
            //Если слева - не быстрый волновой фронт, то продолжаем
            if (collidedPair.getFirstLayer().getSpeed() == SimGlobals.getCharacteristicsSpeedCompression())
                return false;

            //Если слева - не медленный волновой фронт, то продолжаем
            if (collidedPair.getFirstLayer().getSpeed() == SimGlobals.getCharacteristicsSpeedStretching())
                return false;
        }

        else if (collisionDesc.firstLayer.contains("xi")){

            if(speedString.contains("a")) {

                if(speedString.contains("-")){

                    //xi(-a)
                    //Если слева - быстрый волновой фронт, то продолжаем
                    if (collidedPair.getFirstLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedCompression())
                        return false;

                    //Если слева - деформируемый слой, то продолжаем
                    if (collidedPair.getFirstLayer().getA1() + collidedPair.getFirstLayer().getA2() == 0.0)
                        return false;

                }
                else {

                    //xi(a)
                    //Если скорость левого волнового фронта == скорости сжатия (быстрый волновой фронт), то продолжаем
                    //Косвенно - если она положительная
                    if (collidedPair.getFirstLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedCompression())
                        return false;

                    //Если слева - деформируемый слой, то продолжаем
                    if (collidedPair.getFirstLayer().getA1() + collidedPair.getFirstLayer().getA2() == 0.0)
                        return false;

                }
            }
            else {
                if(speedString.contains("-")){
                    //TODO xi(-b)
                    return false;
                }
                else{
                    //TODO xi(b)
                    return false;
                }

            }
        }

        else if (collisionDesc.firstLayer.contains("gamma")){
            // TODO gamma
            return false;
        }

        return true;
    }

    private boolean checkSecondLayer(CollidedPairDescription collidedPair){
        String speedString = extractSpeedString(collisionDesc.secondLayer);

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

        else if (collisionDesc.secondLayer.contains("xi")){
            if (speedString.contains("a")){

                if(speedString.contains("-")){
                    //TODO xi(-a)
                    return false;
                }
                else{
                    //xi(a)
                    //Если скорость правого волнового фронта == скорости растяжения (быстрый волновой фронт), то продолжаем
                    //Косвенно - если она положительная
                    if (collidedPair.getSecondLayer().getSpeed() != 0.0 - SimGlobals.getCharacteristicsSpeedCompression())
                        return false;

                    //Если справа - деформируемый слой, то продолжаем
                    if (collidedPair.getThirdLayer().getA1() + collidedPair.getThirdLayer().getA2() == 0.0)
                        return false;
                }
            }
            else {
                if (speedString.contains("-")){
                    //TODO: xi(-b)
                    return false;
                }
                else {
                    //xi(b)
                    //Если скорость правого волнового фронта == скорости растяжения (быстрый волновой фронт), то продолжаем
                    //Косвенно - если она положительная
                    if (collidedPair.getSecondLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedStretching())
                        return false;

                    //Если справа - деформируемый слой, то продолжаем
                    if (collidedPair.getThirdLayer().getA1() + collidedPair.getThirdLayer().getA2() == 0.0)
                        return false;
                }
            }

        }

        else if (collisionDesc.secondLayer.contains("gamma")){
            if(speedString.contains("a")){
                if(speedString.contains("-")){
                    //TODO gamma(-a)
                    return false;
                }
                else{
                    //TODO gamma(a)
                    return false;
                }
            }
            else{
                if(speedString.contains("-")){
                    // TODO gamma(-b)
                    return false;
                }
                else{
                    //gamma(b)
                    //Если скорость правого волнового фронта == скорости растяжения (быстрый волновой фронт), то продолжаем
                    //Косвенно - если она положительная
                    if (collidedPair.getSecondLayer().getSpeed() != SimGlobals.getCharacteristicsSpeedStretching())
                        return false;

                    //Если справа - недеформируемый слой, то продолжаем
                    if (collidedPair.getThirdLayer().getA1() + collidedPair.getThirdLayer().getA2() != 0.0)
                        return false;
                }
            }
        }

        else if(collisionDesc.secondLayer.contains("O")){
            //TODO O
            return false;
        }
        return true;
    }

    @Override
    public ArrayList<LayerDescription> calculateWaveFronts(CollidedPairDescription collidedPair) {
        
        // Берем текущий выбранный результат взаимодействия
        String[] waveFronts = splitFronts(collisionDesc.resultLayers.get(0));
        
        //Результат операций
        var newLayers = new ArrayList<LayerDescription>();

        //Оболочка среды для создаваемых волновых фронтов
        var layerWrapper = new ArrayList<LayerDescription>();

        //--------ЛЕВЫЙ ВОЛНОВОЙ ФРОНТ--------

        if(waveFronts[0]!=null)
            addLeftFront(newLayers, collidedPair, waveFronts[0]);

        //--------ПРАВЫЙ ВОЛНОВОЙ ФРОНТ---------
        //--------СЛОЙ ДЕФОРМАЦИИ ПО ЦЕНТРУ--------
        layerWrapper.add(collidedPair.getFirstLayer());
        layerWrapper.add(collidedPair.getThirdLayer());
        
        if(waveFronts[1]!=null)
            addRightFront(layerWrapper,newLayers,collidedPair,waveFronts[1]);
        
        return newLayers;
    }
    
    private void addLeftFront(ArrayList<LayerDescription> newLayers, CollidedPairDescription collidedPair, String front){
        String speedString = extractSpeedString(front);
        newLayers.add(collidedPair.getFirstLayer());

        if(front.contains("xi")){
            newLayers.get(0).setWaveType(WaveType.SIMPLE_FRACTURE);

            if (speedString.contains("a")){
                if(speedString.contains("-")){
                    //xi(-a)
                    newLayers.get(0).setSpeed(0.0 - SimGlobals.getCharacteristicsSpeedCompression());
                }
                else{
                    //xi(a)
                    newLayers.get(0).setSpeed(SimGlobals.getCharacteristicsSpeedCompression());
                }
            }
            else{
                if(speedString.contains("-")){
                    //xi(-b)
                    newLayers.get(0).setSpeed(0.0 - SimGlobals.getCharacteristicsSpeedStretching());
                }
                else{
                    //xi(b)
                    newLayers.get(0).setSpeed(SimGlobals.getCharacteristicsSpeedStretching());
                }
            }
        }
        else if (front.contains("gamma")){
            newLayers.get(0).setWaveType(WaveType.HALF_SIGNOTON);

            if (speedString.contains("a")){
                if(speedString.contains("-")){
                    //gamma(-a)
                    newLayers.get(0).setSpeed(0.0 - SimGlobals.getCharacteristicsSpeedCompression());
                }
                else{
                    //gamma(a)
                    newLayers.get(0).setSpeed(SimGlobals.getCharacteristicsSpeedCompression());
                }
            }
            else{
                if(speedString.contains("-")){
                    //gamma(-b)
                    newLayers.get(0).setSpeed(0.0 - SimGlobals.getCharacteristicsSpeedStretching());
                }
                else{
                    //gamma(b)
                    newLayers.get(0).setSpeed(SimGlobals.getCharacteristicsSpeedStretching());
                }
            }
        }
        else if (front.contains("sigma")){
            newLayers.get(0).setWaveType(WaveType.SHOCK_WAVE);
            newLayers.get(0).setSpeed(
                    Math.abs(collidedPair.getFirstLayer().getSpeed()) + SimGlobals.getCharacteristicsSpeedStretching() / 2
            );
        }

        newLayers.get(0).setWaveFrontStartTime(collidedPair.getCollisionTime());
        newLayers.get(0).setCurrentX(collidedPair.getCollisionX() + collidedPair.getDeltaTime() * newLayers.get(0).getSpeed());
    }

    private void addRightFront(ArrayList<LayerDescription> layerWrapper, ArrayList<LayerDescription> newLayers, CollidedPairDescription collidedPair, String front){
        String speedString = extractSpeedString(front);

        if(front.contains("sigma")){
            //sigma
            var rightLayer = ShockWave.generatePositiveShockWave(
                    layerWrapper,
                    collidedPair.getCollisionX(),
                    collidedPair.getCollisionTime(),
                    Math.abs(collidedPair.getFirstLayer().getSpeed()),
                    WaveType.SHOCK_WAVE
            );
            rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());
            newLayers.add(rightLayer);
        }
        else if (front.contains("xi")){
            if(speedString.contains("a")){
                if(speedString.contains("-")){
                    //xi(-a)
                    var rightLayer = SimpleFracture.generateFastNegative(
                            layerWrapper,
                            collidedPair.getCollisionX(),
                            collidedPair.getCollisionTime(),
                            WaveType.SIMPLE_FRACTURE
                    );
                    rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());
                    newLayers.add(rightLayer);
                }
                else{
                    //xi(a)
                    var rightLayer = SimpleFracture.generateFastPositive(
                            layerWrapper,
                            collidedPair.getCollisionX(),
                            collidedPair.getCollisionTime(),
                            WaveType.SIMPLE_FRACTURE
                    );
                    rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());
                    newLayers.add(rightLayer);
                }
            }
            else{
                if (speedString.contains("-")){
                    //xi(-b)
                    var rightLayer = SimpleFracture.generateSlowNegative(
                            layerWrapper,
                            collidedPair.getCollisionX(),
                            collidedPair.getCollisionTime(),
                            WaveType.SIMPLE_FRACTURE
                    );
                    rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());
                    newLayers.add(rightLayer);
                }
                else{
                    //xi(b)
                    var rightLayer = SimpleFracture.generateSlowPositive(
                            layerWrapper,
                            collidedPair.getCollisionX(),
                            collidedPair.getCollisionTime(),
                            WaveType.SIMPLE_FRACTURE
                    );
                    rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());
                    newLayers.add(rightLayer);
                }
            }

        }
        else if (front.contains("gamma")){
            if(speedString.contains("a")){
                if (speedString.contains("-")){
                    //gamma(-a)
                    var rightLayer = SimpleFracture.generateFastNegative(
                            layerWrapper,
                            collidedPair.getCollisionX(),
                            collidedPair.getCollisionTime(),
                            WaveType.HALF_SIGNOTON
                    );
                    rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());
                    newLayers.add(rightLayer);
                }
                else{
                    //gamma(a)
                    var rightLayer = SimpleFracture.generateFastPositive(
                            layerWrapper,
                            collidedPair.getCollisionX(),
                            collidedPair.getCollisionTime(),
                            WaveType.HALF_SIGNOTON
                    );
                    rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());
                    newLayers.add(rightLayer);
                }
            }
            else{
                if(speedString.contains("-")){
                    //gamma(-b)
                    var rightLayer = SimpleFracture.generateSlowNegative(
                            layerWrapper,
                            collidedPair.getCollisionX(),
                            collidedPair.getCollisionTime(),
                            WaveType.HALF_SIGNOTON
                    );
                    rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());
                    newLayers.add(rightLayer);
                }
                else{
                    //gamma(b)
                    var rightLayer = SimpleFracture.generateSlowPositive(
                            layerWrapper,
                            collidedPair.getCollisionX(),
                            collidedPair.getCollisionTime(),
                            WaveType.HALF_SIGNOTON
                    );
                    rightLayer.setCurrentX(rightLayer.getCurrentX() + collidedPair.getDeltaTime() * rightLayer.getSpeed());
                    newLayers.add(rightLayer);
                }
            }
        }
    }

    private static String[] splitFronts(String layer) {
        return layer.split("(?<=\\))(?=[a-zA-Z]+\\()");
    }
}
