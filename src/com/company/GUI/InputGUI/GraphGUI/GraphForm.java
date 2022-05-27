package com.company.GUI.InputGUI.GraphGUI;

import com.company.GUI.DataHandler;
import com.company.GUI.Database.DBHandler;
import com.company.GUI.GUIGlobals;
import com.company.thread_organization.SimulationSynchronizerThread;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.*;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.chart.util.ShapeUtils;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Rectangle2D;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

// Без UI дизайнера, ибо он плохо работает с библиотекой jfreechart
public class GraphForm extends JFrame {
    private JFreeChart xyLineChart;
    private ChartPanel chartPanel;

    private XYDataset dataset1;
    private XYDataset dataset2;

    private XYSeries series1;
    private XYSeries series2;

    private Crosshair crosshairx;
    private Crosshair crosshairy;

    private boolean isLinApproxGraph;

    public GraphForm(JLabel mainFrameLabel, SimulationSynchronizerThread ServerThread) {
        this.setTitle("ADCS - Ввод графика");
        this.setSize(GUIGlobals.graph_frame_width, GUIGlobals.graph_frame_height);
        this.setVisible(true);

        // позволяет появляться фрейму в центре экрана. (по дефолту - в верхнем левом углу)
        this.setLocationRelativeTo(null);

        // панель с графиком
        chartPanel = createChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        // кнопка "задать"
        JButton setGraphButton = createSetGraphButton(mainFrameLabel);
        add(setGraphButton, BorderLayout.SOUTH);

        // меню
        JMenuBar menuBar = new JMenuBar();
        JMenu viewMenu = createViewMenu();
        JMenu fileMenu = createFileMenu();
        JMenu changeGraphMenu = createInputMenu();

        menuBar.add(fileMenu);
        menuBar.add(viewMenu);
        menuBar.add(changeGraphMenu);
        setJMenuBar(menuBar);

        // флажок текущего задаваемого графика
        isLinApproxGraph = false;
    }

    /**
     * Функция создания панели с графиком
     * @return
     */
    private ChartPanel createChartPanel(){
        dataset1 = createDataset1();
        dataset2 = createDataset2();

        xyLineChart = ChartFactory.createXYLineChart(
                "График малых деформаций",
                "t, " + DataHandler.unitOfTime + "",
                "φ(t), мм",
                dataset1,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        xyLineChart.setBackgroundPaint(GUIGlobals.background_color);
        ChartPanel chartPanel  = new ChartPanel(xyLineChart);
        chartPanel.setPopupMenu(null);

        // Установка дефолтных параметров графика
        DataHandler.setDefaultGraphAxisSettings();
        DataHandler.setDefaultGraphViewSettings();

        // Отрисовка сетки
        XYPlot plot = (XYPlot) xyLineChart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setDomainGridlinePaint(Color.GRAY);

        // Отрисовка маркера (линия y=0)
        final ValueMarker marker = new ValueMarker(0.0);
        marker.setPaint(Color.black);
        marker.setStroke(new BasicStroke(1.0f));
        plot.addRangeMarker(marker);

        xyLineChart.getTitle().setFont(new Font("Tahoma", Font.PLAIN, 20));

        // Датасеты точек графиков
        plot.setDataset(0, dataset1);
        plot.setDataset(1, dataset2);

        // рендерер для графика мал. деф.
        XYSplineRenderer renderer1 = new XYSplineRenderer(DataHandler.spline_precision);
        plot.setRenderer(0, renderer1);
        renderer1.setSeriesPaint(0, Color.RED);

        // рендерер для графика лин. апр.
        XYLineAndShapeRenderer renderer2 = new XYLineAndShapeRenderer();
        plot.setRenderer(1,renderer2);
        renderer2.setSeriesPaint(0, Color.BLUE);

        // отрисовка позиции мышки
        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        crosshairx = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        crosshairy = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        crosshairx.setLabelVisible(true);
        crosshairy.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(crosshairx);
        crosshairOverlay.addRangeCrosshair(crosshairy);
        chartPanel.addOverlay(crosshairOverlay);

        // установка параметров отрисовки графиков
        setGraphAxisSettings(xyLineChart);
        setGraphViewSettings(xyLineChart);

        // обработчик кликов
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {
                // если нажата правая кнопка
                if (chartMouseEvent.getTrigger().getButton() == MouseEvent.BUTTON3)
                    // возврат к исходным параметрам графика
                    setGraphAxisSettings(xyLineChart);
                else {
                    ChartEntity ce = chartMouseEvent.getEntity();

                    // удаление существующей точки
                    if (ce instanceof XYItemEntity &&
                            (
                                    (((XYItemEntity) ce).getDataset() == dataset1 && !isLinApproxGraph) ||
                                    (((XYItemEntity) ce).getDataset() == dataset2 && isLinApproxGraph)
                            ))
                        removePoint(ce);

                    // добавление новой точки
                    else
                        addPoint(chartMouseEvent);
                }
            }

            /**
             * Функция удаления точки
             * @param ce
             */
            private void removePoint(ChartEntity ce){
                XYItemEntity e = (XYItemEntity) ce;
                int i = e.getItem();
                // Если не первая (нулевая) точка
                if(i>0){
                    if(isLinApproxGraph)
                        series2.remove(i);
                    else
                        series1.remove(i);
                }
            }

            /**
             * Функция добавления новой точки
             * @param cme
             */
            private void addPoint(ChartMouseEvent cme){
                int mouseX = cme.getTrigger().getX();
                int mouseY = cme.getTrigger().getY();

                // StackOverflow, спасибо, что ты есть
                Rectangle2D plotArea = chartPanel.getScreenDataArea();
                XYPlot plot = (XYPlot) xyLineChart.getPlot(); // your plot
                double chartX = plot.getDomainAxis().java2DToValue(mouseX, plotArea, plot.getDomainAxisEdge());
                double chartY = plot.getRangeAxis().java2DToValue(mouseY, plotArea, plot.getRangeAxisEdge());

                if(chartX < DataHandler.xmin || chartX > DataHandler.xmax) return;
                if(chartY < DataHandler.ymin || chartY > DataHandler.ymax) return;

                if(isLinApproxGraph)
                    series2.add(chartX, chartY);
                else
                    series1.add(chartX, chartY);
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {
                // отрисовка оверлея - позиция мышки
                showCrosshair(chartMouseEvent);
            }

            /**
             * Функция отрисовки овердея - позиции мышки
             * @param cme
             */
            private void showCrosshair(ChartMouseEvent cme){
                int mouseX = cme.getTrigger().getX();
                int mouseY = cme.getTrigger().getY();

                Rectangle2D plotArea = chartPanel.getScreenDataArea();
                XYPlot plot = (XYPlot) xyLineChart.getPlot();
                double chartX = plot.getDomainAxis().java2DToValue(mouseX, plotArea, plot.getDomainAxisEdge());
                double chartY = plot.getRangeAxis().java2DToValue(mouseY, plotArea, plot.getRangeAxisEdge());

                crosshairx.setValue(chartX);
                crosshairy.setValue(chartY);
            }
        });

        return chartPanel;
    }

