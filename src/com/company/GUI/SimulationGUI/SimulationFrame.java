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

        // заглушка
        JButton b1 = new JButton("Пауза");
        //b1.setSize(new Dimension(100,50));
        JButton b2 = new JButton("Остановка");
        //b2.setSize(new Dimension(100,50));
        JPanel panel = new JPanel();
        panel.add(b1);
        panel.add(b2);
        add(panel, BorderLayout.NORTH);
    }
}
