package com.company.GraphicalData.InputGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;

public class GraphPanel extends JPanel implements MouseListener, MouseMotionListener {
    //временное решение
    int drawField_xoffset = 10;
    int drawField_yoffset = 40;
    int drawField_width = 760;
    int drawField_height = 450;
    int xScale = 10;
    int yScale = 10;

    //список заданных координат точек
    ArrayList<Point> points = new ArrayList<Point>();

    JLabel label_placeholder = new JLabel("ЗДЕСЬ БУДЕТ ГРАФИЧЕСКИЙ ВВОД. (ПРОТОТИП: LMB для задания точки, RMB для удаления)");
    JLabel label_coord_placeholder = new JLabel("-,-");
    //Панель графического ввода
    public GraphPanel(){

        this.setBackground(Color.orange);
        this.add(label_placeholder);
        this.add(label_coord_placeholder);
    }
    public void paint(Graphics g) {
        super.paint(g);
        drawField(g);
    }
    public void drawField(Graphics g){
        Graphics2D g2D = (Graphics2D) g;

        //Отрисовка поля для ввода
        g2D.setPaint(Color.WHITE);
        g2D.fillRect(drawField_xoffset, drawField_yoffset, drawField_width, drawField_height);

        g2D.setPaint(Color.BLACK);
        Polygon arrow = new Polygon();
        arrow.addPoint(drawField_xoffset+15,drawField_yoffset +10);
        arrow.addPoint(drawField_xoffset+10, drawField_yoffset+20);
        arrow.addPoint(drawField_xoffset+20, drawField_yoffset+20);
        g2D.fill(arrow);
        g2D.drawLine(
                drawField_xoffset+15,
                drawField_yoffset +10,
                drawField_xoffset+15,
                drawField_height+drawField_yoffset-10
        );
        int middlePoint = drawField_yoffset + (drawField_height)/2;
        g2D.drawLine(
                drawField_xoffset+15,
                middlePoint,
                drawField_xoffset+drawField_width-10,
                middlePoint
        );
        arrow.reset();
        arrow.addPoint(drawField_xoffset+drawField_width-10, middlePoint);
        arrow.addPoint(drawField_xoffset+drawField_width-20, middlePoint-5);
        arrow.addPoint(drawField_xoffset+drawField_width-20, middlePoint+5);
        g2D.fill(arrow);

        //Отрисовка заданных точек
        g2D.setPaint(Color.BLUE);
        g2D.fillOval(drawField_xoffset+15-4, middlePoint-4, 8, 8);
        Point previous = new Point(drawField_xoffset+15, middlePoint);

        for (Point point:points){

            //отрисовка новой точки и соединение с предыдущей
            g2D.setPaint(Color.BLUE);
            g2D.fillOval(point.x-4, point.y-4, 8, 8);
            g2D.drawLine(previous.x, previous.y, point.x, point.y);
            previous = point;

            //Отрисовка сообщения о кооринатах добавленной точки
            g2D.setPaint(Color.GRAY);
            Point normPoint = getNormalCoordinates(point);
            String coordinates = new String("(" + normPoint.x + "," + normPoint.y + ")");
            g2D.drawString(coordinates, point.x-20, point.y+20);

        }
        addMouseListener(this);
        addMouseMotionListener(this);
    }

    //Перевод в декартову систему координат для вывода координат точек на графике
    Point getNormalCoordinates(Point p){
        int x = (p.x - drawField_xoffset - 15) / xScale;
        int y =(drawField_yoffset + (drawField_height)/2 - p.y) / yScale;
        return new Point(x, y);
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            int x = e.getX();
            int y = e.getY();
            if ((x > 10 && x < drawField_width + 10) && (y > 40 && y < drawField_height + 40))
                points.add(new Point(x, y));
            repaint();
        }
        else{
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

    @Override
    public void mouseMoved(MouseEvent e){
        int x = e.getX();
        int y = e.getY();
        Point normP = getNormalCoordinates(new Point(x,y));
        label_coord_placeholder.setText("(" + normP.x + "," + normP.y + ")");
    }
    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
