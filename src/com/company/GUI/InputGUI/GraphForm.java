package com.company.GUI.InputGUI;

import com.company.GUI.DataHandler;
import com.company.GUI.GUIGlobals;
import com.company.Simulation.SimulationSynchronizerThread;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.CrosshairState;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYSplineRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import javax.xml.crypto.Data;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;

// Без UI дизайнера, ибо он плохо работает с библиотекой jfreechart
public class GraphForm extends JFrame {
    private ChartPanel chartPanel;
    private JFreeChart xyLineChart;
    private XYDataset dataset;
    private XYSeries series;

    private CrosshairOverlay crosshairOverlay;
    private Crosshair crosshairx;
    private Crosshair crosshairy;

    private JButton setGraphButton;
    private JMenuBar menuBar;
    private JMenu viewMenu;


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
        setGraphButton = createSetGraphButton(mainFrameLabel);
        add(setGraphButton, BorderLayout.SOUTH);

        // меню
        menuBar = new JMenuBar();
        viewMenu = createViewMenu();
        menuBar.add(viewMenu);
        setJMenuBar(menuBar);
    }

    /**
     * Функция создания панели с графиком
     * @return
     */
    private ChartPanel createChartPanel(){
        dataset = createDataset();
        xyLineChart = ChartFactory.createXYLineChart(
                "Введите график",
                "x",
                "y",
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);
        xyLineChart.setBackgroundPaint(GUIGlobals.background_color);
        ChartPanel chartPanel  = new ChartPanel(xyLineChart);
        chartPanel.setPopupMenu(null);

        DataHandler.setDefault();
        setGraphSettings(xyLineChart);

        XYPlot plot = (XYPlot) xyLineChart.getPlot();
        plot.setBackgroundPaint(Color.LIGHT_GRAY);
        //XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        XYSplineRenderer renderer = new XYSplineRenderer();
        plot.setRenderer(renderer);

        renderer.setSeriesShapesVisible(0, true);
        //renderer.setSeriesShape(0, ShapeUtilities.createTranslatedShape(new Rectangle(2,2), -1, -1));

        crosshairOverlay = new CrosshairOverlay();
        crosshairx = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        crosshairy = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
        crosshairx.setLabelVisible(true);
        crosshairy.setLabelVisible(true);
        crosshairOverlay.addDomainCrosshair(crosshairx);
        crosshairOverlay.addRangeCrosshair(crosshairy);
        chartPanel.addOverlay(crosshairOverlay);

        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {
                if (chartMouseEvent.getTrigger().getButton() == MouseEvent.BUTTON3)
                    setGraphSettings(xyLineChart);
                else {
                    ChartEntity ce = chartMouseEvent.getEntity();
                    // удаление существующей точки
                    if (ce instanceof XYItemEntity) removePoint(ce);
                        // добавление новой точки
                    else addPoint(chartMouseEvent);
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
                if(i>0) series.remove(i);
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
                series.add(chartX, chartY);
            }

            @Override
            public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {
                showCrosshair(chartMouseEvent);
            }

            /**
             *
             * @param cme
             */
            private void showCrosshair(ChartMouseEvent cme){
                int mouseX = cme.getTrigger().getX();
                int mouseY = cme.getTrigger().getY();

                // StackOverflow, спасибо, что ты есть
                Rectangle2D plotArea = chartPanel.getScreenDataArea();
                XYPlot plot = (XYPlot) xyLineChart.getPlot(); // your plot
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
    public static void setGraphSettings(JFreeChart chart){
        XYPlot plot = (XYPlot) chart.getPlot();
        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        xAxis.setRange(DataHandler.xmin, DataHandler.xmax);
        xAxis.setTickUnit(new NumberTickUnit(DataHandler.xtick));
        yAxis.setRange(DataHandler.ymin, DataHandler.ymax);
        yAxis.setTickUnit(new NumberTickUnit(DataHandler.ytick));
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
                mainFrameLabel.setText("<html><font color='green'>Задано</font></html>");
            }
        });
        return setGraphButton;
    }

    /**
     * Функция создания меню
     * @return
     */
    private JMenu createViewMenu(){
        JMenu viewSettings = new JMenu("Вид");
        JMenuItem changeGraphSettings = new JMenuItem("Параметры графика");
        viewSettings.add(changeGraphSettings);

        changeGraphSettings.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                GraphSettingsDialog dialog = new GraphSettingsDialog(xyLineChart);
            }
        });
        return viewSettings;
    }

    /**
     * Функция создания датасета
     * @return
     */
    private XYDataset createDataset(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        series = new XYSeries("series");
        series.add(0, 0);
        dataset.addSeries(series);
        return dataset;
    }
}
