package com.company.GUI;

import com.company.Simulation.SimulationVariables.SimulationGlobals;

/**
 * Обработчик входных данных
 */
public class GUIDataHandler {

    // Статусы ввода
    static boolean paramInputStatus = false;
    static boolean graphInputStatus = false;

    // Гетеры
    public static boolean getParamInputStatus(){
        return paramInputStatus;
    }

    public static boolean getGraphInputStatus(){
        return graphInputStatus;
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
    public static String setInputParams(String p1, String p2, String p3, String p4){
        try{
            if (Double.parseDouble(p1)<=0) throw new Exception();
        }
        catch (Exception e){return "Параметр Ламе μ введен неверно";}

        try{
            if (Double.parseDouble(p2)<=0) throw new Exception();
        }
        catch (Exception e){return "Параметр Ламе λ введен неверно";}

        try{
            if (Double.parseDouble(p3)<=0) throw new Exception();
        }
        catch (Exception e){return "Параметр плотность материала ρ введен неверно";}

        try{
            if (Double.parseDouble(p4)==0) throw new Exception();
        }
        catch (Exception e){return "Коффициент разномодульности ν введен неверно";}

        // Передача данных в решатель
        SimulationGlobals.setSimulationGlobals(Double.parseDouble(p1), Double.parseDouble(p2), Double.parseDouble(p3), Double.parseDouble(p4));
        paramInputStatus = true;
        return "<html><font color='green'>Параметры введены</font></html>";
    }
    // -----------------------------------------------------------------------------------------------------------------
}
