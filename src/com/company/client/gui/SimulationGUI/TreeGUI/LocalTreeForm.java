package com.company.client.gui.SimulationGUI.TreeGUI;

import com.company.client.gui.GUIGlobals;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;

public class LocalTreeForm extends JFrame {
    private mxGraph graph;
    private JPanel contentPane;
    private JPanel formPanel;
    private JTable table1;
    private JPanel treePanel;
    private LocalResTree localResTree;
    private Object parent;

    public LocalTreeForm(){
        setTitle(GUIGlobals.program_title + " - Таблица возможных взаимодействий");
        setSize(GUIGlobals.env_param_frame_width*2, GUIGlobals.env_param_frame_height);
        this.add(contentPane);

        //test
        localResTree = testTree();

        treePanel.add(drawTree(localResTree));
        System.out.println(drawTree(localResTree));

        drawTable(localResTree);

        setJMenuBar(createFileMenu());

        this.setVisible(true);
    }

    private mxGraphComponent drawTree(LocalResTree root){

        //create graph
        graph = new mxGraph();

        parent = graph.getDefaultParent();
        graph.getModel().beginUpdate();
        try{
            createGraphNodes(root,null);
        } finally{
            graph.getModel().endUpdate();
        }

        // layout graph
        mxCompactTreeLayout layout = new mxCompactTreeLayout(graph,false);
        layout.execute(parent);

        //create graph component
        mxGraphComponent graphComponent = new mxGraphComponent(graph);
        // Disables creating new connections (arrows)
        graphComponent.setConnectable(false);
        // Disables editing of all cells
        graph.setCellsEditable(false);

        // двойной клик
        graphComponent.getGraphControl().addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                Object cell = graphComponent.getCellAt(e.getX(),e.getY());
                if (cell !=null){
                    System.out.println("cell=" + graph.getLabel(cell));
                }
            }
        });

        // Set margin with a border
        graphComponent.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));

        graph.setCellsEditable(false); // Отключение редактирования ячеек
        graph.setCellsMovable(false);  // Отключение перетаскивания ячеек
        graph.setCellsResizable(false); // Отключение изменения размеров ячеек
        graph.setEdgeLabelsMovable(false); // Отключение перетаскивания меток ребер
        graph.setAllowDanglingEdges(false); // Отключение создания висячих ребер

        return graphComponent;
    }

    private void createGraphNodes(LocalResTree node, Object parentCell) {
        Object cell = graph.insertVertex(parent, null, "Узел " + node.marker + ": " + node.result, 0, 0, 80, 30);
        if (parentCell != null) {
            graph.insertEdge(parent, null, "", parentCell, cell);
        }
        if (node.children==null) return;
        for (LocalResTree child : node.children) {
            createGraphNodes(child, cell);
        }
    }

    private void drawTable(LocalResTree root){
        // Create the table
        String[] columnNames = {"Узел", "Локальное решение", "Потомки"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);
        createTableDataWidth(root,tableModel);
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
