package com.company.GUI.SimulationGUI;

import com.company.GUI.DataHandler;
import com.company.GUI.GUIGlobals;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;

/**
 * Панель отображения результатов
 */
public class GraphsPanel extends JPanel {

    public XYSeries series1 = createSeries("series1");
    public XYSeries series2 = createSeries("series2");

    public JFreeChart chart1 = createChart(series1, "График смещений", "x", "u(x,t)");
    public JFreeChart chart2 = createChart(series2, "График деформаций", "x", "u");

    public ChartPanel chartPanel1 = createChartPanel(chart1);
    public ChartPanel chartPanel2 = createChartPanel(chart2);

    /**
     * Панель отображения результатов
     */
    public GraphsPanel(){

        //Отрисовка панели
        this.setBackground(GUIGlobals.background_color);

        this.add(chartPanel1);
        this.add(chartPanel2);

        this.setLayout(new FlowLayout());
    }

    /**
     * Создание графика
     * @param series
     * @param title
     * @return
     */
    public JFreeChart createChart(XYSeries series, String title, String xAxisLabel, String yAxisLabel){
        // Создание датасета точек
        XYDataset dataset = createDataset(series);

        // Создание графика
        JFreeChart chart = ChartFactory.createXYLineChart(
                title,
                xAxisLabel,
                yAxisLabel,
                dataset,
                PlotOrientation.VERTICAL,
                false,
                true,
                false
        );

        // Отрисовка сетки
        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.white);
        plot.setRangeGridlinePaint(Color.GRAY);
        plot.setDomainGridlinePaint(Color.GRAY);

        // Отрисовка маркера (линия y=0)
        final ValueMarker marker = new ValueMarker(0.0);
        marker.setPaint(Color.black);
        marker.setStroke(new BasicStroke(1.0f));
        plot.addRangeMarker(marker);

        DataHandler.setDefaultGraphAxisSettings();
        setGraphAxisSettings(chart);

        return chart;
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
    }

    /**
     * Создание Панели с графиком
     * @param chart
     * @return
     */
    public ChartPanel createChartPanel(JFreeChart chart){

        // Размеры панели графика
        int width = (GUIGlobals.graph_frame_width - 50) / 2;
        int height = GUIGlobals.graph_frame_height - 180;

        ChartPanel chartPanel = new ChartPanel(chart){
            public Dimension getPreferredSize(){
                return new Dimension(width,height);
            }
        };

        // Отключение (скрытие) зума при зажатии кнопки
        chartPanel.setZoomTriggerDistance(Integer.MAX_VALUE);
        chartPanel.setFillZoomRectangle(false);
        chartPanel.setZoomOutlinePaint(new Color(0f, 0f, 0f, 0f));

        return chartPanel;
    }

    /**
     * Создание series (мн-ва) с точками
     * @param name
     * @return
     */
    public XYSeries createSeries(String name){
        XYSeries series = new XYSeries(name);
        //series.add(0,0);

        return series;
    }

    /**
     * Создание датасета с заднным множеством series
     * @param series
     * @return
     */
    public XYDataset createDataset(XYSeries series) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(series);
        return dataset;
    }
}
