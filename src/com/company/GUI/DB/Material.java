package com.company.GUI.DB;

/**
 * Класс материала
 */
public class Material {
    // Поля класса
    public double lameMu;
    public double lameLambda;
    public double materialDensity;
    public double coefficientNu;
    public String name;

    // Конструктор
    public Material(String name, double p1, double p2, double p3, double p4) {
        this.name = name;
        this.lameMu = p1;
        this.lameLambda = p2;
        this.materialDensity = p3;
        this.coefficientNu = p4;
    }

    // Выводим информацию по материалу
    @Override
    public String toString() {
        return String.format("ID: %s | Mu: %s | Lambda: %s | Density: %s | Nu: %s",
                this.name, this.lameMu, this.lameLambda, this.materialDensity, this.coefficientNu);
    }
}
