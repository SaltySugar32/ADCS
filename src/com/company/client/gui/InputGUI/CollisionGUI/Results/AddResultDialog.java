package com.company.client.gui.InputGUI.CollisionGUI.Results;

import com.company.client.gui.Database.DBHandler;
import com.company.client.gui.InputGUI.CollisionGUI.Table.CollisionTableForm;
import com.company.server.simulation.collision.CollisionSwitcher;

import javax.swing.*;
import java.awt.event.*;

public class AddResultDialog extends JDialog {
    private JPanel contentPane;
    private JPanel param1Panel;
    private JTextField textField1;
    private JButton SubmitButton;

    public AddResultDialog(CollisionResultForm parent, int colIndex, int rowIndex, String collisionLabel) {
        add(contentPane);
        setVisible(true);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setTitle("Добавление результата взаимодействия для " + collisionLabel);

        setSize(400, 200);

        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = textField1.getText();
                JFrame frame = new JFrame();
                frame.setAlwaysOnTop(true);

                if (!name.isEmpty()){
                    // add front
                    if (DBHandler.addCollisionResult(colIndex, rowIndex, name)) {
                        JOptionPane.showMessageDialog(frame, "Результат взаимодействия добавлен");

                        DBHandler.getAllCollisions();
                        CollisionSwitcher.initCollisionHandlers_new();
                        // repaint
                        parent.updateTable();
                    }
                    else JOptionPane.showMessageDialog(frame, "Ошибка ввода");
                }
                else JOptionPane.showMessageDialog(frame, "Введите название фронта");
            }
        });
    }
}