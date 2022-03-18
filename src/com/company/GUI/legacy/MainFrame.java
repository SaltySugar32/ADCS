package com.company.GUI.legacy;

import com.company.GUI.DataHandler;
import com.company.GUI.GUIGlobals;
import com.company.GUI.SimulationGUI.SimulationFrame;
import com.company.Simulation.SimulationSynchronizerThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Главное окно ввода
 */
public class MainFrame extends JFrame {

    // Главная панель
    private MainPanel mainPanel;

    // Кнопка перехода к симуляции
    private JButton startButton = new JButton("ПЕРЕЙТИ К СИМУЛЯЦИИ");

    /**
     * Главное окно ввода данных
     *
     * @param title Название окна
     * @param ServerThread Поток
     */
    public MainFrame(String title, SimulationSynchronizerThread ServerThread){

        // Создание окна ввода
        mainPanel = new MainPanel(startButton);
        this.setTitle(title);
        //this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(GUIGlobals.window_width,GUIGlobals.window_height);
        this.setLocationRelativeTo(null);
        this.add(mainPanel);
        this.setVisible(true);

        /**
         * Обработчик событий кнопки
         */
        startButton.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                if(DataHandler.env_param_input_status) {

                    // Удаление окна ввода
                    dispose();

                    // Вызов окна симуляции
                    SimulationFrame simulationFrame = new SimulationFrame("ADCS - Симуляция", ServerThread);
                }
                else{

                    // Вызов диалогового окна ошибки ввода
                    JDialog dialog = new JDialog(new JFrame(), "ADCS - error");
                    dialog.setLocationRelativeTo(null);
                    JLabel lab = new JLabel("Сначала введите исходные параметры", SwingConstants.CENTER);
                    dialog.setLayout(new BorderLayout());
                    dialog.add(lab, BorderLayout.NORTH);
                    dialog.setSize(300, 100);
                    dialog.setVisible(true);
                }
            }
        });

    }

}
