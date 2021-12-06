package com.company.GraphicalData.InputGUI;

import com.company.GraphicalData.GUIGlobals;

import javax.swing.*;
import java.awt.*;

public class MainPanel extends JPanel {
    private JPanel inputPanel = new InputPanel();
    private JPanel graphPanel = new GraphPanel();
    private JSplitPane splitPane = new JSplitPane();

    //Главная панель ввода данных
    public MainPanel(JButton startButton){
        startButton.setPreferredSize(new Dimension(50, 50));
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(GUIGlobals.splitPane_position);
        splitPane.setTopComponent(inputPanel);
        splitPane.setBottomComponent(graphPanel);

        this.setLayout(new BorderLayout());
        this.add(splitPane);
        this.add(startButton, BorderLayout.SOUTH);

    }
}
