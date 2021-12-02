package com.company.Simulation.SimulationVariables;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * Глобальные переменные процесса симуляции, созданы для манипуляции переменными среды симуляции деформации
 */
public class SimulationGlobals {

    //----------------------НЕОТСОРТИРОВАННЫЕ ПЕРЕМЕННЫЕ-----------------------

    static Comparator<WaveFront> comparator = Comparator.comparingDouble(o -> o.getSpeed() * SimulationGlobals.getSimulationTime());

    //-------------------------------------------------------------------------

    //------------------------ТЕКУЩЕЕ ВРЕМЯ СИМУЛЯЦИИ--------------------------

    static double simulationTime = 0.0;
    /**
     * В районе от 0.1 * 10^-6 до 1 * 10^-6 по текущей задумке
     */
    static double simulationTimeDelta;
    static double simulationTimePow;
    /**
     * Статус инициализации переменных среды
     */
    static boolean isInitialized;

    //-------------------------------------------------------------------------

    //---------------------ИЗМЕНЕНИЕ ВРЕМЕНИ ЗА ОПЕРАЦИЮ-----------------------
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
    static double powPA = 9;

    //-------------------------------------------------------------------------

    //---------------------ХАРАКТЕРИСТИКИ СРЕДЫ СИМУЛЯЦИИ----------------------
    /**
     * Текущая волновая картина, содержащая в себе каждый из волновых фронтов
     */
    static ArrayList<WaveFront> currentWavePicture;

    static {
        simulationTimePow = -6;
        simulationTimeDelta = 0.5 * Math.pow(10, SimulationGlobals.simulationTimePow);
    }

    static {
        isInitialized = false;
        lameMu = 0;
        lameLambda = 0;
        materialDensity = 0;
        coefficientNu = 0;

        currentWavePicture = new ArrayList<>();
    }

    public static double getSimulationTime() {
        return simulationTime;
    }

    public static void setSimulationTime(double simulationTime) {
        SimulationGlobals.simulationTime = simulationTime;
    }
    //Все переменные выше - константы материала

    public static void nextSimulationTime() {
        simulationTime += simulationTimeDelta;
    }

    public static double getSimulationTimeDelta() {
        return simulationTimeDelta;
    }

    /**
     * Ввод длительности по времени каждого шага симуляции
     *
     * @param simulationTimeDelta В районе от 0.1 до 1
     */
    public static void setSimulationTimeDelta(double simulationTimeDelta) {
        SimulationGlobals.simulationTimeDelta = simulationTimeDelta * Math.pow(10, simulationTimePow);
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

    public static void sortCurrentWavePicture(ArrayList<WaveFront> currentWavePicture) {
        currentWavePicture.sort(comparator);
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

    //-------------------------------------------------------------------------

}

