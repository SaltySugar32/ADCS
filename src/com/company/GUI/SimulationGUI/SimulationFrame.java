package com.company.GUI.SimulationGUI;

import com.company.GUI.DataHandler;
import com.company.GUI.GUIGlobals;
import com.company.Simulation.SimulationSynchronizerThread;

import javax.swing.*;
import javax.xml.crypto.Data;

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
        this.setSize(GUIGlobals.graph_frame_width,GUIGlobals.graph_frame_height);
        this.setLocationRelativeTo(null);
        this.add(mainPanel);
        this.setVisible(true);

        /*
        double[][] array = DataHandler.lin_appr_array;
                for(int i=0; i<array[0].length; i++){
                    System.out.println(array[0][i] + "; " + array[1][i]);
                }
         */
    }
}
