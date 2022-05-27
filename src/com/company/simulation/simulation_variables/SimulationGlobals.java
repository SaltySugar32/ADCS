package com.company.simulation.simulation_variables;

import com.company.simulation.simulation_variables.border_displacement.LinearFunction;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

/**
 * Глобальные переменные процесса симуляции, созданы для манипуляции переменными среды симуляции деформации
 * <br>
 * Здесь не должно быть ни одной функции, кроме get/set
 */
public class SimulationGlobals {

    //----------------------НЕОТСОРТИРОВАННЫЕ ПЕРЕМЕННЫЕ-----------------------


    //-------------------------------------------------------------------------

    //---------------------ХАРАКТЕРИСТИКИ СРЕДЫ СИМУЛЯЦИИ----------------------

    /**
     * Множитель на ГИГАПаскали (ГПа) для каждого из параметров выше
     */
    static double powPA;

    /**
     * Параметр Ламе Mu (Мю)
     */
    static double lameMu;

    /**
     * Параметр Ламе Lambda (Лямбда)
     */
    static double lameLambda;

    /**
     * Параметр - коэффициент, связывающий деформации материала только с изменениями объёма
     */
    static double coefficientNu;

    /**
     * Параметр плотность материала
     */
    static double materialDensity;

    static {
        lameMu = 0;
        lameLambda = 0;
        materialDensity = 0;
        coefficientNu = 0;
        powPA = 9;
    }

    /**
     * Текущая волновая картина, содержащая в себе каждый из волновых фронтов
     */
    static ArrayList<WaveFront> currentWavePicture;

    /**
     * Воздействие на границу материала
     */
    static ArrayList<LinearFunction> borderDisplacementFunctions;

    static {
        currentWavePicture = new ArrayList<>();
        borderDisplacementFunctions = new ArrayList<>();
    }

    //-------------------------------------------------------------------------

    //--------------------------------SETTERS----------------------------------

    /**
     * Функция, устанавливающая четыре параметра материала в кратной единице powPA
     *
     * @param lameMu          Коэффициент Ламе Мю
     * @param lameLambda      Коэффициент Ламе Лямбда
     * @param materialDensity Плотность материала
     * @param coefficientNu   Коэффициент разномодульности материала
     */
    public static void setSimulationGlobals(double lameMu, double lameLambda, double materialDensity, double coefficientNu) {
        SimulationGlobals.lameMu = lameMu * Math.pow(10, powPA);
        SimulationGlobals.lameLambda = lameLambda * Math.pow(10, powPA);
        SimulationGlobals.coefficientNu = coefficientNu * Math.pow(10, powPA);
        SimulationGlobals.materialDensity = materialDensity;
    }

    public static void setCurrentWavePicture(ArrayList<WaveFront> currentWavePicture) {
        SimulationGlobals.currentWavePicture = currentWavePicture;
    }

    public static void setBorderDisplacementFunctions(ArrayList<LinearFunction> borderDisplacementFunctions) {
        SimulationGlobals.borderDisplacementFunctions = borderDisplacementFunctions;
    }

    //-------------------------------------------------------------------------

    //--------------------------------GETTERS----------------------------------

    public static double getLameMu() {
        return lameMu;
    }

    public static double getLameLambda() {
        return lameLambda;
    }

    public static double getMaterialDensity() {
        return materialDensity;
    }

    public static double getCoefficientNu() {
        return coefficientNu;
    }

    public static ArrayList<WaveFront> getCurrentWavePicture() {
        return currentWavePicture;
    }

    public static ArrayList<LinearFunction> getBorderDisplacementFunctions() {
        return borderDisplacementFunctions;
    }

    //-------------------------------------------------------------------------

}
