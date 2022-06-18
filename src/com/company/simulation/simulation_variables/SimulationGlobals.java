package com.company.simulation.simulation_variables;

import com.company.simulation.simulation_variables.border_displacement.LinearFunction;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

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

    /**
     * Характеристическая скорость при растяжении
     */
    static double characteristicsSpeedStretching;

    /**
     * Характеристическая скорость при сжатии
     */
    static double characteristicsSpeedCompression;

    static {
        lameMu = 0;
        lameLambda = 0;
        materialDensity = 0;
        coefficientNu = 0;
        powPA = 9;
        characteristicsSpeedStretching = 0;
        characteristicsSpeedCompression = 0;
    }

    /**
     * Текущая волновая картина, содержащая в себе каждый из волновых фронтов
     */
    static volatile ArrayList<LayerDescription> currentWavePicture;

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
        assert (materialDensity != 0.0);

        SimulationGlobals.lameMu = lameMu * Math.pow(10, powPA);
        SimulationGlobals.lameLambda = lameLambda * Math.pow(10, powPA);
        SimulationGlobals.coefficientNu = coefficientNu * Math.pow(10, powPA);
        SimulationGlobals.materialDensity = materialDensity;


        /* (\lambda + 2 * \mu - 2 * \nu) / (\rho)
         * Вычисление характеристической скорости при растяжении
         */
        SimulationGlobals.characteristicsSpeedStretching =
                DenoteFactor.METERS.toMillis(
                        Math.sqrt(
                                (SimulationGlobals.getLameLambda() + 2 * SimulationGlobals.getLameMu() -
                                        2 * SimulationGlobals.getCoefficientNu()) / (SimulationGlobals.getMaterialDensity())
                        )
                );

        /* (\lambda + 2 * \mu + 2 * \nu) / (\rho)
         * Вычисление характеристической скорости при сжатии
         */
        SimulationGlobals.characteristicsSpeedCompression =
                DenoteFactor.METERS.toMillis(
                        Math.sqrt(
                                (SimulationGlobals.getLameLambda() + 2 * SimulationGlobals.getLameMu() +
                                        2 * SimulationGlobals.getCoefficientNu()) / (SimulationGlobals.getMaterialDensity())
                        )
                );
    }

    public static void setCurrentWavePicture(ArrayList<LayerDescription> currentWavePicture) {
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

    public static ArrayList<LayerDescription> getCurrentWavePicture() {
        return currentWavePicture;
    }

    public static ArrayList<LinearFunction> getBorderDisplacementFunctions() {
        return borderDisplacementFunctions;
    }

    public static double getCharacteristicsSpeedStretching() {
        return characteristicsSpeedStretching;
    }

    public static double getCharacteristicsSpeedCompression() {
        return characteristicsSpeedCompression;
    }

    //-------------------------------------------------------------------------

}
