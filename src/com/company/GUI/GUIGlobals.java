package com.company.GUI;

import java.awt.*;

/**
 * Глобальные параметры GUI
 */
public class GUIGlobals {

    // Размер окна
    public static int window_width = 1050;
    public static int window_height = 950;

    // Цвет бекграунда
    public static Color background_color = Color.decode("#A9CADC");

    // Позиция разделителя inputPanel и graphPanel
    public static int splitPane_position = 170;


    // Параметры поля графического ввода
        // Сдвиги от края окна
        public static int drawField_xoffset = 10;
        public static int drawField_yoffset = 60;

        // необходимый сдвиг из-за стандартных параметров окна
        static int drawField_yoffset2 = 110;

        // Размер поля
//        public static int drawField_width = window_width - 4 * drawField_xoffset;
//        public static int drawField_height = window_height - splitPane_position - drawField_yoffset - drawField_yoffset2;
        public static int drawField_width = 1000;
        public static int drawField_height = 600;
        public static int middlePoint = GUIGlobals.drawField_yoffset + (GUIGlobals.drawField_height)/2;

        // Параметры для преобразования координат
        public static int drawField_xScale = 10;
        public static int drawField_yScale = 10;

    // Размеры кнопок
    public static Dimension button_dimension = new Dimension(50, 50);
}
