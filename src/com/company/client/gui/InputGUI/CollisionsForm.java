package com.company.client.gui.InputGUI;

import com.company.client.gui.Database.DBHandler;
import com.company.client.gui.GUIGlobals;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.text.Style;
import java.awt.*;

import static java.awt.Font.*;

public class CollisionsForm extends JFrame{

    private JTable table1;
    private JPanel mainPanel;
    private JPanel tablePanel;
    private JButton saveButton;

    public CollisionsForm() {
        this.setTitle(GUIGlobals.program_title + " - Ввод параметров среды");
        this.setSize(GUIGlobals.env_param_frame_width, GUIGlobals.env_param_frame_height/2);

        this.add(mainPanel);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("X");
        for (String col : DBHandler.firstLayers) {
            tableModel.addColumn(DBHandler.formatCollisionLabel(col));
        }

        for (String row : DBHandler.secondLayers) {
            Object[] rowData = new Object[DBHandler.firstLayers.size() + 1];
            rowData[0] = DBHandler.formatCollisionLabel(row); // Имя строки


            for (int colIndex = 0; colIndex < DBHandler.firstLayers.size(); colIndex++) {
                String cellValue = DBHandler.getCollisionResult(row, DBHandler.firstLayers.get(colIndex));
                rowData[colIndex + 1] = DBHandler.formatCollisionLabel(cellValue);
            }

            tableModel.addRow(rowData);
        }

        table1 = new JTable(tableModel);
        table1.setFont(new Font("Serif", PLAIN, 20));
        JScrollPane scrollPane = new JScrollPane(table1);

        // Добавление JScrollPane в mainPanel
        tablePanel.add(scrollPane, BorderLayout.CENTER);
        saveButton.setPreferredSize(new Dimension(50,50));
        tablePanel.add(saveButton, BorderLayout.SOUTH);
        mainPanel.add(tablePanel);
        this.pack();

        this.setVisible(true);
    }
    private static void createAndShowTable(String[] columns, String[] rows) {
        JFrame frame = new JFrame("Collision Table");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("X"); // Добавляем столбец для имен строк

        // Добавляем столбцы из первой строки файла
        for (String col : columns) {
            tableModel.addColumn(col);
        }

        // Заполняем строки и ячейки таблицы
        for (String row : rows) {
            Object[] rowData = new Object[columns.length + 1];
            rowData[0] = row; // Имя строки

            for (int colIndex = 0; colIndex < columns.length; colIndex++) {
                //String cellValue = getCollisionResult(row, columns[colIndex]);
                //rowData[colIndex + 1] = cellValue;
            }

            tableModel.addRow(rowData);
        }
    }

}
