package com.company.user_clients;

import com.company.GUI.InputGUI.MainForm;
import com.company.thread_organization.SimulationSynchronizerThread;

public class UserClient_v2 extends Thread implements UserClient {

    SimulationSynchronizerThread synchroThread;

    public UserClient_v2(SimulationSynchronizerThread SynchroThread) {
        this.synchroThread = SynchroThread;
    }

    @Override
    public void run() {
        MainForm mainFrame = new MainForm(synchroThread);
    }
}
