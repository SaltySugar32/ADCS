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

        public AddFrontDialog(int type) {
            add(contentPane);
            setVisible(true);
            setLocationRelativeTo(null);
            setAlwaysOnTop(true);
            setTitle("Добавление фронта");
            setSize(400, 200);

            SubmitButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String name = textField1.getText();
                    if (!name.isEmpty()) {
                        /*// add front
                        if (type==0)
                            DBHandler.addFirstLayer(name);
                        else
                            DBHandler.addSecondLayer(name);
*/
                        // repaint

                        dispose();
                    }
                }
            });
        }
    }
