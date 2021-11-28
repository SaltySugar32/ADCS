package com.company.UserClients;

import com.company.GraphicalData.GUI.MainFrame;
import com.company.Simulation.SimulationServerThread;
import com.company.Simulation.SimulationSynchronizerThread;

public class UserClient_v2 extends Thread implements UserClient {

    SimulationSynchronizerThread ServerThread;

    public UserClient_v2(SimulationSynchronizerThread SynchroThread) {
        this.ServerThread = SynchroThread;
    }

    @Override
    public void run() {
        MainFrame mainFrame = new MainFrame("ADCS");
    }
}
