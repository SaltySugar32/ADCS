package com.company.client.gui.SimulationGUI.TreeGUI;

import com.company.client.gui.GUIGlobals;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.model.mxCell;
import com.mxgraph.model.mxGeometry;
import com.mxgraph.swing.mxGraphComponent;
import com.mxgraph.view.mxGraph;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
    private boolean showFullTable = true;

    public LocalTreeForm(){
        setTitle(GUIGlobals.program_title + " - Таблица возможных взаимодействий");
        setSize(GUIGlobals.env_param_frame_width*2, GUIGlobals.env_param_frame_height);
        this.add(contentPane);

        //test
        localResTree = testTree();

        treePanel.add(drawTree(localResTree));
        System.out.println(drawTree(localResTree));

        drawTable(localResTree);

        setJMenuBar(createMenu());

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

        // Расположение графа
        mxCompactTreeLayout layout = new mxCompactTreeLayout(graph,false);
        // Прямые линии
        layout.setEdgeRouting(false);
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
                    toggleNode(cell);
                    System.out.println("cell=" + graph.getLabel(cell));
                }
            }
        });

        // Enable panning and zooming
        graphComponent.setPanning(true);
        //graphComponent.setZoomEnabled(true);
        graphComponent.getGraphControl().addMouseWheelListener(new MouseWheelListener() {
            @Override
            public void mouseWheelMoved(MouseWheelEvent e) {
                if (e.getWheelRotation() < 0) {
                    graphComponent.zoomIn();
                } else {
                    graphComponent.zoomOut();
                }
            }
        });

        // Set margin with a border
        graphComponent.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
