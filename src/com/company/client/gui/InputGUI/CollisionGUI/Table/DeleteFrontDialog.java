package com.company.client.gui.InputGUI.CollisionGUI.Table;

import com.company.client.gui.Database.DBHandler;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DeleteFrontDialog extends JDialog {
    private JPanel contentPane;
    private JPanel param1Panel;
    private JButton SubmitButton;
    private JComboBox comboBox1;

    public DeleteFrontDialog(CollisionTableForm parent, int type) {
        add(contentPane);
        setVisible(true);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        if (type == 0)
            setTitle("Удаление догоняющего фронта");
        else
            setTitle("Удаление убегающего фронта");

        setSize(400, 200);

        loadComboBox(type);

        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = comboBox1.getSelectedIndex();

                JFrame frame = new JFrame();
                frame.setAlwaysOnTop(true);

                if (index>=0) {
                    // delete front
                    if (deleteFront(type, index)) {
                        JOptionPane.showMessageDialog(frame, "Фронт удален");
                        // repaint
                        parent.updateTable();
                        loadComboBox(type);
                    } else JOptionPane.showMessageDialog(frame, "Ошибка удаления");
                } else JOptionPane.showMessageDialog(frame, "Фронтов нет");
            }
        });
    }

    // инициализация комбобокса (меню с выбором фронта)
    public void loadComboBox(int type){
        comboBox1.removeAllItems();
        ArrayList<String> fronts = (type==0)? DBHandler.firstLayers:DBHandler.secondLayers;
        for(String front:fronts)
            comboBox1.addItem(front);
    }

    public boolean deleteFront(int type, int index){
        if (type==0)
            return DBHandler.deleteFirstLayer(DBHandler.firstLayers.get(index));
        else
            return DBHandler.deleteSecondLayer(DBHandler.secondLayers.get(index));
    }
}
