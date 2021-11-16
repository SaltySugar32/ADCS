package com.company.Simulation.SimulationClients;

import com.company.Simulation.SimulationClient;
import com.company.Simulation.SimulationServer;

public class SimulationClient_v2 extends Thread implements SimulationClient {
    SimulationServer ServerThread;

    public SimulationClient_v2(SimulationServer ServerThread) {
        this.ServerThread = ServerThread;
    }

    @Override
    public void run() {

    }
}
