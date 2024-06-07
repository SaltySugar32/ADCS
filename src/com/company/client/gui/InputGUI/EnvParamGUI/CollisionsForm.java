package com.company.client.gui.InputGUI.EnvParamGUI;

import com.company.client.gui.Database.CollisionDesc;
import com.company.client.gui.Database.DBHandler;
import com.company.client.gui.GUIGlobals;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CollisionsForm extends JFrame{

    public CollisionsForm() {
        this.setTitle(GUIGlobals.program_title + " - Ввод параметров среды");
        this.setSize(GUIGlobals.env_param_frame_width, GUIGlobals.env_param_frame_height);

        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("X");
        for (String col : DBHandler.firstLayers) {
            tableModel.addColumn(col);
        }

        for (String row : DBHandler.secondLayers) {
            Object[] rowData = new Object[DBHandler.firstLayers.size() + 1];
            rowData[0] = row; // Имя строки


            for (int colIndex = 0; colIndex < DBHandler.firstLayers.size(); colIndex++) {
                String cellValue = DBHandler.getCollisionResult(row, DBHandler.firstLayers.get(colIndex));
                rowData[colIndex + 1] = cellValue;
            }

            tableModel.addRow(rowData);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);
        this.add(scrollPane, BorderLayout.CENTER);
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
