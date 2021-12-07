package com.company.GUI.SimulationGUI;

import com.company.GUI.GUIGlobals;
import com.company.Simulation.SimulationSynchronizerThread;

import javax.swing.*;

/**
 * Окно симуляции
 */
public class SimulationFrame extends JFrame {
    private MainPanel mainPanel;

    /**
     * Главное окно симуляции
     *
     * @param title Название окна
     * @param ServerThread Поток
     */
    public SimulationFrame(String title, SimulationSynchronizerThread ServerThread){

        // Создание окна
        mainPanel = new MainPanel(ServerThread);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(GUIGlobals.window_width,GUIGlobals.window_height);
        this.setLocationRelativeTo(null);
        this.add(mainPanel);
        this.setVisible(true);
    }
}
