package com.company.UserClients;

import com.company.GraphicalData.GUI.MainFrame;
import com.company.Simulation.SimulationServerThread;

public class UserClient_v2 extends Thread implements UserClient {

    SimulationServerThread ServerThread;

    public UserClient_v2(SimulationServerThread ServerThread) {
        this.ServerThread = ServerThread;
    }

    @Override
    public void run() {
        MainFrame mainFrame = new MainFrame("ADCS");
    }
}
