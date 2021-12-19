package com.company.GUI.InputGUI;

import com.company.GUI.GUIDataHandler;
import com.company.GUI.GUIGlobals;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

/**
 * Панель графического ввода
 */
public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener {

    //список заданных координат точек
    ArrayList<Point> points = new ArrayList<Point>();

    // Метки - плейсхолдеры
    JLabel label_placeholder = new JLabel("ЗДЕСЬ БУДЕТ ГРАФИЧЕСКИЙ ВВОД. (ПРОТОТИП: LMB для задания точки, RMB для удаления)");

    // Текущая позиция мыши
    Point currMousePosition = new Point(0,-100);

    JButton resetButton = new JButton("Сбросить точки");
    JLabel addPointLabel = new JLabel("Введите координаты точки: ");
    JTextField textFieldX = new JTextField(5);
    JTextField textFieldY = new JTextField(5);
    JButton addPointButton = new JButton("Добавить точку");
    JButton inputButton = new JButton("Ввести текущие точки");
    JLabel statusLabel = new JLabel("");

    /**
     * Панель графического ввода
     */
    public GraphPanel(){

        this.setBackground(GUIGlobals.background_color);
        SpringLayout layout = new SpringLayout();
        this.setLayout(layout);

        this.add(label_placeholder);
        this.add(addPointLabel);
        this.add(textFieldX);
        this.add(textFieldY);
        this.add(addPointButton);
        this.add(resetButton);
        this.add(inputButton);
        this.add(statusLabel);

        int offset = (GUIGlobals.window_width-label_placeholder.getText().length() * 6)/2;

        layout.putConstraint(SpringLayout.WEST , label_placeholder, offset, SpringLayout.WEST , this);

        layout.putConstraint(SpringLayout.WEST , addPointLabel, 10, SpringLayout.WEST , this);
        layout.putConstraint(SpringLayout.NORTH, addPointLabel, 22, SpringLayout.NORTH, label_placeholder);

        layout.putConstraint(SpringLayout.WEST , textFieldX, 10, SpringLayout.EAST , addPointLabel);
        layout.putConstraint(SpringLayout.NORTH, textFieldX, 22, SpringLayout.NORTH, label_placeholder);

        layout.putConstraint(SpringLayout.WEST , textFieldY, 10, SpringLayout.EAST , textFieldX);
        layout.putConstraint(SpringLayout.NORTH, textFieldY, 22, SpringLayout.NORTH, label_placeholder);

        layout.putConstraint(SpringLayout.WEST , addPointButton, 10, SpringLayout.EAST , textFieldY);
        layout.putConstraint(SpringLayout.NORTH, addPointButton, 20, SpringLayout.NORTH, label_placeholder);

        layout.putConstraint(SpringLayout.WEST , resetButton, 100, SpringLayout.EAST, addPointButton);
        layout.putConstraint(SpringLayout.NORTH, resetButton, 20, SpringLayout.NORTH, label_placeholder);

        layout.putConstraint(SpringLayout.WEST , inputButton, 10, SpringLayout.EAST , resetButton);
        layout.putConstraint(SpringLayout.NORTH, inputButton, 20, SpringLayout.NORTH, label_placeholder);

        layout.putConstraint(SpringLayout.WEST , statusLabel, 10, SpringLayout.EAST , inputButton);
        layout.putConstraint(SpringLayout.NORTH, statusLabel, 20, SpringLayout.NORTH, label_placeholder);
    }

    /**
     * Отрисовка поля ввода на панеле
     *
     * @param g Графика
     */
    public void paint(Graphics g) {
        super.paint(g);
        drawField(g);
        drawMouseCoord(g);
    }

    /**
     * Отрисовка поля
     *
     * @param g Графика
     */
    public void drawField(Graphics g){
        Graphics2D g2D = (Graphics2D) g;

        // Отрисовка поля для ввода
        g2D.setPaint(Color.WHITE);
        g2D.fillRect(GUIGlobals.drawField_xoffset, GUIGlobals.drawField_yoffset, GUIGlobals.drawField_width, GUIGlobals.drawField_height);

        // Отрисовка осей координат
            // Отрисовка оси Oy
            g2D.setPaint(Color.BLACK);

            Polygon arrow = new Polygon();
            arrow.addPoint(GUIGlobals.drawField_xoffset,GUIGlobals.drawField_yoffset -10);
            arrow.addPoint(GUIGlobals.drawField_xoffset-5, GUIGlobals.drawField_yoffset);
            arrow.addPoint(GUIGlobals.drawField_xoffset+5, GUIGlobals.drawField_yoffset);
            g2D.fill(arrow);
            g2D.setFont(new Font("TimesRoman", Font.BOLD, 15));
            g2D.drawString("u(0,t)", GUIGlobals.drawField_xoffset+10, GUIGlobals.drawField_yoffset-5);

            g2D.setStroke(new BasicStroke(2));
            g2D.drawLine(
                    GUIGlobals.drawField_xoffset,
                    GUIGlobals.drawField_yoffset,
                    GUIGlobals.drawField_xoffset,
                    GUIGlobals.drawField_height+GUIGlobals.drawField_yoffset
            );

            //Отрисовка оси Ox
            arrow.reset();
            arrow.addPoint(GUIGlobals.drawField_xoffset+GUIGlobals.drawField_width+10, GUIGlobals.middlePoint);
            arrow.addPoint(GUIGlobals.drawField_xoffset+GUIGlobals.drawField_width, GUIGlobals.middlePoint-5);
            arrow.addPoint(GUIGlobals.drawField_xoffset+GUIGlobals.drawField_width, GUIGlobals.middlePoint+5);
            g2D.fill(arrow);

            g2D.drawString("t", GUIGlobals.drawField_xoffset+GUIGlobals.drawField_width+2, GUIGlobals.middlePoint-10);

            g2D.setStroke(new BasicStroke(3));
            g2D.drawLine(
                    GUIGlobals.drawField_xoffset,
                    GUIGlobals.middlePoint,
                    GUIGlobals.drawField_xoffset+GUIGlobals.drawField_width,
                    GUIGlobals.middlePoint
            );

        //Отрисовка заданных точек
        g2D.setPaint(Color.BLUE);
        Point previous = new Point(GUIGlobals.drawField_xoffset, GUIGlobals.middlePoint);
        g2D.fillOval(previous.x - 4, previous.y - 4, 8, 8);

        g2D.setFont(new Font("TimesRoman", Font.PLAIN, 12));
        for (Point point:points){

            //отрисовка новой точки и соединение с предыдущей
            g2D.setPaint(Color.BLUE);
            g2D.fillOval(point.x-4, point.y-4, 8, 8);
            g2D.drawLine(previous.x, previous.y, point.x, point.y);
            previous = point;

            //Отрисовка сообщения о кооринатах добавленной точки
            g2D.setPaint(Color.GRAY);
            DecCoord decCoord = new DecCoord(point);
            String coordinates = new String("(" + decCoord.x + "," + decCoord.y + ")");
            g2D.drawString(coordinates, point.x-20, point.y+20);

        }

        // Добавление обработчиков событий мыши
        addMouseListener(this);
        addMouseMotionListener(this);

        /**
         * Обработчики нажатия конопок
         */
        addPointButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                boolean status = true;
                try {
                    if (Float.parseFloat(textFieldX.getText())<=0) throw new Exception();
                }
                catch (Exception exc){status = false;}
                try {
                    if (Float.parseFloat(textFieldY.getText())<=0) throw new Exception();
                }
                catch (Exception exc){status = false;}

                if (status){
                    DecCoord decCoord = new DecCoord();
                    points.add(decCoord.getPoint(Float.parseFloat(textFieldX.getText()), Float.parseFloat(textFieldY.getText())));
                    repaint();
                }
                else statusLabel.setText("Координаты введены неверно");
            }
        });

        resetButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                points.clear();
                repaint();
            }
        });
    }

    /**
     * Отрисовка оординат мыши
     *
     * @param g Графика
     */
    public void drawMouseCoord(Graphics g){
        Graphics2D g2D = (Graphics2D) g;
        g2D.setPaint(Color.GRAY);
        DecCoord decCoord = new DecCoord(new Point(currMousePosition.x, currMousePosition.y));
        String coordinates = "(" + decCoord.x + "," + decCoord.y + ")";
        g2D.drawString(coordinates, currMousePosition.x-10, currMousePosition.y-5);
    }

    /**
     *  Класс - обработчик координаты точки
     */
    class DecCoord{
        public float x;
        public float y;

        DecCoord(){}

        DecCoord(Point p){
            // преобразование точки на поле в координаты

//            OldRange = (OldMax - OldMin)
//            NewRange = (NewMax - NewMin)
//            NewValue = (((OldValue - OldMin) * NewRange) / OldRange) + NewMin

            this.x = (float) ((p.x - GUIGlobals.drawField_xoffset)*GUIGlobals.drawField_xScale) / GUIGlobals.drawField_width;
            this.y = - ((float) ((p.y - GUIGlobals.drawField_yoffset)*GUIGlobals.drawField_yScale) / GUIGlobals.drawField_height - (float)GUIGlobals.drawField_yScale/2);
        }

        public Point getPoint(float x, float y){
            int newx =(int)( x * GUIGlobals.drawField_width / GUIGlobals.drawField_xScale + GUIGlobals.drawField_xoffset);
            int newy =(int)( (-y + GUIGlobals.drawField_yScale/2)  * GUIGlobals.drawField_height / GUIGlobals.drawField_yScale + GUIGlobals.drawField_yoffset);
            return new Point(newx, newy);
        }


    }

    /**
     * Обработчик клика мыши
     */
    @Override
    public void mouseClicked(MouseEvent e) {

        // клик ЛКМ
        if (SwingUtilities.isLeftMouseButton(e)) {

            // Получение позиции мыши
            int x = e.getX();
            int y = e.getY();

            // Добавление точки
            if ((x > GUIGlobals.drawField_xoffset && x < GUIGlobals.drawField_width + GUIGlobals.drawField_xoffset)
                    &&
                    (y > GUIGlobals.drawField_yoffset && y < GUIGlobals.drawField_height + GUIGlobals.drawField_yoffset))
                points.add(new Point(x, y));
            repaint();
        }

        // клик другой кнопки мыши
        else{

            // Удаление предыдущей точки
            if (points.size() > 0)
                points.remove(points.size()-1);
            repaint();
        }

    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseDragged(MouseEvent e) {

    }

    /**
     * Обработчик передвижения мыши (ДЛЯ ТЕСТИРОВАНИЯ)
     */
    @Override
    public void mouseMoved(MouseEvent e){

        // Получение позиции мыши
        int x = e.getX();
        int y = e.getY();

        // Отображение координат для тестирования
        if ((x > GUIGlobals.drawField_xoffset && x < GUIGlobals.drawField_width + GUIGlobals.drawField_xoffset)
                &&
                (y > GUIGlobals.drawField_yoffset && y < GUIGlobals.drawField_height + GUIGlobals.drawField_yoffset))
            currMousePosition = new Point(x,y);
        else
            currMousePosition = new Point(0, -100);
        repaint();

    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
