package com.company.GUI.InputGUI.GraphGUI;

import com.company.GUI.Database.DBHandler;
import org.jfree.data.xy.XYSeries;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class LoadGraphDialog extends JDialog {
    private JPanel contentPane;
    private JPanel xPanel;
    private JLabel formulaLabel;
    private JComboBox comboBox1;
    private JButton setButton;
    private JButton saveButton;
    private JButton deleteButton;
    private JButton buttonOK;
    private JButton buttonCancel;

    public LoadGraphDialog(XYSeries series, boolean stopState) {
        setContentPane(contentPane);
        setTitle("Загрузка графика");
        setSize(400, 220);

        // Загрузка иконки для кнопки
        try {
            BufferedImage deleteIcon = ImageIO.read(new File(DBHandler.resourcesPath + "trash.png"));
            deleteButton.setIcon(new ImageIcon(deleteIcon));
        }
        catch(IOException ex){}

        loadComboBox(comboBox1);

        setVisible(true);
        this.setAlwaysOnTop(true);

        setButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double [][] array = DBHandler.getGraphArray(comboBox1.getSelectedItem().toString());
                if(array != null) {
                    series.clear();
                    series.add(0, 0);
                    for (int i = 0; i < array[0].length; i++)
                        series.add(array[0][i], array[1][i]);

                    if(stopState)
                        series.add(Integer.MAX_VALUE, series.getY(series.getItemCount()-1));
                }
            }
        });

        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (comboBox1.getSelectedItem() == null)
                    return;

                Object[] options = {"Удалить", "Отмена"};
                int dialogResult = JOptionPane.showOptionDialog(
                        null,
                        "Удалить файл '" + comboBox1.getSelectedItem().toString() + "'?",
                        "Подтверждение",
                        JOptionPane.OK_CANCEL_OPTION,
                        JOptionPane.WARNING_MESSAGE,
                        null,
                        options,
                        options[0]
                );
                if(dialogResult == 0) {
                    DBHandler.deleteGraphFile(comboBox1.getSelectedItem().toString());
                    loadComboBox(comboBox1);
                }
            }
        });

        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String dialogResult = (String) JOptionPane.showInputDialog(
                        null,
                        "Введите название: ",
                        "Добавление нового файла точек графика",
                        JOptionPane.PLAIN_MESSAGE,
                        null,
                        null,
                        ""
                );


                if((dialogResult == null)
                        || (dialogResult.strip().equals(""))
                        || (dialogResult.strip().equals("temp1")
                        || (dialogResult.strip().equals("temp2"))
                ))
                    JOptionPane.showMessageDialog(
                            null,
                            "Недопустимое имя",
                            "Ошибка",
                            JOptionPane.ERROR_MESSAGE
                    );
                else {
                    int index = series.getItemCount()-1;
                    double y = (double) series.getY(index);
                    if (stopState)
                        series.remove(index);

                    DBHandler.addGraphFile(dialogResult, series.toArray());

                    if(stopState)
                        series.add(Integer.MAX_VALUE, y);

                    loadComboBox(comboBox1);
                }

            }
        });
    }

    public void loadComboBox(JComboBox cb){
        cb.removeAllItems();

        List<String> names = DBHandler.getGraphNames();
        for (String name : names) {
            cb.addItem(name);
        }
    }
}