    /**
     * Функция, задающая стандартные диапазоны осей
     * @param chart
     */
    public static void setGraphAxisSettings(JFreeChart chart){
        XYPlot plot = (XYPlot) chart.getPlot();

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();

        xAxis.setRange(DataHandler.xmin, DataHandler.xmax);
        xAxis.setTickUnit(new NumberTickUnit(DataHandler.xtick));

        yAxis.setRange(DataHandler.ymin, DataHandler.ymax);
        yAxis.setTickUnit(new NumberTickUnit(DataHandler.ytick));

        plot.getDomainAxis().setLabel("t, " + DataHandler.unitOfTime + "");
    }

    /**
     * Функция, задающая стандартные настройки отображения графиков
     * @param chart
     */
    public static void setGraphViewSettings(JFreeChart chart){
        XYPlot plot = (XYPlot) chart.getPlot();

        // рендерер для 1го графика
        XYSplineRenderer r1 = (XYSplineRenderer)plot.getRenderer(0);
        r1.setPrecision(DataHandler.spline_precision);
        r1.setSeriesStroke(0, new BasicStroke(DataHandler.spline_width));

        // рендерер для 2го графика
        XYLineAndShapeRenderer r2 = (XYLineAndShapeRenderer)plot.getRenderer(1);
        r2.setSeriesStroke(0, new BasicStroke(DataHandler.lin_appr_width));

        // отрисовка маркера
        plot.clearRangeMarkers();
        final ValueMarker marker = new ValueMarker(0.0);
        marker.setPaint(Color.black);
        marker.setStroke(new BasicStroke(DataHandler.marker_width));
        plot.addRangeMarker(marker);

        // отрисовка маток точек
        int w1 = DataHandler.point_width;
        int w2 = w1/2;
        r1.setSeriesShape(0, ShapeUtils.createTranslatedShape(new Rectangle(w1,w1), -w2, -w2));
        r2.setSeriesShape(0, ShapeUtils.createTranslatedShape(new Rectangle(w1,w1), -w2, -w2));
    }

