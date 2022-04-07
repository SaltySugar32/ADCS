package com.company.Simulation.SimulationVariables;

public class SimulationTime {
    //------------------------ТЕКУЩЕЕ ВРЕМЯ СИМУЛЯЦИИ--------------------------

    static double simulationTime;

    static {
        simulationTime = 0.0;
    }

    public static double getSimulationTime() {
        return simulationTime;
    }

    public static void setSimulationTime(double simulationTime) {
        SimulationTime.simulationTime = simulationTime;
    }
    //Все переменные выше - константы материала

    public static void nextSimulationTime() {
        simulationTime += simulationTimeDelta;
    }

    //-------------------------------------------------------------------------

    //---------------------ИЗМЕНЕНИЕ ВРЕМЕНИ ЗА ЦИКЛ-----------------------

    /*
     * Число от 0.1 до 1, выражающее длительность операции.
     */
    static double simulationTimeDelta;

    /*
     * Эквивалентно 10 ^ SimulationTimePow.getPow()
     */
    static double simulationTimePow;

    static {
        setSimulationTimePow(SimulationTimePow.MICROSECONDS);
        simulationTimeDelta = 0.5 * simulationTimePow;
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
        SimulationTime.simulationTimeDelta = simulationTimeDelta * simulationTimePow;
    }

    public static double getSimulationTimePow() {
        return simulationTimePow;
    }

    /**
     * Ввод множителя шага симуляции, выражаемого в степени секунды.
     */
    public static void setSimulationTimePow(SimulationTimePow simulationTimePow) {
        SimulationTime.simulationTimePow = Math.pow(10, simulationTimePow.getPow());
    }

    //-------------------------------------------------------------------------

}
