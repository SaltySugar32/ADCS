package com.company.GUI.legacy;

import com.company.GUI.GUIGlobals;

import javax.swing.*;
import java.awt.*;

/**
 * Главная панель ввода
 */
public class MainPanel extends JPanel {

    // Панели ввода
    private JPanel inputPanel = new InputPanel();
    private JPanel graphPanel = new GraphPanel();

    // Разделитель панелей
    private JSplitPane splitPane = new JSplitPane();

    /**
     * Главная панель ввода данных
     *
     * @param startButton кнопка перехода к симуляции
     */
    public MainPanel(JButton startButton){

        startButton.setPreferredSize(GUIGlobals.button_dimension);
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(GUIGlobals.splitPane_position);
        splitPane.setTopComponent(inputPanel);
        splitPane.setBottomComponent(graphPanel);

        this.setLayout(new BorderLayout());
        this.add(splitPane);
        this.add(startButton, BorderLayout.SOUTH);

    }
}
