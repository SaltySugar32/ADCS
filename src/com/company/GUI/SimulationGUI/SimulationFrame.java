package com.company.GUI.SimulationGUI;

import com.company.GUI.Database.DBHandler;
import com.company.GUI.GUIGlobals;
import com.company.thread_organization.SimulationSynchronizerThread;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYSeries;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Окно симуляции
 */
public class SimulationFrame extends JFrame {

    GraphsPanel graphsPanel = new GraphsPanel();
    /**
     * Главное окно симуляции
     *
     * @param ServerThread Поток
     */
    public SimulationFrame(SimulationSynchronizerThread ServerThread){

        this.setTitle("ADCS - Симуляция");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setSize(GUIGlobals.graph_frame_width,GUIGlobals.graph_frame_height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        // Меню
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        this.setJMenuBar(menuBar);

        // Панель с графиками
        this.add(graphsPanel);

        // Панель с ползунками и кнопками
        ParamsPanel paramsPanel = new ParamsPanel(ServerThread);
        this.add(paramsPanel, BorderLayout.SOUTH);
    }

    /**
     * Создание меню "Файл"
     * @return
     */
    public JMenu createFileMenu(){
        JMenu fileMenu = new JMenu("Файл");
        JMenuItem quickSave = new JMenuItem("Сохранить изображения");
        JMenuItem saveAs = new JMenuItem("Сохранить изображения как...");

        fileMenu.add(quickSave);
        //fileMenu.add(saveAs);

        // Shortcut квиксейва CTRL + S
        KeyStroke key = KeyStroke.getKeyStroke("control S");
        quickSave.setAccelerator(key);

        quickSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quickSaveChart(graphsPanel.chart1, graphsPanel.chartPanel1, "_деформации");
                quickSaveChart(graphsPanel.chart2, graphsPanel.chartPanel2, "_перемещения");
            }
        });

        return  fileMenu;
    }

    /**
     * Быстрое сохранение изображений
     * @param chart
     * @param panel
     */
    private void quickSaveChart(JFreeChart chart, ChartPanel panel, String chart_type){
        String path = "data/outputImages/";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String file_path = path + timeStamp  + chart_type + ".png";

        DBHandler.SaveChart(chart, panel, file_path);
    }

    /**
     * Сохранение изображения как png файл
     * @param chart
     * @param panel
     */
    private void saveChartAsPNG(JFreeChart chart, ChartPanel panel){
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл для сохранения");
        fileChooser.setFileFilter(new FileNameExtensionFilter("Folder", ""));

        // Окно выбора файла
        int userSelection = fileChooser.showSaveDialog(parentFrame);

        File folderToSave = null;
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            folderToSave = fileChooser.getSelectedFile();
        }

        // сохранение графика как PNG файл
        if(folderToSave!=null) {
            String file_path = folderToSave.getAbsolutePath().toString();

            if(!file_path.endsWith(".png"))
                file_path += ".png";

            DBHandler.SaveChart(chart, panel, file_path);
        }
    }

}
