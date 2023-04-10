package com.company.server.simulation.simulation_types.layer_description;

public record BorderDescription(double k, double b, double startTime) {

    public double calculateBorderDisplacement(double currentTime) {
        return k * (currentTime - startTime) + b;
    }

}