/*
        graph.setCellsEditable(false); // Отключение редактирования ячеек
        graph.setCellsMovable(false);  // Отключение перетаскивания ячеек
        graph.setCellsResizable(false); // Отключение изменения размеров ячеек
        graph.setEdgeLabelsMovable(false); // Отключение перетаскивания меток ребер
        graph.setAllowDanglingEdges(false); // Отключение создания висячих ребер
*/
        graphComponent.setConnectable(false);
        graph.setCellsEditable(false);
        graph.setAllowDanglingEdges(false);
        graph.setCellsDisconnectable(false);
        graph.setCellsDeletable(false);
        graph.setCellsMovable(false);
        graph.setEdgeLabelsMovable(false);
        graph.setVertexLabelsMovable(false);
        graph.setDropEnabled(false);
        graph.setSplitEnabled(false);

        return graphComponent;
    }

    private void createGraphNodes(LocalResTree node, Object parentCell) {
        // bug
        String label = node.isCollapsed ? "..." : String.valueOf(node.marker);
        Object cell = graph.insertVertex(parent, null, label, 0, 0, 40, 30, "shape=none;labelPosition=center;verticalLabelPosition=middle;fontSize=12");

        /// Сохраняем маркер как атрибут узла
        ((mxCell) cell).setValue(new CellValue(node.marker, label));

        if (parentCell != null) {
            graph.insertEdge(parent, null, "", parentCell, cell, "endArrow=none;edgeStyle=none;");
        }
        if (node.children == null || node.isCollapsed) return;
        for (LocalResTree child : node.children) {
            createGraphNodes(child, cell);
        }
    }

    private void toggleNode(Object cell) {
        // Получаем пользовательский объект из узла
        Object value = ((mxCell) cell).getValue();
        if (value instanceof CellValue) {
            int marker = ((CellValue) value).marker;
            LocalResTree node = findNodeByMarker(localResTree, marker);
            if (node != null) {
                node.isCollapsed = !node.isCollapsed;

                // Переотрисовка дерева
                treePanel.removeAll();
                treePanel.add(drawTree(localResTree));
                treePanel.revalidate();
                treePanel.repaint();

                if (!showFullTable) {
                    drawTable(localResTree);
                }
            }
        }
    }

    private LocalResTree findNodeByMarker(LocalResTree node, int marker) {
        if (node.marker == marker) {
            return node;
        }
        if (node.children != null) {
            for (LocalResTree child : node.children) {
                LocalResTree found = findNodeByMarker(child, marker);
                if (found != null) {
                    return found;
                }
            }
        }
        return null;
    }

    private void drawTable(LocalResTree root){
        // Create the table
        String[] columnNames = {"Узел", "Локальное решение", "Потомки"};
        DefaultTableModel tableModel = new DefaultTableModel(columnNames,0);

        createTableDataWidth(root, tableModel);

        table1.setModel(tableModel);
        setFontSize(table1, 1.5f, 20);
    }

    //Обход дерева в ширину
    private void createTableDataWidth(LocalResTree root, DefaultTableModel tableModel) {
        if (root == null) return;

        // Очередь для обхода в ширину
        Queue<LocalResTree> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            LocalResTree node = queue.poll();

            if (showFullTable || (!node.isCollapsed)) {
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
    }

    // Установка размера строк и фонта
    private void setFontSize(JTable table, float scaleFactor, int rowHeight){
        JTable temp = new JTable();
        Font defaultFont =temp.getFont();
        float newSize = defaultFont.getSize() * scaleFactor;

        Font newFont = defaultFont.deriveFont(newSize);
        table.setFont(newFont);

        // Adjust row height to fit the window height
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        if(rowHeight>0)
            table.setRowHeight(rowHeight);
    }

    // Меню Файл
    public JMenuBar createMenu(){
        JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("Файл");
        JMenu viewMenu = new JMenu("Вид");

        JMenuItem saveTreeAsPNG = new JMenuItem("Сохранить дерево как изображение PNG");
        JMenuItem saveTableAsPNG = new JMenuItem("Сохранить таблицу как изображение PNG");
        fileMenu.add(saveTreeAsPNG);
        fileMenu.add(saveTableAsPNG);

        JMenuItem toggleTableView = new JMenuItem("Отобразить таблицу для видимых узлов");
        viewMenu.add(toggleTableView);

        // Добавляем действие на переключение вида таблицы
        toggleTableView.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showFullTable = !showFullTable;
                toggleTableView.setText(showFullTable ? "Отобразить таблицу для видимых узлов" : "Отобразить таблицу полностью");
                drawTable(localResTree);
            }
        });

        saveTreeAsPNG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveComponentAsPNG(treePanel, "Сохранить дерево локальных решений как PNG");
            }
        });

        saveTableAsPNG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveTableAsPNG(table1);
            }
        });

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        return menuBar;
    }

    private void saveTableAsPNG(JTable table1) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Сохранить таблицу как PNG");

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".png")) {
                filePath += ".png";
            }

            // Создаем изображение с учетом заголовков
            int width = table1.getWidth();
            int height = table1.getHeight() + table1.getTableHeader().getHeight();
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();

            // Рисуем заголовок таблицы
            table1.getTableHeader().paint(g2d);
            // Рисуем таблицу ниже заголовка
            g2d.translate(0, table1.getTableHeader().getHeight());
            table1.paint(g2d);
            g2d.dispose();

            try {
                ImageIO.write(image, "png", new File(filePath));
                JOptionPane.showMessageDialog(this, "Сохранено: " + filePath);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Ошибка при сохранении файла.", "Ошибка", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void saveComponentAsPNG(JComponent component, String dialogTitle) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle(dialogTitle);

        int userSelection = fileChooser.showSaveDialog(this);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            String filePath = fileToSave.getAbsolutePath();
            if (!filePath.toLowerCase().endsWith(".png")) {
                filePath += ".png";
            }

            BufferedImage image = new BufferedImage(component.getWidth(), component.getHeight(), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2d = image.createGraphics();
            component.paint(g2d);
            g2d.dispose();

            try {
                ImageIO.write(image, "png", new File(filePath));
                JOptionPane.showMessageDialog(this, "Сохранено: " + filePath);
            } catch (IOException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Ошибка при сохранении файла.", "Ошибка", JOptionPane.ERROR_MESSAGE);
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
