package com.company.Simulation.SimulationVariables;

import java.util.ArrayList;

//Глобальные переменные процесса симуляции, созданы для манипуляции переменными среды симуляции деформации
public class SimulationGlobals {

    //Статус инициализации переменных среды
    static boolean isInitialized;
    //Параметр Ламе Mu (Мю)
    static double lameMu;
    //Параметр Ламе Lambda (Лямбда)
    static double lameLambda;
    //Параметр плотность материала
    static double materialDensity;
    //Параметр - коэффициент, связывающий деформации материала только с изменениями объёма
    static double coefficientNu;
    //Все переменные выше - константы материала

    static ArrayList<WaveFront> currentWavePicture;

    static {
        isInitialized = false;
        lameMu = 0;
        lameLambda = 0;
        materialDensity = 0;
        coefficientNu = 0;

        currentWavePicture = new ArrayList<>();
    }

    public static void setSimulationGlobals(double lameMu, double lameLambda, double materialDensity, double coefficientNu) {
        SimulationGlobals.isInitialized = true;
        SimulationGlobals.lameMu = lameMu;
        SimulationGlobals.lameLambda = lameLambda;
        SimulationGlobals.materialDensity = materialDensity;
        SimulationGlobals.coefficientNu = coefficientNu;
    } //Отсутствует проверка введенных параметров

    public static void setCurrentWavePicture(ArrayList<WaveFront> currentWavePicture) {
        SimulationGlobals.currentWavePicture = currentWavePicture;
    }

    public static boolean isInitialized() {
        return isInitialized;
    }

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
}
