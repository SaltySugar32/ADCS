package com.company.GraphicalData;

import com.company.Simulation.SimulationVariables.SimulationGlobals;

//проверка/обработка входных данных
public class DataHandler {
    static boolean paramInputStatus = false;
    static boolean graphInputStatus = false;

    public static boolean getParamInputStatus(){
        return paramInputStatus;
    }

    public static boolean getGraphInputStatus(){
        return graphInputStatus;
    }

    public static String setInputParams(String p1, String p2, String p3, String p4){
        try{Double.parseDouble(p1);} catch (Exception e){return "Параметр Ламе μ введен неверно";}
        try{Double.parseDouble(p2);} catch (Exception e){return "Параметр Ламе λ введен неверно";}
        try{Double.parseDouble(p3);} catch (Exception e){return "Параметр плотность материала ρ введен неверно";}
        try{Double.parseDouble(p4);} catch (Exception e){return "Коффициент разномодульности ν введен неверно";}
        SimulationGlobals.setSimulationGlobals(Double.parseDouble(p1), Double.parseDouble(p2), Double.parseDouble(p3), Double.parseDouble(p4));
        paramInputStatus = true;
        return "<html><font color='green'>Параметры введены</font></html>";
    }
}
