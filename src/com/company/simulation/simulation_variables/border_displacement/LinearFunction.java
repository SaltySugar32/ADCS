package com.company.simulation.simulation_variables.border_displacement;

public class LinearFunction {

    private final double k;

    private final double b;

    private final double time;

    public double getK() {
        return k;
    }

    public double getB() {
        return b;
    }

    public double getTime() {
        return time;
    }

    public double calculateBorderDisplacement(double currentTime) {
        return k * (time - currentTime) + b;
    }

    public LinearFunction(double k, double b, double time) {
        this.k = k;
        this.b = b;
        this.time = time;
    }
}
