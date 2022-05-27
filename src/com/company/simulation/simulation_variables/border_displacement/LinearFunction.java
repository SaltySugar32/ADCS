package com.company.simulation.simulation_variables.border_displacement;

public class LinearFunction {

    private final double k;

    private final double b;

    private final double startTime;

    public double getK() {
        return k;
    }

    public double getB() {
        return b;
    }

    public double getStartTime() {
        return startTime;
    }

    public double calculateBorderDisplacement(double currentTime) {
        return k * (currentTime - startTime) + b;
    }

    public LinearFunction(double k, double b, double startTime) {
        this.k = k;
        this.b = b;
        this.startTime = startTime;
    }
}
