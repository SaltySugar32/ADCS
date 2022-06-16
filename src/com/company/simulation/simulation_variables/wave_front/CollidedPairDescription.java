package com.company.simulation.simulation_variables.wave_front;

import com.company.simulation.simulation_variables.wave_front.LayerDescription;

public class CollidedPairDescription {
    private LayerDescription firstLayer;

    private LayerDescription secondLayer;

    private double collisionX;

    private double collisionTime;

    public LayerDescription getFirstLayer() {
        return firstLayer;
    }

    public LayerDescription getSecondLayer() {
        return secondLayer;
    }

    public double getCollisionX() {
        return collisionX;
    }

    public double getCollisionTime() {
        return collisionTime;
    }

    public void setFirstLayer(LayerDescription firstLayer) {
        this.firstLayer = firstLayer;
    }

    public void setSecondLayer(LayerDescription secondLayer) {
        this.secondLayer = secondLayer;
    }

    public void setCollisionX(double collisionX) {
        this.collisionX = collisionX;
    }

    public void setCollisionTime(double collisionTime) {
        this.collisionTime = collisionTime;
    }
}
