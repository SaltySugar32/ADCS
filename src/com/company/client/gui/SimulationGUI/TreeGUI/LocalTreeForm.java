package com.company.client.gui.SimulationGUI.TreeGUI;

import com.company.client.gui.Database.DBHandler;
import com.company.client.gui.GUIGlobals;
import com.company.client.gui.InputGUI.CollisionGUI.Table.CollisionTableForm;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

public class LocalTreeForm extends JFrame {
    private JPanel contentPane;
    private JPanel treePanel;
    private JTable table1;

    public LocalTreeForm(){
        setTitle(GUIGlobals.program_title + " - Таблица возможных взаимодействий");
        setSize(GUIGlobals.env_param_frame_width*2, GUIGlobals.env_param_frame_height);
        this.add(contentPane);

        drawTable();
        setJMenuBar(createFileMenu());

        this.setVisible(true);
    }

    private void drawTable(){
        // Create the table
        String[] columnNames = {"Узел", "Локальное решение", "Потомки"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
        createTableDataWidth(testTree(),tableModel);
        table1.setModel(tableModel);
        setFontSize(table1, 1.5f, 20);
    }

    private void createTableData(LocalResTree node, DefaultTableModel tableModel) {
        if (node == null) return;
        String childrenMarkers = "";
        if (node.children != null)
            childrenMarkers =  node.children.stream().map(n -> String.valueOf(n.marker)).reduce((a, b) -> a + ", " + b).orElse("");
        tableModel.addRow(new Object[]{node.marker, node.result, childrenMarkers});

        if(node.children==null)
            return;

        for(LocalResTree child : node.children) {
            createTableData(child, tableModel);
        }
    }

    //Обход дерева в ширину
    private void createTableDataWidth(LocalResTree root, DefaultTableModel tableModel) {
        if (root == null) return;

        // Очередь для обхода в ширину
        Queue<LocalResTree> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            LocalResTree node = queue.poll();

            String childrenMarkers = "";
            if (node.children != null) {
                childrenMarkers = node.children.stream()
                        .map(n -> String.valueOf(n.marker))
                        .reduce((a, b) -> a + ", " + b)
                        .orElse("");
            }

            tableModel.addRow(new Object[]{node.marker, node.result, childrenMarkers});

            if (node.children != null) {
                queue.addAll(node.children);
            }
        }
    }

    // Установка размера строк и фонта
    private void setFontSize(JTable table, float scaleFactor, int rowHeight){
        Font defaultFont = table.getFont();
        float newSize = defaultFont.getSize() * scaleFactor;
        Font newFont = defaultFont.deriveFont(newSize);
        table.setFont(newFont);

        // Adjust row height to fit the window height
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        if(rowHeight>0)
            table.setRowHeight(rowHeight);
    }

    // Меню Файл
    public JMenuBar createFileMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");

        JMenuItem quickSave = new JMenuItem("Сохранить изображения");
        JMenuItem saveAs = new JMenuItem("Сохранить изображения как...");

        fileMenu.add(saveAs);
        fileMenu.add(quickSave);

        // Shortcut квиксейва CTRL + S
        KeyStroke key = KeyStroke.getKeyStroke("control S");
        quickSave.setAccelerator(key);

        quickSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //

            }
        });

        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //
            }
        });

        menuBar.add(fileMenu);
        return menuBar;
    }

    private LocalResTree testTree(){
        LocalResTree tree = new LocalResTree(1, "ξΣξ b γ b", Arrays.asList(
                new LocalResTree(2, "ξ -αΣ*γ b γ", Arrays.asList(
                        new LocalResTree(4, "ξ αΣ*γ b γ", null),
                        new LocalResTree(5, "ξ -αΣ*γ b γ", null)
                )),
                new LocalResTree(3, "ξ αΣ*γ b γ", Arrays.asList(
                        new LocalResTree(6, "ξ -αΣ*γ b γ", null),
                        new LocalResTree(7, "ξ αΣ*γ b γ", null)
                ))
        ));
        return tree;
    }
}
