package com.company.GUI.InputGUI;

import com.company.GUI.DataHandler;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GraphAxisSettingsDialog extends JDialog {
    private JPanel mainPanel;
    private JButton setButton;
    private JPanel ButtonPanel;
    private JButton defaultButton;
    private JPanel textFieldsPanel;
    private JPanel xPanel;
    private JTextField xminField;
    private JTextField xmaxField;
    private JTextField xtickField;
    private JLabel xminLabel;
    private JLabel xmaxLabel;
    private JLabel xtickLabel;
    private JPanel yPanel;
    private JLabel yminLabel;
    private JLabel ymaxLabel;
    private JLabel ytickLabel;
    private JTextField yminField;
    private JTextField ymaxField;
    private JTextField ytickField;
    private JLabel statusLabel;

    public GraphAxisSettingsDialog(JFreeChart chart){
        add(mainPanel);
        setTitle("Параметры");
        setSize(350, 350);
        //setModal(true);
        //setLocationRelativeTo(null);
        setVisible(true);
        this.setAlwaysOnTop(true);

        fillTextFields();

        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText(DataHandler.setGraphAxisSettings(
                        xminField.getText(),
                        xmaxField.getText(),
                        xtickField.getText(),
                        yminField.getText(),
                        ymaxField.getText(),
                        ytickField.getText()
                ));
                GraphForm.setGraphSettings(chart);
            }
        });

        defaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataHandler.setDefaultGraphAxisSettings();
                GraphForm.setGraphSettings(chart);
                fillTextFields();
            }
        });
    }

    /**
     * Функция записывает во все TextField текущие значения
     */
    private void fillTextFields(){
        xminField.setText(Double.toString(DataHandler.xmin));
        xmaxField.setText(Double.toString(DataHandler.xmax));
        xtickField.setText(Double.toString(DataHandler.xtick));

        yminField.setText(Double.toString(DataHandler.ymin));
        ymaxField.setText(Double.toString(DataHandler.ymax));
        ytickField.setText(Double.toString(DataHandler.ytick));
    }
}
