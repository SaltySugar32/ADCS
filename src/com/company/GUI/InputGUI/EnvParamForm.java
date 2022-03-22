package com.company.GUI.InputGUI;

import com.company.GUI.DataHandler;
import com.company.GUI.GUIGlobals;
import com.company.Simulation.SimulationSynchronizerThread;
import com.company.Simulation.SimulationVariables.SimulationGlobals;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnvParamForm extends JFrame{
    private JPanel mainPanel;
    private JButton setEnvParamsButton;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;
    private JTextField textField4;
    private JPanel param1Panel;
    private JPanel param2Panel;
    private JPanel param3Panel;
    private JPanel param4Panel;
    private JLabel statusLabel;

    public EnvParamForm(JLabel mainFrameLabel, SimulationSynchronizerThread ServerThread) {
        this.setTitle("ADCS - Ввод параметров среды");
        this.setSize(GUIGlobals.env_param_frame_width, GUIGlobals.env_param_frame_height);
        this.setVisible(true);

        // позволяет появляться фрейму в центре экрана. (по дефолту - в верхнем левом углу)
        this.setLocationRelativeTo(null);

        // Главная панель со всеми кнопками
        this.add(mainPanel);
        statusLabel.setText("");

        // отображение текущих значений параметров среды
        textField1.setText(Double.toString(DataHandler.lameMu));
        textField2.setText(Double.toString(DataHandler.lameLambda));
        textField3.setText(Double.toString(DataHandler.materialDensity));
        textField4.setText(String.valueOf(DataHandler.coefficientNu));

        // Событие нажатия кнопки "задать параметры"
        setEnvParamsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // сообщение о корректности ввода
                statusLabel.setText(DataHandler.setEnvParams(
                        textField1.getText(),
                        textField2.getText(),
                        textField3.getText(),
                        textField4.getText()
                ));
                // если ввод коректный, то на главном фрейме отобразить "Задано" для параметров среды
                if (DataHandler.env_param_input_status) mainFrameLabel.setText("<html><font color='green'>Задано</font></html>");
            }
        });
    }
}
