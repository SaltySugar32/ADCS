package com.company.client.gui.InputGUI.CollisionGUI;

import com.company.client.gui.DataHandler;
import com.company.client.gui.Database.DBHandler;
import com.company.client.gui.Database.Material;
import com.company.client.gui.InputGUI.EnvParamGUI.EnvParamForm;

import javax.swing.*;
import java.awt.event.*;

public class AddFrontDialog extends JDialog {
        private JPanel contentPane;
        private JPanel param1Panel;
        private JTextField textField1;
        private JButton SubmitButton;

        public AddFrontDialog(CollisionTableForm parent, int type) {
            add(contentPane);
            setVisible(true);
            setLocationRelativeTo(null);
            setAlwaysOnTop(true);
            if(type==0)
                setTitle("Добавление догоняющего фронта");
            else
                setTitle("Добавление убегающего фронта");

            setSize(400, 200);

            SubmitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = textField1.getText();
                    JFrame frame = new JFrame();
                    frame.setAlwaysOnTop(true);

                    if (!name.isEmpty()){
                        // add front
                        if (addFront(type, name)) {
                            JOptionPane.showMessageDialog(frame, "Фронт добавлен");
                            // repaint
                            parent.updateTable();
                            dispose();
                        }
                        else JOptionPane.showMessageDialog(frame, "Ошибка ввода");
                    }
                    else JOptionPane.showMessageDialog(frame, "Введите название фронта");
                }
            });
        }

        private boolean addFront(int type, String name){
            // validation
            if (type == 0)
                return DBHandler.addFirstLayer(name);
            else
                return DBHandler.addSecondLayer(name);
        }
    }
