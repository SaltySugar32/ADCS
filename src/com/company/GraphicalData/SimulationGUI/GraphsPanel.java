package com.company.GraphicalData.SimulationGUI;

import javax.swing.*;
import java.awt.*;

public class GraphsPanel extends JPanel {
    private JLabel label_placehlder = new JLabel("ЗДЕСЬ БУДУТ ГРАФИКИ");
    public GraphsPanel(){
        this.setBackground(Color.orange);
        this.add(label_placehlder);
    }
}
