package com.company.client.ui;

import com.company.client.gui.InputGUI.MainForm;
import com.company.server.runtime.SimServer;

public class UserClient_v2 extends Thread implements UserClient {

    SimServer synchroThread;

    public UserClient_v2(SimServer SynchroThread) {
        this.synchroThread = SynchroThread;
    }

    @Override
    public void run() {
        MainForm mainFrame = new MainForm(synchroThread);
    }
}
