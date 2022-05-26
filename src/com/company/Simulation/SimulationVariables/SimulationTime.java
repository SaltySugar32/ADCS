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

    public static void nextSimulationTime() {
        simulationTime += simulationTimeDelta;
    }

    //-------------------------------------------------------------------------

    //------------------------ИЗМЕНЕНИЕ ВРЕМЕНИ ЗА ЦИКЛ------------------------

    /**
     * Число в рамках от (0.1 до 1) * simulationTimePow
     */
    static double simulationTimeDelta;

    /**
     * Эквивалентно 10 ^ SimulationTimePow.getPow()
     */
    static double simulationTimePow;

    static {
        setSimulationTimePow(SimulationTimePow.MICROSECONDS);
        simulationTimeDelta = 0.5 * simulationTimePow;
    }

    /**
     * Число, выражающее длительность операции.
     * @return Число в рамках (от 0.1 до 1) * simulationTimePow
     */
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

    /**
     * Число, выражающее множитель степени секунды.
     * @return Число в рамках (от 1 до 10^-9)
     */
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
