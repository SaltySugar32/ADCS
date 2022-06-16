package com.company.GUI.SimulationGUI;

import com.company.GUI.DataHandler;
import com.company.GUI.GUIGlobals;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.panel.CrosshairOverlay;
import org.jfree.chart.panel.Overlay;
import org.jfree.chart.plot.Crosshair;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.ValueMarker;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.general.Dataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.List;

/**
 * Панель отображения результатов
 */
public class GraphsPanel extends JPanel {

    public XYSeries series1 = new XYSeries("series1");
    public XYSeries series2 = new XYSeries("series2");

    public JFreeChart chart1 = createChart(series1, "График смещений", "x", "u(x)");
    public JFreeChart chart2 = createChart(series2, "График деформаций", "x", "u");

    Crosshair crosshairX1 = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
    Crosshair crosshairY1 = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
    Crosshair crosshairX2 = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));
    Crosshair crosshairY2 = new Crosshair(Double.NaN, Color.GRAY, new BasicStroke(0f));

    public ChartPanel chartPanel1 = createChartPanel(chart1, crosshairX1, crosshairY1);
    public ChartPanel chartPanel2 = createChartPanel(chart2, crosshairX2, crosshairY2);

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

        // Ширина линий
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setSeriesStroke(0, new BasicStroke(2));
        renderer.setSeriesShapesVisible(0,false);

        updateGraphAxis(chart, series);

        return chart;
    }

    /**
     * Функция отрисовки овердея - позиции мышки
     * @param cme
     */
    private void showCrosshair(ChartPanel chartPanel, JFreeChart chart, ChartMouseEvent cme, Crosshair chx, Crosshair chy){
        int mouseX = cme.getTrigger().getX();
        int mouseY = cme.getTrigger().getY();

        Rectangle2D plotArea = chartPanel.getScreenDataArea();
        XYPlot plot = (XYPlot) chart.getPlot();
        double chartX = plot.getDomainAxis().java2DToValue(mouseX, plotArea, plot.getDomainAxisEdge());
        double chartY = plot.getRangeAxis().java2DToValue(mouseY, plotArea, plot.getRangeAxisEdge());

        chx.setValue(chartX);
        chy.setValue(chartY);
    }

    /**
     * Отрисовка оверлея - позиции мышки
     */
    public void toggleCrosshair(){
        if (crosshairX1.isLabelVisible()) {
            crosshairX1.setLabelVisible(false);
            crosshairX2.setLabelVisible(false);
            crosshairY1.setLabelVisible(false);
            crosshairY2.setLabelVisible(false);
        }
        else {
            crosshairX1.setLabelVisible(true);
            crosshairX2.setLabelVisible(true);
            crosshairY1.setLabelVisible(true);
            crosshairY2.setLabelVisible(true);
        }
    }

    /**
     * Функция, задающая стандартные диапазоны осей
     * @param chart
     */
    public void updateGraphAxis(JFreeChart chart, XYSeries series){
        XYPlot plot = (XYPlot) chart.getPlot();

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();

        // порог
        double threshold;

        threshold = (series.getMaxX() - series.getMinX()) / 10;
        double maxX = Math.max(series.getMaxX(), threshold) * 1.05;

        threshold = (series.getMaxY() - series.getMinY()) / 11;
        double maxY = Math.max(series.getMaxY(), threshold) * 1.05;
        double minY = Math.min(series.getMinY(), -threshold) * 1.05;

        try {
            xAxis.setRange(0, maxX);
            yAxis.setRange(minY, maxY);
        }
        catch (Exception exception){}
    }

    /**
     * Функция очистки маркеров
     * @param chart
     * @param series
     */
    public void resetMarkers(JFreeChart chart, XYSeries series){
        XYPlot plot = (XYPlot) chart.getPlot();
        XYSeriesCollection dataset = (XYSeriesCollection) plot.getDataset();
        dataset.removeAllSeries();
        dataset.addSeries(series);
    }

    /**
     * Функция добавления маркера на график
     * @param chart
     * @param color
     * @param x
     * @param ymin
     * @param ymax
     */
    public void setMarker(JFreeChart chart, Color color,double x, double ymin, double ymax){
        XYPlot plot = (XYPlot) chart.getPlot();
        XYSeriesCollection dataset = (XYSeriesCollection) plot.getDataset();
        XYSeries new_series = new XYSeries(x);
        new_series.add(x, ymin);
        new_series.add(x, ymax);
        try {
            dataset.addSeries(new_series);
            // цвет маркера
            plot.getRenderer().setSeriesPaint(dataset.getSeriesCount() - 1, color);

            // настройка линий (пунктир и ширина)
            plot.getRenderer().setSeriesStroke(
                    dataset.getSeriesCount() - 1,
                    new BasicStroke(
                            2.0f,
                            BasicStroke.CAP_BUTT,
                            BasicStroke.JOIN_MITER,
                            10.0f,
                            new float[] {10.0f},
                            0.0f)
            );
        }
        catch (Exception ex){}
    }

    /**
     * Создание Панели с графиком
     * @param chart
     * @return
     */
    public ChartPanel createChartPanel(JFreeChart chart, Crosshair chx, Crosshair chy){

        ChartPanel chartPanel = new ChartPanel(chart);
        updateChartSize(chartPanel, GUIGlobals.graph_frame_width, GUIGlobals.graph_frame_height);

        // Отключение (скрытие) зума при зажатии кнопки
        chartPanel.setZoomTriggerDistance(Integer.MAX_VALUE);
        chartPanel.setFillZoomRectangle(false);
        chartPanel.setZoomOutlinePaint(new Color(0f, 0f, 0f, 0f));

        // отрисовка позиции мышки
        CrosshairOverlay crosshairOverlay = new CrosshairOverlay();
        crosshairOverlay.addDomainCrosshair(chx);
        crosshairOverlay.addRangeCrosshair(chy);
        chartPanel.addOverlay(crosshairOverlay);

        chartPanel.setPopupMenu(null);

        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {

            }

            @Override
            public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {
                showCrosshair(chartPanel, chart, chartMouseEvent, chx, chy);
            }
        });

        return chartPanel;
    }

    /**
     * Обновление размеров панели с графиком
     * @param chartPanel
     * @param frame_width
     * @param frame_height
     */
    public void updateChartSize(ChartPanel chartPanel, int frame_width, int frame_height){
        // Размеры панели графика
        int width = (frame_width - 30) / 2;
        int height = frame_height - 180;
        chartPanel.setPreferredSize(new Dimension(width, height));
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
