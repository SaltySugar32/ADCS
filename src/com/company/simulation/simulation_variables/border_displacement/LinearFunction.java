package com.company.simulation.simulation_variables.border_displacement;

public record LinearFunction(double k, double b, double startTime) {

    public double calculateBorderDisplacement(double currentTime) {
        return k * (currentTime - startTime) + b;
    }

}
