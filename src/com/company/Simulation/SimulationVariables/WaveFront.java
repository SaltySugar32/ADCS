package com.company.Simulation.SimulationVariables;

import com.company.Simulation.SimulationFunctions.WaveFrontComputations;

//Описание волнового фронта
//Пока далеко от истины
public class WaveFront { //U(x,t) = A1 * x + A2 * t + A0

    //---------------------------ПАРАМЕТРЫ ВОЛНЫ-----------------------------

    /**
     * Зависимость смещения на границе волнового фронта от координаты x - тензор малых деформаций e
     */
    double A1;
    /**
     * Зависимость смещения от времени
     */
    double A2;
    /**
     * Смещение по координате / задержка во времени
     */
    double A0;

    /**
     * Тензор напряжений Эйлера-Коши по оси Ox - \sigma в формулах
     */
    double tension;

    /**
     * Характеристическая скорость волнового фронта
     */
    double characteristicSpeed;

    double speed;

    {
        tension = 0;
        characteristicSpeed = 0;
        speed = 0;
    }

    //-------------------------------SETTERS--------------------------------

    public void setWaveFront(double A1, double A2, double A0) {
        this.A1 = A1;
        this.A2 = A2;
        this.A0 = A0;
    }

    //-------------------------------GETTERS--------------------------------

    public double getA1() {
        return A1;
    }

    public double getA2() {
        return A2;
    }

    public double getA0() {
        return A0;
    }

    public double getSpeed() {
        return speed;
    }

    public double getTension() {
        return tension;
    }

    public double getCharacteristicSpeed() {
        return characteristicSpeed;
    }

    //-------------------------------ФУНКЦИИ--------------------------------

    public void computeTension() {
        tension = WaveFrontComputations.computeTension(A1);
    }

    public double getNewTension() {
        computeTension();
        return getTension();
    }

    public void computeCharSpeed() {
        characteristicSpeed = WaveFrontComputations.computeCharSpeed(A1);
    }

    public double getNewCharSpeed() {
        computeCharSpeed();
        return getCharacteristicSpeed();
    }

    //----------------------------ИНИЦИАЛИЗАТОР-----------------------------

    public WaveFront(double A1, double A2, double A0, WaveFront nextWaveFront) {
        this.A1 = A1;
        this.A2 = A2;
        this.A0 = A0;
    }
}
