package com.company.GraphicalData.InputGUI;

import com.company.GraphicalData.DataHandler;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class InputPanel extends JPanel {
    private JLabel label_lameMu = new JLabel("Параметр Mu (Мю)");
    private JLabel label_lameLambda = new JLabel("Параметр Ламе Lambda (Лямбда)");
    private JLabel label_materialDensity = new JLabel("Параметр плотность материала");
    private JLabel label_coefficientNu = new JLabel("Параметр Nu (Ню)");

    private JTextField textField_lameMu = new JTextField(15);
    private JTextField textField_lameLambda = new JTextField(15);
    private JTextField textField_materialDensity = new JTextField(15);
    private JTextField textField_coefficientNu = new JTextField(15);

    private JButton button_input= new JButton("Ввести параметры среды");
    private JLabel label_input = new JLabel("");

    //Панель ввода исходных параметров
    public InputPanel(){
        JPanel inputParamPanel = new JPanel();
        SpringLayout layout = new SpringLayout();
        inputParamPanel.setLayout(layout);

        inputParamPanel.add(label_lameMu);
        inputParamPanel.add(label_lameLambda);
        inputParamPanel.add(label_materialDensity);
        inputParamPanel.add(label_coefficientNu);
        inputParamPanel.add(textField_lameMu);
        inputParamPanel.add(textField_lameLambda);
        inputParamPanel.add(textField_materialDensity);
        inputParamPanel.add(textField_coefficientNu);
        inputParamPanel.add(label_input);

        label_input.setForeground(Color.RED);
        button_input.setPreferredSize(new Dimension(30,30));

        this.setLayout(new BorderLayout());
        this.add(inputParamPanel);
        this.add(button_input, BorderLayout.SOUTH);


        layout.putConstraint(SpringLayout.WEST , label_lameMu, 30, SpringLayout.WEST , inputParamPanel);
        layout.putConstraint(SpringLayout.NORTH, label_lameMu, 10, SpringLayout.NORTH, inputParamPanel);
        layout.putConstraint(SpringLayout.NORTH, textField_lameMu, 10, SpringLayout.NORTH, inputParamPanel);
        layout.putConstraint(SpringLayout.WEST , textField_lameMu, 20, SpringLayout.EAST , label_materialDensity);

        layout.putConstraint(SpringLayout.WEST , label_lameLambda, 30, SpringLayout.WEST , inputParamPanel);
        layout.putConstraint(SpringLayout.NORTH, label_lameLambda, 15, SpringLayout.SOUTH, label_lameMu);
        layout.putConstraint(SpringLayout.NORTH, textField_lameLambda, 10, SpringLayout.SOUTH, textField_lameMu);
        layout.putConstraint(SpringLayout.WEST , textField_lameLambda, 20, SpringLayout.EAST , label_materialDensity);

        layout.putConstraint(SpringLayout.WEST , label_materialDensity, 30, SpringLayout.WEST , inputParamPanel);
        layout.putConstraint(SpringLayout.NORTH, label_materialDensity, 15, SpringLayout.SOUTH, label_lameLambda);
        layout.putConstraint(SpringLayout.NORTH, textField_materialDensity, 10, SpringLayout.SOUTH, textField_lameLambda);
        layout.putConstraint(SpringLayout.WEST , textField_materialDensity, 20, SpringLayout.EAST , label_materialDensity);

        layout.putConstraint(SpringLayout.WEST , label_coefficientNu, 30, SpringLayout.WEST , inputParamPanel);
        layout.putConstraint(SpringLayout.NORTH, label_coefficientNu, 15, SpringLayout.SOUTH, label_materialDensity);
        layout.putConstraint(SpringLayout.NORTH, textField_coefficientNu, 10, SpringLayout.SOUTH, textField_materialDensity);
        layout.putConstraint(SpringLayout.WEST , textField_coefficientNu, 20, SpringLayout.EAST , label_materialDensity);

        layout.putConstraint(SpringLayout.WEST , label_input, 50, SpringLayout.EAST , textField_lameMu);
        layout.putConstraint(SpringLayout.NORTH, label_input, 50, SpringLayout.NORTH, inputParamPanel);


        button_input.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent ae){
                label_input.setText(DataHandler.setInputParams(
                        textField_lameMu.getText(),
                        textField_lameLambda.getText(),
                        textField_materialDensity.getText(),
                        textField_coefficientNu.getText()
                        ));
            }
        });
    }
}
