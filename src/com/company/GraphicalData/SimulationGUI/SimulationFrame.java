package com.company.GraphicalData.SimulationGUI;

import com.company.GraphicalData.GUIGlobals;
import com.company.Simulation.SimulationSynchronizerThread;

import javax.swing.*;

public class SimulationFrame extends JFrame {
    private MainPanel mainPanel;

    //Главное окно
    public SimulationFrame(String title, SimulationSynchronizerThread ServerThread){
        mainPanel = new MainPanel(ServerThread);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(GUIGlobals.window_width,GUIGlobals.window_height);
        this.setLocationRelativeTo(null);
        this.add(mainPanel);
        this.setVisible(true);
    }
}
