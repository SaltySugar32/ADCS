package com.company.GraphicalData.GUI;

import javax.swing.*;

public class MainPanel extends JPanel {
    private JPanel inputPanel;
    private JPanel graphPanel;
    private JSplitPane splitPane;
    private JButton startButton;

    //Главная панель ввода данных
    public MainPanel(){
        inputPanel = new InputPanel();
        graphPanel = new GraphPanel();
        startButton = new JButton("START");
        splitPane = new JSplitPane();
        splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
        splitPane.setDividerLocation(150);
        splitPane.setTopComponent(inputPanel);
        splitPane.setBottomComponent(graphPanel);

        this.setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        this.add(splitPane);
        this.add(startButton);
    }
}
