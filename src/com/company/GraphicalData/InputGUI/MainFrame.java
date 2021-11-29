package com.company.GraphicalData.InputGUI;

import com.company.GraphicalData.DataHandler;
import com.company.GraphicalData.SimulationGUI.SimulationFrame;
import com.company.Simulation.SimulationSynchronizerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainFrame extends JFrame {
    private MainPanel mainPanel;
    private JButton startButton = new JButton("ПЕРЕЙТИ К СИМУЛЯЦИИ");

    //Главное окно
    public MainFrame(String title, SimulationSynchronizerThread ServerThread){
        mainPanel = new MainPanel(startButton);
        this.setTitle(title);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(800,800);
        this.setLocationRelativeTo(null);
        this.add(mainPanel);
        this.setVisible(true);

        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                if(DataHandler.getParamInputStatus()) {
                    dispose();
                    SimulationFrame simulationFrame = new SimulationFrame("ADCS - Симуляция", ServerThread);
                }
                else{
                    JDialog dialog = new JDialog(new JFrame(), "ADCS - error");
                    dialog.setLocationRelativeTo(null);
                    JLabel lab = new JLabel("Сначала введите исходные параметры");
                    dialog.setLayout(new BorderLayout());
                    dialog.add(lab, BorderLayout.NORTH);
                    dialog.setSize(300, 100);
                    dialog.setVisible(true);
                }
            }
        });

    }

}
