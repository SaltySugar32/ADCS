package com.company.GraphicalData.InputGUI;

import javax.swing.*;
import java.awt.*;

public class GraphPanel extends JPanel {
    JLabel label_placeholder = new JLabel("ЗДЕСЬ БУДЕТ ГРАФИЧЕСКИЙ ВВОД");

    //Панель графического ввода
    public GraphPanel(){

        this.setBackground(Color.orange);
        this.add(label_placeholder);
    }
}