    /**
     * Функция создания кнопки "Задать"
     * @param mainFrameLabel
     * @return
     */
    private JButton createSetGraphButton(JLabel mainFrameLabel){
        JButton setGraphButton = new JButton();
        setGraphButton.setPreferredSize(new Dimension(GUIGlobals.graph_frame_width, 50));
        setGraphButton.setText("Задать график");

        setGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // получение массива точек графика лин. апр.
                DBHandler.addGraphFile("temp", series2.toArray());
                DataHandler.setGraphInput(series2.toArray());
                mainFrameLabel.setText("<html><font color='green'>Задано</font></html>");
            }
        });

        return setGraphButton;
    }

    /**
     * Функция создания меню "Вид"
     * @return
     */
    private JMenu createViewMenu(){
        JMenu viewSettings = new JMenu("Вид");
        JMenuItem changeGraphSettings = new JMenuItem("Параметры осей");
        JMenuItem changeSplineSettings = new JMenuItem("Параметры отображения");

        viewSettings.add(changeGraphSettings);
        viewSettings.add(changeSplineSettings);

        changeGraphSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphAxisSettingsDialog dialog = new GraphAxisSettingsDialog(xyLineChart);
            }
        });

        changeSplineSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphViewSettingsDialog dialog = new GraphViewSettingsDialog(xyLineChart);
            }
        });

        return viewSettings;
    }

    /**
     * Функция создания меню "Файл"
     * @return
     */
    private JMenu createFileMenu(){
        JMenu fileSettings = new JMenu("Файл");

        JMenuItem setFormula = new JMenuItem("Задать формулу");
        JMenuItem quickSavePNG = new JMenuItem("Сохранить изображение");
        JMenuItem savePNG = new JMenuItem("Сохранить изображение как...");

        fileSettings.add(setFormula);
        fileSettings.add(quickSavePNG);
        fileSettings.add(savePNG);


        setFormula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SetFormulaDialog dialog = new SetFormulaDialog(xyLineChart, series1);
            }
        });

        quickSavePNG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                quickSaveChartAsPNG(xyLineChart, chartPanel);
            }
        });

        KeyStroke key = KeyStroke.getKeyStroke("control S");
        quickSavePNG.setAccelerator(key);

        savePNG.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChartAsPNG(xyLineChart, chartPanel);
            }
        });

        return fileSettings;
    }

    /**
     * создание меню смены задаваемого графика
     * @return
     */
    private JMenu createInputMenu(){
        JMenu inputMenu = new JMenu("Ввод");
        JMenuItem changeGraph = new JMenuItem("Сменить задаваемый график");
        JMenuItem loadGraph = new JMenuItem("Загрузить график");

        inputMenu.add(changeGraph);
        inputMenu.add(loadGraph);

        changeGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                XYPlot plot = (XYPlot) xyLineChart.getPlot();
                XYSplineRenderer r1 = (XYSplineRenderer) plot.getRenderer(0);
                XYLineAndShapeRenderer r2 = (XYLineAndShapeRenderer) plot.getRenderer(1);
                if(isLinApproxGraph){
                    r1.setSeriesShapesVisible(0, true);
                    r2.setSeriesShapesVisible(0, false);
                }
                else{
                    r1.setSeriesShapesVisible(0, false);
                    r2.setSeriesShapesVisible(0, true);
                }
                isLinApproxGraph = !isLinApproxGraph;

                if(isLinApproxGraph)
                    xyLineChart.getTitle().setText("График линейной аппроксимации");
                else
                    xyLineChart.getTitle().setText("График малых деформаций");
            }
        });

        loadGraph.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LoadGraphDialog dialog = new LoadGraphDialog(series2);
            }
        });

        return inputMenu;
    }

    /**
     * Функция создания датасета1 - график мал. деф., со сплайном
     * @return
     */
    private XYDataset createDataset1(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        series1 = new XYSeries("series1");
        series1.add(0, 0);
        dataset.addSeries(series1);

        return dataset;
    }

    /**
     * Функция создания датасета2 - график лин. апр.
     * @return
     */
    private XYDataset createDataset2(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        series2 = new XYSeries("series2");
        series2.add(0, 0);
        dataset.addSeries(series2);

        return dataset;
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

            if(!file_path.endsWith(".png"))
                file_path += ".png";

            SaveChart(chart, panel, file_path);
        }
    }

    /**
     * Быстрое сохранение изображения
     * @param chart
     * @param panel
     */
    private void quickSaveChartAsPNG(JFreeChart chart, ChartPanel panel){
        String path = "data/inputImages/";
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
        String file_path = path + timeStamp + ".png";

        SaveChart(chart, panel, file_path);
    }

    /**
     * Сохранение графика в файл
     * @param chart
     * @param panel
     * @param file
     */
    private void SaveChart(JFreeChart chart, ChartPanel panel, String file){
        // Удаление названия графика в изображении
        String title = chart.getTitle().getText();
        chart.getTitle().setText("");

        // сохранение графика
        try {
            ChartUtils.saveChartAsPNG(
                    new File(file),
                    chart,
                    panel.getWidth(),
                    panel.getHeight()
            );
        }
        catch (IOException ex) {}

        // Откат названия графика
        chart.getTitle().setText(title);
    }
}
