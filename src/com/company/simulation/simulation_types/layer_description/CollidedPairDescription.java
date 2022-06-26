package com.company.simulation.simulation_types.layer_description;

/**
 * firstLayer - состояние среды левее места столкновения
 * <br>
 * secondLayer - состояние среды внутри места столкновения
 * <br>
 * thirdLayer - состояние среды правее места столкновения
 */
public class CollidedPairDescription {
    private LayerDescription firstLayer;

    private LayerDescription secondLayer;

    private LayerDescription thirdLayer;

    private double collisionX;

    private double collisionTime;

    private double deltaTime;

    public LayerDescription getFirstLayer() {
        return firstLayer;
    }

    public LayerDescription getSecondLayer() {
        return secondLayer;
    }

    public LayerDescription getThirdLayer() {
        return thirdLayer;
    }

    public double getCollisionX() {
        return collisionX;
    }

    public double getCollisionTime() {
        return collisionTime;
    }

    public double getDeltaTime() {
        return deltaTime;
    }

    public void setFirstLayer(LayerDescription firstLayer) {
        this.firstLayer = firstLayer;
    }

    public void setSecondLayer(LayerDescription secondLayer) {
        this.secondLayer = secondLayer;
    }

    public void setThirdLayer(LayerDescription thirdLayer) {
        this.thirdLayer = thirdLayer;
    }

    public void setCollisionX(double collisionX) {
        this.collisionX = collisionX;
    }

    public void setCollisionTime(double collisionTime) {
        this.collisionTime = collisionTime;
    }

    public void setDeltaTime(double deltaTime) {
        this.deltaTime = deltaTime;
    }
}
