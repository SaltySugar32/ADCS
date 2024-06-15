package com.company.client.gui.InputGUI.CollisionGUI;

import com.company.client.gui.Database.CollisionDesc;
import com.company.client.gui.Database.DBHandler;
import com.company.client.gui.GUIGlobals;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.util.ArrayList;

public class CollisionTableForm extends JFrame {
    private JTable table;
    private DefaultTableModel tableModel;

    private ArrayList<String> firstLayers;
    private ArrayList<String> secondLayers;

    private ArrayList<CollisionDesc> collisionDescs;

    public CollisionTableForm(){
        setTitle(GUIGlobals.program_title + " - Таблица возможных взаимодействий");
        setSize(GUIGlobals.env_param_frame_width, GUIGlobals.env_param_frame_height);

        getCollisions();
        setJMenuBar(createFileMenu());


        this.add(createScrollPane(), BorderLayout.CENTER);

        this.setVisible(true);
    }

    private void getCollisions(){
        DBHandler.getAllCollisions();
        this.firstLayers = DBHandler.firstLayers;
        this.secondLayers = DBHandler.secondLayers;
        this.collisionDescs = DBHandler.collissionDescs;
    }

    private JMenuBar createFileMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");

        JMenu trailingFrontMenu = new JMenu("Догоняющий фронт");
        JMenuItem addTrailingFront = new JMenuItem("Добавить фронт");
        JMenuItem removeTrailingFront = new JMenuItem("Удалить фронт");

        trailingFrontMenu.add(addTrailingFront);
        trailingFrontMenu.add(removeTrailingFront);

        JMenu leadingFrontMenu = new JMenu("Убегающий фронт");
        JMenuItem addLeadingFront = new JMenuItem("Добавить фронт");
        JMenuItem removeLeadingFront = new JMenuItem("Удалить фронт");

        leadingFrontMenu.add(addLeadingFront);
        leadingFrontMenu.add(removeLeadingFront);

        fileMenu.add(trailingFrontMenu);
        fileMenu.add(leadingFrontMenu);

        menuBar.add(fileMenu);
        return (menuBar);
    }

    private DefaultTableModel createTableModel(){
        DefaultTableModel tableModel = new ReadOnlyTableModel();
        tableModel.addColumn("X");

        for (String col : firstLayers) {
            tableModel.addColumn(DBHandler.formatCollisionLabel(col));
        }

        for (String row : secondLayers) {
            Object[] rowData = new Object[firstLayers.size() + 1];
            rowData[0] = DBHandler.formatCollisionLabel(row);

            for (int colIndex = 0; colIndex < firstLayers.size(); colIndex++) {
                String cellValue = DBHandler.getCollisionResult(row, firstLayers.get(colIndex));
                rowData[colIndex + 1] = DBHandler.formatCollisionLabel(cellValue);
            }

            tableModel.addRow(rowData);
        }
        return tableModel;
    }

    // Custom table model to make cells non-editable
    class ReadOnlyTableModel extends DefaultTableModel {
        @Override
        public boolean isCellEditable(int row, int column) {
            return false;
        }
    }

    private JScrollPane createScrollPane(){
        table = new JTable(createTableModel());
        table.setDefaultRenderer(Object.class, new CustomTableCellRenderer(collisionDescs));
        // Обработчик двойного клика по ячейке
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                int row = table.rowAtPoint(evt.getPoint());
                int col = table.columnAtPoint(evt.getPoint());
                if (row >= 0 && col >= 1 && evt.getClickCount() == 2) { // Проверяем, что клик был двойным и по ячейке с данными
                    String cellValue = (String) table.getValueAt(row, col);
                    if (cellValue != null && !cellValue.isEmpty()) {
                        // Открываем диалоговое окно
                        SetCollisionDialog dialog = new SetCollisionDialog();
                    }
                }
            }
        });

        setTableFontSize(1.5F, table);
        // Adjust row height to fit the window height
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        table.setRowHeight(100);

        // Set header background color
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel headerLabel = (JLabel) super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                headerLabel.setBackground(GUIGlobals.background_color);
                headerLabel.setHorizontalAlignment(JLabel.CENTER);
                return headerLabel;
            }
        });
        // Set row header background color
        DefaultTableCellRenderer rowHeaderRenderer = new DefaultTableCellRenderer();
        rowHeaderRenderer.setBackground(GUIGlobals.background_color);
        rowHeaderRenderer.setHorizontalAlignment(JLabel.CENTER);
        table.getColumnModel().getColumn(0).setCellRenderer(rowHeaderRenderer);

        return new JScrollPane(table);
    }

    private void adjustRowHeight(JTable table) {
        int totalRowHeight = table.getRowHeight() * table.getRowCount();
        int availableHeight = getHeight() - table.getTableHeader().getHeight();
        if (totalRowHeight < availableHeight) {
            int newHeight = availableHeight / table.getRowCount();
            table.setRowHeight(newHeight);
        }
    }

    private void setTableFontSize(float scaleFactor, JTable table) {
        Font defaultFont = table.getFont();
        float newSize = defaultFont.getSize() * scaleFactor;
        Font newFont = defaultFont.deriveFont(newSize);
        table.setFont(newFont);

        // Также применим новый шрифт к заголовкам столбцов
        JTableHeader tableHeader = table.getTableHeader();
        tableHeader.setFont(newFont);

        // Обновим размер строк, чтобы учесть новый размер шрифта
        table.setRowHeight((int) (newFont.getSize() * 1.5));
    }

    private static class CustomTableCellRenderer extends DefaultTableCellRenderer {
        private ArrayList<CollisionDesc> collisionDescs;

        public CustomTableCellRenderer(ArrayList<CollisionDesc> collisionDescs) {
            this.collisionDescs = collisionDescs;
        }

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int col) {
            Component cell = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, col);
            if (value != null && !value.toString().isEmpty()) {
                String cellValue = value.toString();
                for (CollisionDesc desc : collisionDescs) {
                    if (desc.shortDescription.equals(cellValue) && desc.resultLayers.size() > 1) {
                        cell.setBackground(Color.YELLOW); // Цвет для ячеек с несколькими элементами
                    } else {
                        cell.setBackground(Color.WHITE);
                    }
                }
            } else {
                cell.setBackground(Color.WHITE);
            }
            return cell;
        }
    }
}
