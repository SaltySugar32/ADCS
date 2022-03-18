package com.company.GUI;

import com.company.Simulation.SimulationVariables.SimulationGlobals;

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
    // -----------------------------------------------------------------------------------------------------------------
}
