package com.company.GraphicalData.SimulationGUI;

import com.company.Simulation.SimulationSynchronizerThread;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private JPanel graphsPanel = new GraphsPanel();
    private JPanel paramsPanel;

    //Главная панель ввода данных
    public MainPanel(SimulationSynchronizerThread ServerThread){
        paramsPanel = new ParamsPanel(ServerThread);
        this.setLayout(new BorderLayout());
        this.add(graphsPanel);
        this.add(paramsPanel, BorderLayout.SOUTH);
    }
}
