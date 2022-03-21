package com.company.GUI.InputGUI;

import com.company.GUI.GUIGlobals;
import com.company.Simulation.SimulationSynchronizerThread;
import org.jfree.chart.*;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.NumberTickUnit;
import org.jfree.chart.entity.ChartEntity;
import org.jfree.chart.entity.XYItemEntity;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import org.jfree.util.ShapeUtilities;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Без UI дизайнера, ибо он плохо работает с библиотекой jfreechart
public class GraphForm extends JFrame {
    private ChartPanel chartPanel;
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
        JFreeChart xyLineChart = ChartFactory.createXYLineChart(
                "Введите график",
                "x",
                "y",
                createDataset(),
                PlotOrientation.VERTICAL,
                false,
                true,
                false);
        xyLineChart.setBackgroundPaint(GUIGlobals.background_color);
        ChartPanel chartPanel  = new ChartPanel(xyLineChart);
        chartPanel.setBackground(GUIGlobals.background_color);

        XYPlot plot = (XYPlot) xyLineChart.getPlot();
        XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) plot.getRenderer();
        renderer.setBaseShapesVisible(true);
        //renderer.setSeriesShape(0, ShapeUtilities.createTranslatedShape(new Rectangle(2,2), -1, -1));

        NumberAxis xAxis = (NumberAxis) plot.getDomainAxis();
        NumberAxis yAxis = (NumberAxis) plot.getRangeAxis();
        xAxis.setRange(0, 10);
        xAxis.setTickUnit(new NumberTickUnit(1));
        yAxis.setRange(-5, 5);
        yAxis.setTickUnit(new NumberTickUnit(1));
        
        chartPanel.addChartMouseListener(new ChartMouseListener() {
            @Override
            public void chartMouseClicked(ChartMouseEvent chartMouseEvent) {
                //System.out.println("click");

            }

            @Override
            public void chartMouseMoved(ChartMouseEvent chartMouseEvent) {
                report(chartMouseEvent);
            }

            // тестовая функция. при наведении на точку печатает ее координаты
            private void report(ChartMouseEvent cme) {
                ChartEntity ce = cme.getEntity();
                if (ce instanceof XYItemEntity) {
                    XYItemEntity e = (XYItemEntity) ce;
                    XYDataset d = e.getDataset();
                    int s = e.getSeriesIndex();
                    int i = e.getItem();
                    System.out.println("X:" + d.getX(s, i) + ", Y:" + d.getY(s, i));
                }
            }

        });
        return chartPanel;
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
                System.out.print("Нажато");
            }
        });
        return viewSettings;
    }

    // тестовая функция
    private XYDataset createDataset(){
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("series");
        series.add(1, 1);
        series.add(-1, -5);
        series.add(5, 2);
        dataset.addSeries(series);
        return dataset;
    }
    private XYDataset createDataset2() {
        XYSeriesCollection dataset = new XYSeriesCollection();
        XYSeries series = new XYSeries("");
        series.add(0,0);
        dataset.addSeries(series);
        return dataset;
    }
}
