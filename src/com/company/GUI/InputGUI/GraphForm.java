package com.company.GUI.InputGUI;

import com.company.GUI.GUIGlobals;
import com.company.Simulation.SimulationSynchronizerThread;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

// Без UI дизайнера, ибо он плохо работает с библиотекой jfreechart
public class GraphForm extends JFrame {
    private ChartPanel chartPanel;
    private JButton setGraphButton;

    public GraphForm(JLabel mainFrameLabel, SimulationSynchronizerThread ServerThread) {
        this.setTitle("ADCS - Ввод графика");
        this.setSize(GUIGlobals.graph_frame_width, GUIGlobals.graph_frame_height);
        this.setVisible(true);

        // позволяет появляться фрейму в центре экрана. (по дефолту - в верхнем левом углу)
        this.setLocationRelativeTo(null);

        // панель с графиком
        JFreeChart xyLineChart = ChartFactory.createXYLineChart(
                "Введите график",
                "x",
                "y",
                createDataset2(),
                PlotOrientation.VERTICAL,
                false,
                true,
                false);
        xyLineChart.setBackgroundPaint(GUIGlobals.background_color);
        chartPanel  = new ChartPanel(xyLineChart);
        chartPanel.setBackground(GUIGlobals.background_color);
        add(chartPanel, BorderLayout.CENTER);

        // кнопка "задать"
        setGraphButton = new JButton();
        setGraphButton.setPreferredSize(new Dimension(GUIGlobals.graph_frame_width, 50));
        setGraphButton.setText("Задать график");
        add(setGraphButton, BorderLayout.SOUTH);
        setGraphButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                mainFrameLabel.setText("<html><font color='green'>Задано</font></html>");
            }
        });
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
