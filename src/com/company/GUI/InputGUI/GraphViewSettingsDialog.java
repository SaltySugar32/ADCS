package com.company.GUI.InputGUI;

import com.company.GUI.DataHandler;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphViewSettingsDialog extends JDialog {
    private JPanel contentPane;
    private JPanel xPanel;
    private JTextField precisionField;
    private JLabel precisionLabel;
    private JTextField widthField;
    private JLabel widthLabel;
    private JButton setButton;
    private JButton defaultButton;
    private JLabel statusLabel;
    private JTextField markerField;
    private JLabel markerLabel;

    public GraphViewSettingsDialog(JFreeChart chart) {
        add(contentPane);
        setTitle("Параметры");
        setSize(400, 350);

        setVisible(true);
        this.setAlwaysOnTop(true);
        fillTextFields();

        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText(DataHandler.setGraphViewSettings(
                        precisionField.getText(),
                        widthField.getText(),
                        markerField.getText()
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
        String precision = Double.toString(DataHandler.spline_precision);
        precision = precision.substring(0, precision.length()-1);
        precision = precision.substring(0, precision.length()-1);
        precisionField.setText(precision);
        widthField.setText(Double.toString(DataHandler.spline_width));
        markerField.setText(Double.toString(DataHandler.marker_width));
    }
}
