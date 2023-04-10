package com.company.client.gui.Database;

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
    public Material(String name, double lameMu, double lameLambda, double materialDensity, double coefficientNu) {
        this.name = name;
        this.lameMu = lameMu;
        this.lameLambda = lameLambda;
        this.materialDensity = materialDensity;
        this.coefficientNu = coefficientNu;
    }

    // Выводим информацию по материалу
    @Override
    public String toString() {
        return String.format("ID: %s | Mu: %s | Lambda: %s | Density: %s | Nu: %s",
                this.name, this.lameMu, this.lameLambda, this.materialDensity, this.coefficientNu);
    }
}
