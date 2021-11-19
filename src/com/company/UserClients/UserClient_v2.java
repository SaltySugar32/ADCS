package com.company.UserClients;

import com.company.Simulation.SimulationServer;

public class UserClient_v2 extends Thread implements UserClient {
    SimulationServer ServerThread;

    public UserClient_v2(SimulationServer ServerThread) {
        this.ServerThread = ServerThread;
    }

    @Override
    public void run() {

    }
}
