package com.company.simulation.simulation_variables.simulation_time;

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

    //-----------------------МНОЖИТЕЛЬ ИЗМЕНЕНИЯ ВРЕМЕНИ-----------------------

    /**
     * Эквивалентно 10 ^ SimulationTimePow.getPow()
     */
    static SimulationTimePow simulationTimePow;

    static {
        setSimulationTimePow(SimulationTimePow.MILLISECONDS);
    }

    /**
     * Число, выражающее множитель степени секунды.
     * @return Число в рамках (от 1 до 10^-9)
     */
    public static SimulationTimePow getSimulationTimePow() {
        return simulationTimePow;
    }

    /**
     * Ввод множителя шага симуляции, выражаемого в степени секунды.
     */
    public static void setSimulationTimePow(SimulationTimePow simulationTimePow) {
        SimulationTime.simulationTimePow = simulationTimePow;
    }

    //-------------------------------------------------------------------------

    //------------------------ИЗМЕНЕНИЕ ВРЕМЕНИ ЗА ЦИКЛ------------------------

    /**
     * Число в рамках от (0.1 до 1) * simulationTimePow
     */
    static double simulationTimeDelta;

    static {
        simulationTimeDelta = 0.5 * simulationTimePow.getPow();
    }

    /**
     * Число, выражающее длительность операции.
     *
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
        SimulationTime.simulationTimeDelta = simulationTimeDelta * simulationTimePow.getPow();
    }

    //-------------------------------------------------------------------------

    //-----------------ИЗМЕНЕНИЕ ВРЕМЕНИ С ПОВЫШЕННОЙ ТОЧНОСТЬЮ----------------

    /**
     * Эквивалентно 10 ^ SimulationTimeHiPrecisionPow.getPow()
     */
    static double simulationTimeHiPrecisionDelta;

    static {
        setSimulationTimeHiPrecisionDelta(SimulationTimePow.MICROSECONDS);
    }

    /**
     * Число, выражающее множитель степени секунды.
     * @return Число в рамках (от 1 до 10^-9)
     */
    public static double getSimulationTimeHiPrecisionDelta() {
        return simulationTimeHiPrecisionDelta;
    }

    /**
     * Ввод множителя шага повышенной точности для симуляции, выражаемого в степени секунды.
     */
    public static void setSimulationTimeHiPrecisionDelta(SimulationTimePow simulationTimeHiPrecisionDelta) {
        SimulationTime.simulationTimeHiPrecisionDelta = Math.pow(10, simulationTimeHiPrecisionDelta.getPow());
    }

    //-------------------------------------------------------------------------

}
