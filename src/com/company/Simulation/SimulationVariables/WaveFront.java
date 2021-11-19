package com.company.Simulation.SimulationVariables;

//Описание волнового фронта
//Пока далеко от истины
public class WaveFront {

    double A1;
    double A2;
    double A0;
    WaveFront nextWaveFront;

    {
        nextWaveFront = null;
    }

    public WaveFront(double A1, double A2, double A0, WaveFront nextWaveFront) {
        this.A1 = A1;
        this.A2 = A2;
        this.A0 = A0;
        this.nextWaveFront = nextWaveFront;
    }

    public WaveFront(double A1) {

    }


    public void setA1(double a1) {
        A1 = a1;
    }

    public void setA2(double a2) {
        A2 = a2;
    }

    public void setA0(double a0) {
        A0 = a0;
    }

    public double getA1() {
        return A1;
    }

    public double getA2() {
        return A2;
    }

    public double getA0() {
        return A0;
    }
}
