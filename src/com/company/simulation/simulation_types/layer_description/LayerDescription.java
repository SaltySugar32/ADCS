package com.company.simulation.simulation_types.layer_description;

import com.company.simulation.simulation_types.enums.WaveType;
import com.company.simulation.simulation_variables.SimulationTime;

/**
 * Описание линейной аппроксимации процесса, происходящего слева от волнового фронта
 * <br>
 * Здесь не должно быть ничего, кроме базовых get/set методов
 * <br>
 * Любые поступающие сюда значения преобразуются в миллиметры (?)
 */
public class LayerDescription {

    //--------------------------------------------ПАРАМЕТРЫ ВОЛНОВОГО ФРОНТА--------------------------------------------

    /**
     * Текущая координата волнового фронта
     */
    double currentX;

    /**
     * Скорость волнового фронта
     */
    double speed;

    /**
     * Тип волнового фронта
     */
    WaveType waveType;

    /**
     * Время появления волнового фронта
     */
    double waveFrontStartTime;

    //-------------------------------SETTERS--------------------------------

    /**
     * Изменение координаты волнового фронта
     * @param currentX double Новая координата волнового фронта
     */
    public void setCurrentX(double currentX) {
        this.currentX = currentX;
    }

    /**
     * Изменение скорости волнового фронта
     * @param speed Новая скорость волнового фронта
     */
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * Функция, смещающая волновой фронт на расстояние, преодолеваемое им за timeDelta
     * @param timeDelta Изменение времени
     */
    public void moveWaveFront(double timeDelta) {
        //Устанавливаем смещение относительно текущей координаты +
        // скорость волнового фронта, умноженную на рассматриваемую дельту времени
        currentX += speed * timeDelta;
    }

    /**
     * Функция, задающая время появления волнового фронта
     * @param waveFrontStartTime время появления волнового фронта
     */
    public void setWaveFrontStartTime(double waveFrontStartTime) {
        this.waveFrontStartTime = waveFrontStartTime;
    }

    /**
     * Функция, устанавливающая цветовое описание волнового фронта
     * @param waveType цветовое описание волнового фронта
     */
    public void setWaveType(WaveType waveType) {
        this.waveType = waveType;
    }

    //-------------------------------GETTERS--------------------------------

    /**
     * Функция, возвращающая текущую координату волнового фронта
     * @return double
     */
    public double getCurrentX() {
        return currentX;
    }

    /**
     * Функция, возвращающая текущую скорость волнового фронта
     * @return double скорость волнового фронта
     */
    public double getSpeed() {
        return speed;
    }

    /**
     * Функция, возвращающая время появления волнового фронта
     * @return double время появления волнового фронта
     */
    public double getWaveFrontStartTime() {
        return waveFrontStartTime;
    }

    public WaveType getWaveType() {
        return waveType;
    }

    //---------------------------------------------ПАРАМЕТРЫ СЛОЯ ДЕФОРМАЦИИ--------------------------------------------


    /**
     * Время появления слоя деформации
     */
    double layerStartTime;

    /**
     * Зависимость смещения на границе волнового фронта от координаты x
     * <br>
     * Также можно описать как du/dt
     */
    double A1;

    /**
     * Зависимость смещения от времени
     * <br>
     * - Тензор малых деформаций e
     * <br>
     * - Также можно описать как du/dx
     */
    double A2;

    /**
     * Смещение по координате / задержка во времени
     */
    double A0;


    //-------------------------------GETTERS--------------------------------

    /**
     * Зависимость перемещения на границе волнового фронта от координаты x
     * <br>
     * - Также можно описать как du/dt
     * @return double A1
     */
    public double getA1() {
        return A1;
    }

    /**
     * Зависимость перемещения от времени
     * <br>
     * - Также можно описать как du/dx
     * <br>
     * - Тензор малых деформаций e
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
     * Функция, возвращающая время появления слоя деформации
     * @return double время появления слоя деформации
     */
    public double getLayerStartTime() {
        return layerStartTime;
    }

    //-------------------------------ФУНКЦИИ--------------------------------

    /**
     * Функция, вычисляющая смещение на границе волнового фронта
     * <br>
     * U(x,t) = A1 * t + A2 * x + A0
     * @return double
     */
    public double calculateLayerDisplacement() {
        return (getA0() + getA1() * (SimulationTime.getSimulationTime() - layerStartTime) + getA2() * currentX);
    }

    /**
     * Функция, вычисляющая смещение на границе волнового фронта
     * <br>
     * U(x,t) = A1 * t + A2 * x + A0
     * @return double
     */
    public double calculateZeroDisplacement() {
        return (getA0() + getA1() * (SimulationTime.getSimulationTime() - layerStartTime));
    }

    /**
     * Функция, подсчитывающая скорость изменения перемещения в слое
     * @return double Значение изменения перемещения в слое
     */
    public double calculateDeformations() {
        double left = getA0() +
                getA1() * (SimulationTime.getSimulationTime() - layerStartTime) +
                getA2() * currentX;
        double right = getA0() +
                getA1() * (SimulationTime.getSimulationTime() - layerStartTime) +
                getA2() * (currentX + 10);

        return (right - left) / 10;
    }

    /**
     * Функция, задающая A2 - деформацию слоя
     * @param A2 Деформация слоя
     */
    public void setA2(double A2) {
        this.A2 = A2;
    }

    /**
     * Изменение времени появления волнового фронта
     * @param layerStartTime Новое время появления волнового фронта
     */
    public void setLayerStartTime(double layerStartTime) {
        this.layerStartTime = layerStartTime;
    }

    //----------------------------ИНИЦИАЛИЗАТОР-----------------------------

    /**
     * Инициализация нового уравнение смещения среды для волнового фронта, задаваемого уравнением:
     * <br>U(x,t) = A1 * t + A2 * x + A0,<br>
     * в котором
     * @param A0 смещение по координате / задержка по времени
     * @param A1 зависимость смещения на границе волнового фронта от времени t - du/dt
     * @param A2 зависимость смещения на границе волнового фронта от координаты x - du/dx
     */
    public LayerDescription(double A0, double A1, double A2, double layerStartTime, WaveType waveType) {
        this.A1 = A1;
        this.A2 = A2;
        this.A0 = A0;
        this.layerStartTime = layerStartTime;
        this.waveFrontStartTime = layerStartTime;
        this.waveType = waveType;

        this.speed = 0.0;
        this.currentX = 0.0;
    }
}
