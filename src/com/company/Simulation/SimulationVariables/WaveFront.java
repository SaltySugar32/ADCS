package com.company.Simulation.SimulationVariables;

import com.company.Simulation.SimulationFunctions.WaveFrontComputations;

//Описание волнового фронта
//Пока далеко от истины
public class WaveFront { //U(x,t) = A1 * x + A2 * t + A0

    //---------------------------ПАРАМЕТРЫ ВОЛНЫ-----------------------------

    //Деформация материала в точке x - тензор малых деформаций e ?? !!!!!!!!!!!!!!!!!!!!!!!
    double A1;
    //Изменение перемещения во времени
    double A2;
    //Смещение по координате / задержка во времени
    double A0;

    //Следующий волновой фронт по правую сторону
    WaveFront nextWaveFront;

    //Тензор напряжений Эйлера-Коши по оси Ox - \sigma в формулах
    double tension;

    //Характеристическая скорость волнового фронта
    double charSpeed;

    {
        nextWaveFront = null;
        tension = 0;
        charSpeed = 0;
    }

    //-------------------------------SETTERS--------------------------------

    public void setWaveFront(double A1, double A2, double A0) {
        this.A1 = A1;
        this.A2 = A2;
        this.A0 = A0;
    }

    public void setNextWaveFront(WaveFront nextWaveFront) {
        this.nextWaveFront = nextWaveFront;
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

    public WaveFront getNextWaveFront() {
        return nextWaveFront;
    }

    public double getTension() {
        return tension;
    }

    public double getCharSpeed() {
        return charSpeed;
    }

    //-------------------------------ФУНКЦИИ--------------------------------

    public void computeTension() {
        tension = WaveFrontComputations.computeTension(A1/*Пусто, а так не должно быть*/);
    }

    public void computeCharSpeed() {
        charSpeed = WaveFrontComputations.computeCharSpeed(A1);
    }

    //----------------------------ИНИЦИАЛИЗАТОР-----------------------------

    public WaveFront(double A1, double A2, double A0, WaveFront nextWaveFront) {
        this.A1 = A1;
        this.A2 = A2;
        this.A0 = A0;
        this.nextWaveFront = nextWaveFront;
    }
}
