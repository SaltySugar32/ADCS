package com.company.GUI.legacy;

import com.company.GUI.GUIGlobals;
import com.company.Simulation.SimulationSynchronizerThread;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChartInputForm extends JFrame{
    private JButton button1;
    private JPanel ChartPanel;
    private JPanel mainPanel;

    public ChartInputForm(String title, SimulationSynchronizerThread ServerThread) {
        this.setTitle(title);
        this.setSize(GUIGlobals.window_width,GUIGlobals.window_height);
        this.setLocationRelativeTo(null);
        this.add(mainPanel);


        JFreeChart pieChart = ChartFactory.createXYLineChart("title", "x", "y", createDataset(), PlotOrientation.VERTICAL, true, true, false);
        org.jfree.chart.ChartPanel panel = new ChartPanel(pieChart);
        setContentPane(panel);

        this.setVisible(true);

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }
        private XYDataset createDataset(){
            XYSeriesCollection dataset = new XYSeriesCollection();
            XYSeries series = new XYSeries("series");
            series.add(1, 1);
            series.add(-1, -5);
            series.add(5, 2);
            dataset.addSeries(series);
            return dataset;
        }
}
