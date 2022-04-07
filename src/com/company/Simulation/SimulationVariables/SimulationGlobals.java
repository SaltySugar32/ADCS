package com.company.Simulation.SimulationVariables;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Глобальные переменные процесса симуляции, созданы для манипуляции переменными среды симуляции деформации
 * <br>
 * Здесь не должно быть ни одной функции, кроме get/set
 */
public class SimulationGlobals {

    //----------------------НЕОТСОРТИРОВАННЫЕ ПЕРЕМЕННЫЕ-----------------------

    static Comparator<WaveFront> comparator = Comparator.comparingDouble(o -> o.getA2() * SimulationTime.getSimulationTime());

    //-------------------------------------------------------------------------

    //---------------------ХАРАКТЕРИСТИКИ СРЕДЫ СИМУЛЯЦИИ----------------------

    /**
     * Статус инициализации переменных среды
     */
    static boolean isInitialized;
    /**
     * Параметр Ламе Mu (Мю)
     */
    static double lameMu;
    /**
     * Параметр Ламе Lambda (Лямбда)
     */
    static double lameLambda;
    /**
     * Параметр плотность материала
     */
    static double materialDensity;
    /**
     * Параметр - коэффициент, связывающий деформации материала только с изменениями объёма
     */
    static double coefficientNu;
    /**
     * Множитель на ГИГАПаскали (ГПа) для каждого из параметров выше
     */
    static double powPA;

    static {
        isInitialized = false;
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

    static {
        currentWavePicture = new ArrayList<>();
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
        SimulationGlobals.isInitialized = true;
        SimulationGlobals.lameMu = lameMu * Math.pow(10, powPA);
        SimulationGlobals.lameLambda = lameLambda * Math.pow(10, powPA);
        SimulationGlobals.materialDensity = materialDensity * Math.pow(10, powPA);
        SimulationGlobals.coefficientNu = coefficientNu * Math.pow(10, powPA);


    } //Отсутствует проверка введенных параметров

    public static boolean isInitialized() {
        return isInitialized;
    }

    public static void setCurrentWavePicture(ArrayList<WaveFront> currentWavePicture) {
        SimulationGlobals.currentWavePicture = currentWavePicture;
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

    public static Comparator<WaveFront> getComparator() {
        return comparator;
    }

    //-------------------------------------------------------------------------

}
