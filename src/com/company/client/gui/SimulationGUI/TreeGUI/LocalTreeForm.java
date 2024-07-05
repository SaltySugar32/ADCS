package com.company.client.gui.SimulationGUI.TreeGUI;

import com.company.client.gui.GUIGlobals;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
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

        this.setVisible(true);
    }

    private void drawTable(){
        // Create the table
        String[] columnNames = {"Узел", "Лок. реш.", "Потом"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
        createTableDataWidth(testTree(),tableModel);
        table1.setModel(tableModel);
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
