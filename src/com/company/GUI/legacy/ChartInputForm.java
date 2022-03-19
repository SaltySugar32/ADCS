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
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ChartInputForm extends JFrame{
    private JPanel mainPanel;
    private JPanel testPanel;
    private JButton setEnvParamsButton;

    public ChartInputForm(String title, SimulationSynchronizerThread ServerThread) {
        this.setTitle(title);
        this.setSize(1500,1000);
        this.setLocationRelativeTo(null);
        this.add(mainPanel);

        JFreeChart pieChart = ChartFactory.createXYLineChart("title", "x", "y", createDataset(), PlotOrientation.VERTICAL, true, true, false);
        testPanel = new ChartPanel(pieChart);

        add(testPanel, BorderLayout.CENTER);
        setEnvParamsButton.setPreferredSize(new Dimension(1500, 50));
        add(setEnvParamsButton, BorderLayout.SOUTH);

        //mainPanel.add(jPanel);
        this.setVisible(true);

        setEnvParamsButton.addActionListener(new ActionListener() {
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
