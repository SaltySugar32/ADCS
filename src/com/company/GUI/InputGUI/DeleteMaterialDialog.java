package com.company.GUI.InputGUI;

import com.company.GUI.DB.DBHandler;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DeleteMaterialDialog extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JLabel label1;

    public DeleteMaterialDialog(EnvParamForm frame, int index) {
        add(contentPane);
        setVisible(true);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setTitle("ADCS - Удаление материала");
        setSize(400, 200);
        label1.setText("Удалить материал: "+frame.comboBox1.getItemAt(index).toString()+"?");

        buttonOK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DBHandler.deleteMaterial(index - 1);
                frame.comboBox1.removeItemAt(index);
                dispose();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

    }
}
