package com.company.simulation.simulation_variables.border_displacement;

import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.simulation.simulation_variables.wave_front.DenoteFactor;

public record LinearFunction(double k, double b, double startTime) {

    public double calculateBorderDisplacement(double currentTime) {
        return k * (currentTime - startTime) + b;
    }

}
