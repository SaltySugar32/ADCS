package com.company.GraphicalData.SimulationGUI;

import com.company.GraphicalData.GUIGlobals;

import javax.swing.*;
import java.awt.*;

public class GraphsPanel extends JPanel {
    private JLabel label_placeholder = new JLabel("ЗДЕСЬ БУДЕТ ГРАФИЧЕСКИЙ ВЫВОД");
    public GraphsPanel(){
        this.setBackground(GUIGlobals.background_color);
        this.add(label_placeholder);
    }
}
