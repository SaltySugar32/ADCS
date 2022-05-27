package com.company.GUI.InputGUI.GraphGUI;

import com.company.GUI.DataHandler;
import com.company.GUI.InputGUI.GraphGUI.GraphForm;
import org.jfree.chart.JFreeChart;

import javax.swing.*;
import javax.xml.crypto.Data;
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
    private JComboBox comboBox1;

    public GraphAxisSettingsDialog(JFreeChart chart){
        add(mainPanel);
        setTitle("Параметры");
        setSize(350, 400);
        setVisible(true);
        this.setAlwaysOnTop(true);

        fillTextFields();
        initComboBox();

        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                statusLabel.setText(DataHandler.setGraphAxisSettings(
                        xminField.getText(),
                        xmaxField.getText(),
                        xtickField.getText(),
                        yminField.getText(),
                        ymaxField.getText(),
                        ytickField.getText(),
                        comboBox1.getSelectedItem().toString()
                ));
                GraphForm.setGraphAxisSettings(chart);
            }
        });

        defaultButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                DataHandler.setDefaultGraphAxisSettings();
                GraphForm.setGraphAxisSettings(chart);
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

        comboBox1.setSelectedItem(DataHandler.unitOfTime);
    }

    private void initComboBox(){
        comboBox1.removeAllItems();

        comboBox1.addItem("мс");
        comboBox1.addItem("мкс");

        comboBox1.setSelectedItem(DataHandler.unitOfTime);
    }
}
