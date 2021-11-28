package com.company.Simulation;

import com.company.ProgramGlobals;

public class SimulationSynchronizerThread extends Thread {
    SimulationServerThread simulationServerThread;

    public SimulationSynchronizerThread(SimulationServerThread simulationServerThread) {
        this.simulationServerThread = simulationServerThread;
    }

    @Override
    public void run() {
        double timeOnOperation = (double) ProgramGlobals.getOperationsPerSecond() / 1000;


    }
}
