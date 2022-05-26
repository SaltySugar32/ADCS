package com.company.GUI.SimulationGUI;

import com.company.ThreadOrganization.SimulationSynchronizerThread;

import javax.swing.*;
import java.awt.*;

/**
 * Главная панель
 */
public class MainPanel extends JPanel {

    // Панель отображения графиков
    private JPanel graphsPanel = new GraphsPanel();

    // Панель изменения параметров отображения
    private JPanel paramsPanel;

    /**
     * Главная панель симуляции
     *
     * @param ServerThread
     */
    public MainPanel(SimulationSynchronizerThread ServerThread){

        // Отрисовка панели
        paramsPanel = new ParamsPanel(ServerThread);
        this.setLayout(new BorderLayout());
        this.add(graphsPanel);
        this.add(paramsPanel, BorderLayout.SOUTH);
    }
}
