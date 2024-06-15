package com.company.client.gui.InputGUI.CollisionGUI;

import com.company.client.gui.Database.CollisionDesc;
import com.company.client.gui.Database.DBHandler;
import com.company.client.gui.GUIGlobals;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Set;

public class SetCollisionDialog extends JDialog {
    private CollisionDesc collisionDesc;
    private JTable table;
    public SetCollisionDialog(int colIndex, int rowIndex){
        setTitle("Возможные взаимодействия");
        setSize(400, 400);

        getCollision(colIndex-1, rowIndex);

        if (collisionDesc != null)
            //this.add(createScrollPane(), BorderLayout.CENTER);

        setJMenuBar(createFileMenu());
        setVisible(true);
        this.setAlwaysOnTop(true);
    }

    private void getCollision(int col, int row){
        DBHandler.getAllCollisions();
        this.collisionDesc = DBHandler.getCollision(col, row);
    }

    private JMenuBar createFileMenu(){
        // Создание меню "Файл"
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");

        // Создание пунктов подменю
        JMenuItem addCollisionItem = new JMenuItem("Добавить");
        JMenuItem deleteItem = new JMenuItem("Удалить");

        // Добавление обработчиков для пунктов меню
        addCollisionItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Действие при выборе "Добавить коллизию"
                // Например, открытие диалога для добавления коллизии
                JOptionPane.showMessageDialog(SetCollisionDialog.this,
                        "Функционал добавления коллизии еще не реализован", "Предупреждение",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Действие при выборе "Удалить"
                // Например, удаление выбранной коллизии
                JOptionPane.showMessageDialog(SetCollisionDialog.this,
                        "Функционал удаления коллизии еще не реализован", "Предупреждение",
                        JOptionPane.WARNING_MESSAGE);
            }
        });

        // Добавление пунктов в меню "Файл"
        fileMenu.add(addCollisionItem);
        fileMenu.add(deleteItem);

        // Добавление меню "Файл" в строку меню
        menuBar.add(fileMenu);
        return menuBar;
    }

    /*
    private JScrollPane createScrollPane(){
            String[] columnNames = {"Collision", "Action"};
            DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);

            for (int i = 0; i < collisionDesc.resultLayers.size(); i++) {
                String result = collisionDesc.resultLayers.get(i);
                JButton button = new JButton(i == 0 ? "Задано" : "Задать");
                button.setEnabled(i != 0);
                tableModel.addRow(new Object[]{result, button});
            }

            table = new JTable(tableModel);
            table.getColumn("Action").setCellRenderer(new ButtonRenderer());
            table.getColumn("Action").setCellEditor(new ButtonEditor(new JCheckBox()));

            // Highlight the first row
            DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
            renderer.setBackground(GUIGlobals.background_color);
            table.getColumnModel().getColumn(0).setCellRenderer(renderer);

            return new JScrollPane(table);
    }
    */
}
