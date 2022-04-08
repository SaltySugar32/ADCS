package com.company.GUI;

import com.company.Simulation.SimulationVariables.SimulationGlobals;
import org.mariuszgromada.math.mxparser.Constant;
import org.mariuszgromada.math.mxparser.Expression;

/**
 * Обработчик входных данных
 */
public class DataHandler {

    // Статусы ввода исходных данных
    public static boolean env_param_input_status = false;
    public static boolean graph_input_status = false;

    // Параметры среды
    // У Толи в SimulationGlobals с ними сразу идут манипуляции, а мне нужны именно те данные, которые ввели
    public static double lameMu = 0;
    public static double lameLambda = 0;
    public static double materialDensity = 0;
    public static double coefficientNu = 0;

    // Параметры графика
    public static double xmin;
    public static double xmax;
    public static double xtick;
    public static double ymin;
    public static double ymax;
    public static double ytick;

    public static String unitOfTime;

    // параметры отображения
    public static int spline_precision;
    public static float spline_width;
    public static float lin_appr_width;
    public static float marker_width;
    public static int point_width;

    // Точки лин. аппроксимации
    // array[p1][p2]
    // p1 - 0(x)/1(y)
    // p2 - point num
    public static double[][] lin_appr_array;

    public static void setDefaultGraphAxisSettings(){
        xmin = 0;
        xmax = 10;
        xtick = 1;
        ymin = -5;
        ymax = 5;
        ytick = 1;
        unitOfTime = "нс";
    }

    public static void setDefaultGraphViewSettings(){
        spline_precision = 100;
        spline_width = 1;
        lin_appr_width = 1;
        marker_width = 1;
        point_width = 6;
    }

    /**
     * Функция обработки параметров графика
     * @param p1 xmin
     * @param p2 xmax
     * @param p3 xtick
     * @param p4 ymin
     * @param p5 ymax
     * @param p6 ytick
     * @return сообщение
     */
    public static String setGraphAxisSettings(String p1, String p2, String p3, String p4, String p5, String p6, String p7){
        double d1, d2, d3, d4, d5, d6;
        try{
            d1 = Double.parseDouble(p1);
        }
        catch (Exception e){return "мин(x) введен неверно";}
        try {
            d2 = Double.parseDouble(p2);
        }
        catch (Exception e){return "макс(x) введен неверно";}
        try{
            d3 = Double.parseDouble(p3);
        }
        catch (Exception e){return "шаг(x) введен неверно";}
        try{
            d4 = Double.parseDouble(p4);
        }
        catch (Exception e){return "мин(y) введен неверно";}
        try{
            d5 = Double.parseDouble(p5);
        }
        catch (Exception e){return "макс(y) введен неверно";}
        try{
            d6 = Double.parseDouble(p6);
        }
        catch (Exception e){return "шаг(y) введен неверно";}
        xmin = d1;
        xmax = d2;
        xtick = d3;
        ymin = d4;
        ymax = d5;
        ytick = d6;
        unitOfTime = p7;
        return "";
    }

    public static String setGraphViewSettings(String p1, String p2, String p3, String p4, String p5){
        int splinePrecision, pointWidth;
        float splineWidth, linWidth, markerWidth;
        try{
            splinePrecision = Integer.parseInt(p1);
        }
        catch (Exception e){return "Точность сплайна введена неверно";}
        try{
            splineWidth = Float.parseFloat(p2);
        }
        catch (Exception e){return "Ширина сплайна введена неверно";}
        try{
            linWidth = Float.parseFloat(p3);
        }
        catch (Exception e){return "Ширина лин. аппр. введена неверно";}
        try{
            markerWidth = Float.parseFloat(p4);
        }
        catch (Exception e){return "Ширина маркера введена неверно";}
        try{
            pointWidth = Integer.parseInt(p5);
        }
        catch (Exception e){return "Размер точки введен неверно";}
        spline_precision = splinePrecision;
        spline_width = splineWidth;
        lin_appr_width = linWidth;
        marker_width = markerWidth;
        point_width = pointWidth;
        return "";
    }

    /**
     * Обработка входных параметров среды
     *
     * @param p1 = Параметр Ламе μ
     * @param p2 = Параметр Ламе λ
     * @param p3 = Параметр плотность материала ρ
     * @param p4 = Коффициент разномодульности ν
     * @return сообщение об ошибке/успехе
     */
    public static String setEnvParams(String p1, String p2, String p3, String p4){
        double d1, d2, d3, d4;
        try{
            d1 = Double.parseDouble(p1);
            if (d1<=0) throw new Exception();
        }
        catch (Exception e){return "Параметр Ламе μ введен неверно";}

        try{
            d2 = Double.parseDouble(p2);
            if (d2<=0) throw new Exception();
        }
        catch (Exception e){return "Параметр Ламе λ введен неверно";}

        try{
            d3 = Double.parseDouble(p3);
            if (d3<=0) throw new Exception();
        }
        catch (Exception e){return "Параметр плотность материала ρ введен неверно";}

        try{
            d4 = Double.parseDouble(p4);
            if (d4 == 0) throw new Exception();
        }
        catch (Exception e){return "Коффициент разномодульности ν введен неверно";}

        // Передача данных в решатель
        SimulationGlobals.setSimulationGlobals(d1,d2,d3,d4);
        lameMu = d1;
        lameLambda = d2;
        materialDensity = d3;
        coefficientNu = d4;

        env_param_input_status = true;
        return "<html><font color='green'>Параметры введены</font></html>";
    }

    public static void setGraphInput(double[][] array){
        lin_appr_array = array;
        graph_input_status = true;
    }

    public static double[][] getFormulaArray(String p1, String p2){
        double precision = 0;

        try{
            precision = Double.parseDouble(p2);
        }
        catch (Exception e){return null;}

        int num = (int)((xmax - xmin)/precision) + 1;
        double[] t_array = new double[num];
        t_array[0] = xmin;
        for(int i=1; i<t_array.length; i++)
            t_array[i] = t_array[i-1]+precision;

        double[][] array = new double[2][t_array.length];

        for(int i=0; i< t_array.length;i++) {
            Constant t = new Constant("t="+t_array[i]);
            Expression e = new Expression(p1, t);
            array[0][i] = t_array[i];
            array[1][i] = e.calculate();
        }

        return array;
    }
}
