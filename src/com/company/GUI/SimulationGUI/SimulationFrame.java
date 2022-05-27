package com.company.GUI.SimulationGUI;

import com.company.GUI.GUIGlobals;
import com.company.thread_organization.SimulationSynchronizerThread;

import javax.swing.*;
import java.awt.*;

/**
 * Окно симуляции
 */
public class SimulationFrame extends JFrame {
    private MainPanel mainPanel;

    /**
     * Главное окно симуляции
     *
     * @param ServerThread Поток
     */
    public SimulationFrame(SimulationSynchronizerThread ServerThread){

        // Создание окна
        mainPanel = new MainPanel(ServerThread);
        this.setTitle("ADCS - Симуляция");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(GUIGlobals.graph_frame_width,GUIGlobals.graph_frame_height);
        this.setLocationRelativeTo(null);
        this.add(mainPanel);
        this.setVisible(true);
    }
}
