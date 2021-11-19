package com.company.GraphicalData.GUI;

import javax.swing.*;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;

    //Главное окно
    public MainFrame(String title){
        mainPanel = new MainPanel();
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(500,500);
        this.setLocationRelativeTo(null);
        this.add(mainPanel);
        this.setVisible(true);
    }
}
