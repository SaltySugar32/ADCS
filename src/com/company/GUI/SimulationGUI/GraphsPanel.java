package com.company.GUI.SimulationGUI;

import com.company.GUI.DataHandler;
import com.company.GUI.GUIGlobals;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;

/**
 * Панель отображения результатов
 */
public class GraphsPanel extends JPanel {

    public ChartPanel chartPanel1;
    public ChartPanel chartPanel2;

    public JFreeChart chart1;
    public JFreeChart chart2;

    public XYSeries series1;
    public XYSeries series2;
    /**
     * Панель отображения результатов
     */
    public GraphsPanel(){

        //Отрисовка панели
        this.setBackground(GUIGlobals.background_color);

        chartPanel1 = createChartPanel(chart1, series1, "series1", "График перемещений");
        chartPanel2 = createChartPanel(chart2, series2, "series2", "График деформаций");

        this.add(chartPanel1);
        this.add(chartPanel2);

        this.setLayout(new FlowLayout());
    }

    public ChartPanel createChartPanel(JFreeChart chart, XYSeries series, String seriesName, String chartName){
        XYDataset dataset1 = createDataset(series, seriesName);

        chart = ChartFactory.createXYLineChart(
                chartName,
                "t, " + DataHandler.unitOfTime + "",
                "φ(t), мм",
                dataset1,
                PlotOrientation.VERTICAL,
                false,
                true,
                false);

        int size = (GUIGlobals.graph_frame_width - 50) / 2;
        ChartPanel chartPanel = new ChartPanel(chart){
            public Dimension getPreferredSize(){
                return new Dimension(size,size);
            }
        };

        return chartPanel;
    }

    public XYDataset createDataset(XYSeries series, String seriesName) {
        XYSeriesCollection dataset = new XYSeriesCollection();
        series = new XYSeries(seriesName);
        series.add(0,0);

        dataset.addSeries(series);
        return dataset;
    }
}
