package com.company.GUI.SimulationGUI;

import com.company.GUI.Database.DBHandler;
import com.company.GUI.GUIGlobals;
import com.company.simulation.inter_process_functions.border_handlers.Border;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.thread_organization.SimulationSynchronizerThread;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimerTask;
import java.util.Timer;
import java.util.List;


/**
 * Окно симуляции
 */
public class SimulationFrame extends JFrame {

    GraphsPanel graphsPanel = new GraphsPanel();
    ParamsPanel paramsPanel;
    /**
     * Главное окно симуляции
     *
     * @param ServerThread Поток
     */
    public SimulationFrame(SimulationSynchronizerThread ServerThread){

        this.setTitle("ADCS - Симуляция");
        this.setSize(GUIGlobals.graph_frame_width,GUIGlobals.graph_frame_height);
        this.setLocationRelativeTo(null);
        this.setVisible(true);

        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                ServerThread.setNextJobSTOP();
                super.windowClosing(e);
            }
        });

        // Меню
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        this.setJMenuBar(menuBar);

        // Панель с графиками
        this.add(graphsPanel);

        // Панель с ползунками и кнопками
        paramsPanel = new ParamsPanel(ServerThread);
        this.add(paramsPanel, BorderLayout.SOUTH);

        // Таймер - чтение данных из решателя
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                drawOutput();
            }
        };

        Timer timer = new Timer();
        timer.schedule(task, 0, 1000);
    }

    public void drawOutput(){
        // тест
        graphsPanel.series1.clear();
        graphsPanel.series2.clear();

        List<LayerDescription> layerDescriptions = SimulationGlobals.getCurrentWavePicture();
        graphsPanel.series1.add(0, Border.getCurrentBorderDisplacement());

        for (int index = 0; index < layerDescriptions.size(); index++) {
            graphsPanel.series1.add(layerDescriptions.get(index).getCurrentX() , layerDescriptions.get(index).calculateDisplacement());

            // график перемещений
            if (index > 0)
                graphsPanel.series2.add(layerDescriptions.get(index - 1).getCurrentX(), layerDescriptions.get(index).getA2());
            else
                graphsPanel.series2.add(0.0, layerDescriptions.get(index).getA2());

            graphsPanel.series2.add(layerDescriptions.get(index).getCurrentX(), layerDescriptions.get(index).getA2());
        }

        if (layerDescriptions.size() > 0)
            graphsPanel.series2.add(layerDescriptions.get(layerDescriptions.size() - 1).getCurrentX(), 0.0);

        double maxX1 = Math.max(graphsPanel.series1.getMaxX(), 0.1) * 1.1;
        double maxY1 = Math.max(graphsPanel.series1.getMaxY(), 0.1) * 1.1;
        double minY1 = Math.min(graphsPanel.series1.getMinY(), 0.1) * 1.1;

        double maxX2 = Math.max(0.0, graphsPanel.series2.getMaxX()) * 1.1;
        double maxY2 = Math.max(0.0, graphsPanel.series2.getMaxY()) * 1.1;
        double minY2 = Math.min(0.0, graphsPanel.series2.getMinY()) * 1.1;

        graphsPanel.setGraphAxis(graphsPanel.chart1, 0, maxX1, minY1, maxY1);
        graphsPanel.setGraphAxis(graphsPanel.chart2, 0, maxX2, minY2, maxY2);

        /*
        double maxX = Math.max(graphsPanel.series1.getMaxX(), graphsPanel.series2.getMaxX()) * 1.1;
        double maxY = Math.max(graphsPanel.series1.getMaxY(), graphsPanel.series2.getMaxY()) * 1.1;
        double minY = Math.min(graphsPanel.series1.getMinY(), graphsPanel.series2.getMinY()) * 1.1;

        graphsPanel.setGraphAxis(graphsPanel.chart1, 0, maxX, minY, maxY);
        graphsPanel.setGraphAxis(graphsPanel.chart2, 0, maxX, minY, maxY);

         */

        String time = Double.toString(SimulationTime.getSimulationTime());
        paramsPanel.simulationTime.setText(time);
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
