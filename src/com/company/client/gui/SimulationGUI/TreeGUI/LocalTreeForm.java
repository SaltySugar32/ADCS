package com.company.client.gui.SimulationGUI.TreeGUI;

import com.company.client.gui.GUIGlobals;

import javax.swing.*;

public class LocalTreeForm extends JFrame {
    private JPanel contentPane;
    private JPanel treePanel;
    private JTable table1;

    public LocalTreeForm(){
        setTitle(GUIGlobals.program_title + " - Таблица возможных взаимодействий");
        setSize(GUIGlobals.env_param_frame_width*2, GUIGlobals.env_param_frame_height);
        this.add(contentPane);

        this.setVisible(true);
    }
}
