package com.company.simulation.inter_process_functions.collision_handlers.collision_handler_realisations;

import com.company.simulation.inter_process_functions.collision_handlers.ICollisionHandler;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.DenoteFactor;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public class StretchingInBorder implements ICollisionHandler {
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
        return computeTension() / SimulationGlobals.getMaterialDensity();
    }

    @Override
    public ArrayList<WaveFront> generateNewWaveFronts(ArrayList<WaveFront> prevWaveFronts) {
        var newWaveFronts = new ArrayList<WaveFront>();

        WaveFront newWaveFront = new WaveFront(prevWaveFronts.get(0).getA1(), prevWaveFronts.get(0).getA2(), prevWaveFronts.get(0).getA0(), DenoteFactor.MILLI);

        newWaveFront.setSpeed(computeCharSpeed());

        newWaveFronts.add(newWaveFront);

        return newWaveFronts;
    }
}
