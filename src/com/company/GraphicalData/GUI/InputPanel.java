package com.company.GraphicalData.GUI;

import javax.swing.*;
import java.awt.*;

public class InputPanel extends JPanel {
    private JLabel label1;
    private JLabel label2;
    private JLabel label3;
    private JTextField textField1;
    private JTextField textField2;
    private JTextField textField3;

    //Панель ввода исходных параметров
    public InputPanel(){
        label1 = new JLabel("введите параметр 1");
        label2 = new JLabel("введите параметр 2");
        label3 = new JLabel("введите параметр 3");
        textField1 = new JTextField(15);
        textField2 = new JTextField(15);
        textField3 = new JTextField(15);

        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        this.add(label1);
        this.add(label2);
        this.add(label3);
        this.add(textField1);
        this.add(textField2);
        this.add(textField3);

        layout.putConstraint(SpringLayout.WEST , label1, 10, SpringLayout.WEST , this);
        layout.putConstraint(SpringLayout.NORTH, label1, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.NORTH, textField1, 10, SpringLayout.NORTH, this);
        layout.putConstraint(SpringLayout.WEST , textField1, 20, SpringLayout.EAST , label1);

        layout.putConstraint(SpringLayout.WEST , label2, 10, SpringLayout.WEST , this);
        layout.putConstraint(SpringLayout.NORTH, label2, 15, SpringLayout.SOUTH, label1);
        layout.putConstraint(SpringLayout.NORTH, textField2, 10, SpringLayout.SOUTH, textField1);
        layout.putConstraint(SpringLayout.WEST , textField2, 20, SpringLayout.EAST , label2);

        layout.putConstraint(SpringLayout.WEST , label3, 10, SpringLayout.WEST , this);
        layout.putConstraint(SpringLayout.NORTH, label3, 15, SpringLayout.SOUTH, label2);
        layout.putConstraint(SpringLayout.NORTH, textField3, 10, SpringLayout.SOUTH, textField2);
        layout.putConstraint(SpringLayout.WEST , textField3, 20, SpringLayout.EAST , label3);
    }
}
