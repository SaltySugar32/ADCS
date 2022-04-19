package com.company.Simulation.SimulationVariables;

import com.company.Simulation.SimulationFunctions.WaveFrontComputations;

/**Описание волнового фронта
 * Пока далеко от истины
 * Здесь не должно быть ничего, кроме базовых get/set методов
 */
public class WaveFront { //U(x,t) = A1 * x + A2 * t + A0

    //---------------------------ПАРАМЕТРЫ ВОЛНЫ-----------------------------

    /**
     * Зависимость смещения на границе волнового фронта от координаты x
     * <br>
     * - Тензор малых деформаций e
     * <br>
     * - Также можно описать как du/dx
     */
    double A1;

    /**
     * Зависимость смещения от времени
     * <br>
     * Также можно описать как du/dt
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

    {
        tension = 0;
        characteristicSpeed = 0;
    }

    double currentX;

    //-------------------------------SETTERS--------------------------------

    /**
     * Изменение характеристик смещения на границе волнового фронта, задаваемое уравнением:
     * <br>U(x,t) = A1 * x + A2 * t + A0,<br>
     * в котором
     * @param A1 зависимость смещения на границе волнового фронта от координаты x - du/dx
     * @param A2 зависимость смещения на границе волнового фронта от времени t - du/dt
     * @param A0 смещение по координате / задержка по времени
     */
    public void setWaveFront(double A1, double A2, double A0) {
        this.A1 = A1;
        this.A2 = A2;
        this.A0 = A0;
        //TODO: убедиться, что A0 в момент столкновения являет собой координату старта волнового фронта
        this.currentX = A0;
    }

    public void setCurrentX(double currentX) {
        this.currentX = currentX;
    }

    //-------------------------------GETTERS--------------------------------

    /**
     * Зависимость смещения на границе волнового фронта от координаты x
     * <br>
     * - Тензор малых деформаций e
     * <br>
     * - Также можно описать как du/dx
     */
    public double getA1() {
        return A1;
    }

    /**
     * Зависимость смещения от времени
     * <br>
     * - Также можно описать как du/dt
     */
    public double getA2() {
        return A2;
    }

    public double getA0() {
        return A0;
    }

    public double getTension() {
        return tension;
    }

    public double getCharacteristicSpeed() {
        return characteristicSpeed;
    }

    public double getCurrentX() {
        return currentX;
    }

    //-------------------------------ФУНКЦИИ--------------------------------

    public void setTension(double tension) {
        this.tension = tension;
    }

    public void setCharacteristicSpeed(double characteristicSpeed) {
        this.characteristicSpeed = characteristicSpeed;
    }

    //----------------------------ИНИЦИАЛИЗАТОР-----------------------------

    /**
     * Инициализация нового уравнение смещения среды для волнового фронта, задаваемого уравнением:
     * <br>U(x,t) = A1 * x + A2 * t + A0,<br>
     * в котором
     * @param A1 зависимость смещения на границе волнового фронта от координаты x - du/dx
     * @param A2 зависимость смещения на границе волнового фронта от времени t - du/dt
     * @param A0 смещение по координате / задержка по времени
     */
    public WaveFront(double A1, double A2, double A0) {
        this.A1 = A1;
        this.A2 = A2;
        this.A0 = A0;
    }
}
