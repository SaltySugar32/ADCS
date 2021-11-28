package com.company.GraphicalData.SimulationGUI;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private JPanel graphsPanel = new GraphsPanel();
    private JPanel paramsPanel = new ParamsPanel();

    //Главная панель ввода данных
    public MainPanel(){
        this.setLayout(new BorderLayout());
        this.add(graphsPanel);
        this.add(paramsPanel, BorderLayout.SOUTH);
    }
}
