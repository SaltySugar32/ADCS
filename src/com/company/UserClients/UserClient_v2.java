package com.company.UserClients;

import com.company.GUI.InputGUI.MainFrame;
import com.company.Simulation.SimulationSynchronizerThread;

public class UserClient_v2 extends Thread implements UserClient {

    SimulationSynchronizerThread synchroThread;

    public UserClient_v2(SimulationSynchronizerThread SynchroThread) {
        this.synchroThread = SynchroThread;
    }

    @Override
    public void run() {
        MainFrame mainFrame = new MainFrame("ADCS", synchroThread);
    }
}
