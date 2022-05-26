package com.company.simulation.simulation_variables.wave_front;

import com.company.simulation.simulation_variables.simulation_time.SimulationTime;

/**
 * Описание волнового фронта
 * <br>
 * Здесь не должно быть ничего, кроме базовых get/set методов
 * <br>
 * Любые поступающие сюда значения преобразуются в миллиметры
 */
public class WaveFront {

    //---------------------------ПАРАМЕТРЫ ВОЛНЫ-----------------------------

    /**
     * Зависимость смещения на границе волнового фронта от координаты x
     * <br>
     * - Тензор малых деформаций e
     * <br>
     * - Также можно описать как du/dx
     */
    private double A1;

    /**
     * Зависимость смещения от времени
     * <br>
     * Также можно описать как du/dt
     */
    private double A2;

    /**
     * Смещение по координате / задержка во времени
     */
    private double A0;

    /**
     * Текущая координата волнового фронта
     */
    private double currentX;

    //-------------------------------SETTERS--------------------------------

    /**
     * Изменение характеристик смещения на границе волнового фронта, задаваемое уравнением:
     * <br>U(x,t) = A1 * x + A2 * t + A0,<br>
     * в котором
     * @param A1 зависимость смещения на границе волнового фронта от координаты x - du/dx
     * @param A2 зависимость смещения на границе волнового фронта от времени t - du/dt
     * @param A0 смещение по координате / задержка по времени
     */
    public void setWaveFront(double A1, double A2, double A0 , DenoteFactor denoteFactor) {
        this.A1 = denoteFactor.toMilliseconds(A1);
        this.A2 = denoteFactor.toMilliseconds(A2);
        this.A0 = denoteFactor.toMilliseconds(A0);

        this.currentX = A0;
    }

    /**
     * Изменение координаты волнового фронта
     * @param currentX double Новая координата волнового фронта
     */
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
     * @return double A1
     */
    public double getA1() {
        return A1;
    }

    /**
     * Зависимость смещения от времени
     * <br>
     * - Также можно описать как du/dt
     * @return double A2
     */
    public double getA2() {
        return A2;
    }

    /**
     * Начальная координата волнового фронта
     * @return double A0
     */
    public double getA0() {
        return A0;
    }

    /**
     * Функция, возвращающая текущую координату волнового фронта
     * @return double
     */
    public double getCurrentX() {
        return currentX;
    }

    //-------------------------------ФУНКЦИИ--------------------------------

    /**
     * Функция, вычисляющая смещение на границе волнового фронта
     * <br>
     * U(x,t) = A1 * x + A2 * t + A0
     * @return double
     */
    public double calculateDisplacement() {
        return (getA0() + getA1() * SimulationTime.getSimulationTime() + getA2() * currentX);
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
    public WaveFront(double A1, double A2, double A0, DenoteFactor denoteFactor) {
        this.A1 = denoteFactor.toMilliseconds(A1);
        this.A2 = denoteFactor.toMilliseconds(A2);
        this.A0 = denoteFactor.toMilliseconds(A0);

        this.currentX = A0;
    }
}
