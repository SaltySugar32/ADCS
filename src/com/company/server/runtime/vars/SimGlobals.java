package com.company.server.runtime.vars;

import com.company.server.runtime.enums.DenoteFactor;
import com.company.server.simulation.border.BorderDescription;
import com.company.server.simulation.types.LayerDescription;

import java.util.ArrayList;

/**
 * Глобальные переменные процесса симуляции, созданы для манипуляции переменными среды симуляции деформации
 * <br>
 * Здесь не должно быть ни одной функции, кроме get/set
 */
public class SimGlobals {

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
    static ArrayList<BorderDescription> borderDisplacementFunctions;

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

        SimGlobals.lameMu = lameMu * Math.pow(10, powPA);
        SimGlobals.lameLambda = lameLambda * Math.pow(10, powPA);
        SimGlobals.coefficientNu = coefficientNu * Math.pow(10, powPA);
        SimGlobals.materialDensity = materialDensity;


        /* (\lambda + 2 * \mu - 2 * \nu) / (\rho)
         * Вычисление характеристической скорости при растяжении
         */
        SimGlobals.characteristicsSpeedStretching =
                DenoteFactor.METERS.toMillis(
                        Math.sqrt(
                                (SimGlobals.getLameLambda() + 2 * SimGlobals.getLameMu() -
                                        2 * SimGlobals.getCoefficientNu()) / (SimGlobals.getMaterialDensity())
                        )
                );

        /* (\lambda + 2 * \mu + 2 * \nu) / (\rho)
         * Вычисление характеристической скорости при сжатии
         */
        SimGlobals.characteristicsSpeedCompression =
                DenoteFactor.METERS.toMillis(
                        Math.sqrt(
                                (SimGlobals.getLameLambda() + 2 * SimGlobals.getLameMu() +
                                        2 * SimGlobals.getCoefficientNu()) / (SimGlobals.getMaterialDensity())
                        )
                );
    }

    public static void setCurrentWavePicture(ArrayList<LayerDescription> currentWavePicture) {
        SimGlobals.currentWavePicture = currentWavePicture;
    }

    public static void setBorderDisplacementFunctions(ArrayList<BorderDescription> borderDisplacementFunctions) {
        SimGlobals.borderDisplacementFunctions = borderDisplacementFunctions;
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

    public static ArrayList<BorderDescription> getBorderDisplacementFunctions() {
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
