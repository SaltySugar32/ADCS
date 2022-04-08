package com.company.GUI.InputGUI;

import com.company.GUI.DataHandler;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphViewSettingsDialog extends JDialog {
    private JPanel contentPane;
    private JPanel xPanel;
    private JTextField splinePrecisionField;
    private JLabel precisionLabel;
    private JTextField splineWidthField;
    private JLabel widthLabel;
    private JButton setButton;
    private JButton defaultButton;
    private JLabel statusLabel;
    private JTextField markerField;
    private JLabel markerLabel;
    private JTextField linApproxWidthField;
    private JTextField pointWidthField;

    public GraphViewSettingsDialog(JFreeChart chart) {
        add(contentPane);
        setTitle("Параметры");
        setSize(400, 400);

        setVisible(true);
        this.setAlwaysOnTop(true);
        fillTextFields();

        statusLabel.setText("");

        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText(DataHandler.setGraphViewSettings(
                        splinePrecisionField.getText(),
                        splineWidthField.getText(),
                        linApproxWidthField.getText(),
                        markerField.getText(),
                        pointWidthField.getText()
                ));
                GraphForm.setGraphViewSettings(chart);
            }
        });

        defaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataHandler.setDefaultGraphViewSettings();
                GraphForm.setGraphViewSettings(chart);
                fillTextFields();
            }
        });
    }

    /**
     * Функция записывает во все TextField текущие значения
     */
    private void fillTextFields(){
        splinePrecisionField.setText(Integer.toString(DataHandler.spline_precision));
        splineWidthField.setText(Double.toString(DataHandler.spline_width));
        linApproxWidthField.setText(Double.toString(DataHandler.lin_appr_width));
        markerField.setText(Double.toString(DataHandler.marker_width));
        pointWidthField.setText(Integer.toString(DataHandler.point_width));
    }
}
