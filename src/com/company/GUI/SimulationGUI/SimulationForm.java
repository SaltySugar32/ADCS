package com.company.GUI.SimulationGUI;

import com.company.GUI.Database.DBHandler;
import com.company.GUI.GUIGlobals;
import com.company.ProgramGlobals;
import com.company.simulation.inter_process_functions.border_handlers.Border;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.SimulationTime;
import com.company.simulation.simulation_types.layer_description.LayerDescription;
import com.company.thread_organization.SimulationSynchronizerThread;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.List;
import java.util.Timer;


/**
 * Окно симуляции
 */
public class SimulationForm extends JFrame {

    GraphsPanel graphsPanel = new GraphsPanel();
    ParamsPanel paramsPanel;

    // Таймер
    Timer timer = new Timer();

    /**
     * Главное окно симуляции
     *
     * @param ServerThread Поток
     */
    public SimulationForm(SimulationSynchronizerThread ServerThread){

        this.setTitle(GUIGlobals.program_title + " - Симуляция");
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

        JFrame parent = this;
        this.addComponentListener(new ComponentAdapter() {
            @Override
            public void componentResized(ComponentEvent e) {
                graphsPanel.updateChartSize(graphsPanel.chartPanel1, parent.getWidth(), parent.getHeight());
                graphsPanel.updateChartSize(graphsPanel.chartPanel2, parent.getWidth(), parent.getHeight());
                super.componentResized(e);
            }
        });

        // Меню
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(createFileMenu());
        menuBar.add(createViewMenu());
        this.setJMenuBar(menuBar);

        // Панель с графиками
        this.add(graphsPanel);

        // Панель с ползунками и кнопками
        paramsPanel = new ParamsPanel(ServerThread, this);
        this.add(paramsPanel, BorderLayout.SOUTH);

        updateTimer();
    }

    /**
     * Обновление таймера
     */
    public void updateTimer(){
        timer.cancel();

        timer = new Timer();
        // Задача для таймера - чтение данных из решателя
        TimerTask task = new TimerTask() {
            @Override
            public void run() {
                drawOutput();
            }
        };

        int period = 1000 / ProgramGlobals.getFramesPerSecond();
        timer.schedule(task, 0, period);
    }

    /**
     * Вывод графических данных
     */
    public void drawOutput(){

        // очистка графиков
        graphsPanel.series1.clear();
        graphsPanel.series2.clear();

        // список фронтов из решателя
        List<LayerDescription> layerDescriptions = SimulationGlobals.getCurrentWavePicture();
        graphsPanel.series1.add(0, Border.getCurrentBorderDisplacement());

        graphsPanel.resetMarkers(graphsPanel.chart1, graphsPanel.series1);

        for (int index = 0; index < layerDescriptions.size(); index++) {

            // график смещений
            graphsPanel.series1.add(layerDescriptions.get(index).getCurrentX() , layerDescriptions.get(index).calculateLayerDisplacement());

            // добавление маркера
            graphsPanel.setMarker(
                    graphsPanel.chart1,
                    layerDescriptions.get(index).getWaveType().getColor(),
                    layerDescriptions.get(index).getCurrentX(),
                    Integer.MIN_VALUE,
                    Integer.MAX_VALUE
            );

            // график деформаций
            if (index > 0)
                graphsPanel.series2.add(layerDescriptions.get(index - 1).getCurrentX(), layerDescriptions.get(index).getA2());
            else
                graphsPanel.series2.add(0.0, layerDescriptions.get(index).getA2());

            graphsPanel.series2.add(layerDescriptions.get(index).getCurrentX(), layerDescriptions.get(index).getA2());
        }

        if (layerDescriptions.size() > 0)
            graphsPanel.series2.add(layerDescriptions.get(layerDescriptions.size() - 1).getCurrentX(), 0.0);

        // обновление масштабов графиков
        graphsPanel.updateGraphAxis(graphsPanel.chart1, graphsPanel.series1);
        graphsPanel.updateGraphAxis(graphsPanel.chart2, graphsPanel.series2);


        // вывод времени симуляции
        String time = Double.toString(
                BigDecimal.valueOf(
                        SimulationTime.getSimulationTime())
                        .setScale(6, RoundingMode.HALF_DOWN)
                        .doubleValue()
        );
        paramsPanel.simulationTime.setText("Текущее время симуляции: " + time + " c.");
    }

    /**
     * Создание меню "Файл"
     * @return
     */
    public JMenu createFileMenu(){
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
                quickSaveChart(graphsPanel.chart1, graphsPanel.chartPanel1, "_перемещения");
                quickSaveChart(graphsPanel.chart2, graphsPanel.chartPanel2, "_деформации");
            }
        });

        saveAs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String file_name = getImageName();
                DBHandler.saveChart(graphsPanel.chart1, graphsPanel.chartPanel1, file_name + "_перемещения.png");
                DBHandler.saveChart(graphsPanel.chart2, graphsPanel.chartPanel2, file_name + "_деформации.png");
            }
        });

        return  fileMenu;
    }

    /**
     * Создание меню "Вид"
     * @return
     */
    public JMenu createViewMenu(){
        JMenu viewMenu = new JMenu("Вид");
        JMenuItem showPoints = new JMenuItem("Показать/скрыть точки");
        JMenuItem showCrosshair = new JMenuItem("Показать/скрыть оверлей");

        viewMenu.add(showPoints);
        viewMenu.add(showCrosshair);

        showPoints.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                XYPlot plot1 = (XYPlot) graphsPanel.chart1.getPlot();
                XYPlot plot2 = (XYPlot) graphsPanel.chart2.getPlot();
                XYLineAndShapeRenderer r1 = (XYLineAndShapeRenderer) plot1.getRenderer();
                XYLineAndShapeRenderer r2 = (XYLineAndShapeRenderer) plot2.getRenderer();

                if(r1.getSeriesShapesVisible(0).booleanValue()){
                    r1.setSeriesShapesVisible(0,false);
                    r2.setSeriesShapesVisible(0,false);
                }
                else{
                    r1.setSeriesShapesVisible(0, true);
                    r2.setSeriesShapesVisible(0, true);
                }
            }
        });

        showCrosshair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graphsPanel.toggleCrosshair();
            }
        });

        return viewMenu;
    }

    /**
     * Выбор файла для сохранения изображений графиков
     */
    private String getImageName(){
        JFrame parentFrame = new JFrame();
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Выберите файл для сохранения");
        fileChooser.setFileFilter(new FileNameExtensionFilter("PNG images", "png"));

        // Окно выбора файла
        int userSelection = fileChooser.showSaveDialog(parentFrame);

        File fileToSave = null;
        if (userSelection == JFileChooser.APPROVE_OPTION) {
            fileToSave = fileChooser.getSelectedFile();
        }

        // сохранение графика как PNG файл
        if(fileToSave!=null) {
            String file_path = fileToSave.getAbsolutePath().toString();

            return file_path.split(".png")[0];
        }

        return null;
    }

    /**
     * Быстрое сохранение изображений графиков
     * @param chart
     * @param panel
     */
    private void quickSaveChart(JFreeChart chart, ChartPanel panel, String chart_type){
        String path = DBHandler.outputImgsPath;
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String file_path = path + timeStamp  + chart_type + ".png";

        DBHandler.saveChart(chart, panel, file_path);
    }
}