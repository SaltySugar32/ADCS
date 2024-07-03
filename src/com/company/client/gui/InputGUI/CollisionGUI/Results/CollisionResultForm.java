package com.company.client.gui.InputGUI.CollisionGUI.Results;

import com.company.client.gui.Database.CollisionDesc;
import com.company.client.gui.Database.DBHandler;
import com.company.client.gui.GUIGlobals;
import com.company.client.gui.InputGUI.CollisionGUI.Table.CollisionTableForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class CollisionResultForm extends JFrame{
    private JPanel param1Panel;
    private JButton submitButton;
    private JPanel contentPane;
    private JTable table1;
    private JLabel resultLabel;
    private CollisionDesc collisionDesc;
    int colIndex, rowIndex;
    CollisionTableForm parent;

    public CollisionResultForm(CollisionTableForm form, int col, int row){
        setTitle("Задание результата взаимодействия");
        setSize(GUIGlobals.env_param_frame_width, GUIGlobals.env_param_frame_height);
        add(contentPane);

        colIndex = col-1;
        rowIndex = row;
        parent = form;

        setJMenuBar(createFileMenu(this));

        getCollision(colIndex, rowIndex);
        updateTable();
        setFontSize(table1, 1.5f);

        setVisible(true);
        this.setAlwaysOnTop(true);

        // Устанавливаем обработчик действия для кнопки "Задать"
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Действие при нажатии кнопки "Задать"
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    // Получаем значение выбранной ячейки
                    String collision = (String) table1.getValueAt(selectedRow, 0);
                    // Допустим, здесь будет ваш код для задания чего-то с выбранной коллизией
                    JOptionPane.showMessageDialog(CollisionResultForm.this,
                            "'" + collision + "' Задано", "Задание взаимодействия",
                            JOptionPane.INFORMATION_MESSAGE);
                    DBHandler.collisionSwapElements(collisionDesc,selectedRow);
                    DBHandler.writeCollisionsToFile();
                    // обновление полей

                    updateTable();

                } else {
                    JOptionPane.showMessageDialog(CollisionResultForm.this,
                            "Выберите коллизию для задания", "Предупреждение",
                            JOptionPane.WARNING_MESSAGE);
                }
            }
        });
    }

    private void getCollision(int col, int row){
        DBHandler.getAllCollisions();
        resultLabel.setText(
                DBHandler.formatCollisionLabel(DBHandler.firstLayers.get(row)) +
                        " → " +
                        DBHandler.formatCollisionLabel(DBHandler.secondLayers.get(col)));
        this.collisionDesc = DBHandler.getCollision(col, row);
    }

    public void updateTable(){

        parent.updateTable();
        getCollision(colIndex, rowIndex);

        // Создаем таблицу с двумя столбцами
        String[] columnNames = {"Результат", "Статус"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Ячейки только для чтения
            }
        };
        table1.setModel(tableModel);

        if(collisionDesc!=null) {
            // Получаем данные для таблицы из collisionDesc.resultLayers
            ArrayList<String> resultLayers = collisionDesc.resultLayers;
            for (int i = 0; i < resultLayers.size(); i++) {
                String collision = resultLayers.get(i);
                String status = (i == 0) ? "задан" : ""; // Устанавливаем статус "задан" только для первой строки
                Object[] rowData = {DBHandler.formatCollisionLabel(collision), status};
                tableModel.addRow(rowData);
            }
        }
    }

    private void setFontSize(JTable table, float scaleFactor){
        Font defaultFont = table.getFont();
        float newSize = defaultFont.getSize() * scaleFactor;
        Font newFont = defaultFont.deriveFont(newSize);
        table.setFont(newFont);

        // Adjust row height to fit the window height
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowHeight(100);
    }

    private JMenuBar createFileMenu(CollisionResultForm form) {
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
                AddResultDialog dialog = new AddResultDialog(form,colIndex, rowIndex,resultLabel.getText());
            }
        });

        deleteItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Действие при выборе "Удалить"
                DeleteResultDialog dialog = new DeleteResultDialog(form,colIndex,rowIndex,resultLabel.getText());
            }
        });
        // Добавление пунктов в меню "Файл"
        fileMenu.add(addCollisionItem);
        fileMenu.add(deleteItem);

        // Добавление меню "Файл" в строку меню
        menuBar.add(fileMenu);
        return menuBar;
    }
}
