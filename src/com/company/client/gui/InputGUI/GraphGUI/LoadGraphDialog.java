package com.company.client.gui.InputGUI.GraphGUI;

import com.company.client.gui.DataHandler;
import com.company.client.gui.Database.DBHandler;
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

    public LoadGraphDialog(XYSeries series) {
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

                    int last_index = series.getItemCount() - 1;

                    if((double) series.getX(last_index) == (double) Integer.MAX_VALUE) {
                        if (!DataHandler.stop_state)
                            series.remove(last_index);
                    }
                    else if (DataHandler.stop_state)
                        series.add(Integer.MAX_VALUE, series.getY(last_index));
                }
                else {
                    String error_message = "Ошибка чтения '"+ comboBox1.getSelectedItem() + "'.\n\n" +
                            "Координаты должны быть записаны в формате: 'X;Y'\n" +
                            "Допустимы только числа\n" +
                            "Максимальное значение = 2.147483647E9\n" +
                            "Значения X не должны повторяться";
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, error_message, "Ошибка", JOptionPane.WARNING_MESSAGE);
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

                    if(DataHandler.stop_state)
                        series.remove(index);

                    DBHandler.addGraphFile(dialogResult, series.toArray());

                    if(DataHandler.stop_state)
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
