package com.company.GUI.SimulationGUI;

import com.company.GUI.GUIGlobals;

import javax.swing.*;

/**
 * Панель отображения результатов
 */
public class GraphsPanel extends JPanel {

    // Метка - плейсхолдер
    private JLabel label_placeholder = new JLabel("ЗДЕСЬ БУДЕТ ГРАФИЧЕСКИЙ ВЫВОД");

    /**
     * Панель отображения результатов
     */
    public GraphsPanel(){

        //Отрисовка панели
        this.setBackground(GUIGlobals.background_color);
        this.add(label_placeholder);
    }
}
