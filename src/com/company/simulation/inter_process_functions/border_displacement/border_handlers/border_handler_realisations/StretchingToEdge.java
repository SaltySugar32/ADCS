package com.company.simulation.inter_process_functions.border_displacement.border_handlers.border_handler_realisations;

import com.company.simulation.inter_process_functions.border_displacement.border_handlers.IBorderHandler;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.DenoteFactor;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public class StretchingToEdge implements IBorderHandler {
    /**
     * Вычисление напряжения Эйлера-Коши на стыке волн деформации
     */
    public double computeTension() {
        //return \lambda + 2 * \mu - 2 * \nu
        return SimulationGlobals.getLameLambda() + 2 * SimulationGlobals.getLameMu() - 2 * SimulationGlobals.getCoefficientNu();
    }

    /**
     * Вычисление характеристической скорости
     */
    public double computeCharSpeed()
    {
        //return \sigma / \rho === (\lambda + 2 * \mu +- 2 * \nu) / \rho
        return Math.sqrt(computeTension() / SimulationGlobals.getMaterialDensity());
    }

    @Override
    public ArrayList<WaveFront> generateNewWaveFronts(ArrayList<WaveFront> prevWaveFronts) {
        var newWaveFronts = new ArrayList<WaveFront>();

        double speed = DenoteFactor.NULL.toMillimeters(computeCharSpeed());

        double A2 = 0.0 - prevWaveFronts.get(0).getA1() / speed;

        WaveFront newWaveFront = new WaveFront(prevWaveFronts.get(0).getA1(), A2, prevWaveFronts.get(0).getA0(), prevWaveFronts.get(0).getStartTime(), DenoteFactor.MILLI);

        newWaveFront.setSpeed(computeCharSpeed());

        newWaveFronts.add(newWaveFront);

        return newWaveFronts;
    }
}
