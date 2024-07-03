package com.company.client.gui.InputGUI.CollisionGUI.Results;

import com.company.client.gui.Database.CollisionDesc;
import com.company.client.gui.Database.DBHandler;
import com.company.client.gui.InputGUI.CollisionGUI.Table.CollisionTableForm;

import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;

public class DeleteResultDialog extends JDialog {
    private JPanel contentPane;
    private JPanel param1Panel;
    private JButton SubmitButton;
    private JComboBox comboBox1;

    public DeleteResultDialog(CollisionResultForm parent, int colIndex, int rowIndex, String collisionLabel) {
        add(contentPane);
        setVisible(true);
        setLocationRelativeTo(null);
        setAlwaysOnTop(true);
        setTitle("Удаление результата взаимодействия " + collisionLabel);

        setSize(400, 200);

        loadComboBox(colIndex, rowIndex);

        SubmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int index = comboBox1.getSelectedIndex();

                JFrame frame = new JFrame();
                frame.setAlwaysOnTop(true);

                if (index>=0) {
                    // delete result
                    if (DBHandler.deleteCollisionResult(colIndex, rowIndex, index)) {
                        JOptionPane.showMessageDialog(frame, "Результат удален");
                        // repaint
                        parent.updateTable();
                        loadComboBox(colIndex, rowIndex);
                    } else JOptionPane.showMessageDialog(frame, "Ошибка удаления");
                } else JOptionPane.showMessageDialog(frame, "Результатов нет");
            }
        });
    }

    // инициализация комбобокса (меню с выбором фронта)
    public void loadComboBox(int colIndex, int rowIndex){
        comboBox1.removeAllItems();
        ArrayList<String> results = DBHandler.getCollisionResult(DBHandler.firstLayers.get(rowIndex),DBHandler.secondLayers.get(colIndex));
        if(results!=null)
            for(String res:results)
                comboBox1.addItem(res);
    }
}