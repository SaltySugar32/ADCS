package com.company.simulation.simulation_variables.wave_front;

/**
 * Для обеспечения однозначной безопасности ввода новых данных в решатель,
 * а именно ввода множителя относительно размерности,
 * создано данное перечисление.
 * Метод toMilliseconds преобразует значение определённой размерности
 * в миллисекунды, так что сударь сверху должен обеспокоиться только тем,
 * что он должен знать, в какой изначальной размерности существуют его значения.
 */
public enum DenoteFactor {
    METERS {
        @Override
        public double toMeters(double value) {
            return value;
        }
        @Override
        public double toMillimeters(double value) {
            return value * Math.pow(10, 3);
        }
        @Override
        public double toMicrometers(double value) {
            return value * Math.pow(10, 6);
        }
        @Override
        public double toNanometers(double value) {
            return value * Math.pow(10, 9);
        }
    },

    MILLI {
        @Override
        public double toMeters(double value) {
            return value * Math.pow(10, -3);
        }
        @Override
        public double toMillimeters(double value) {
            return value;
        }
        @Override
        public double toMicrometers(double value) {
            return value * Math.pow(10, 3);
        }
        @Override
        public double toNanometers(double value) {
            return value * Math.pow(10, 6);
        }
    },

    MICRO {
        @Override
        public double toMeters(double value) {
            return value * Math.pow(10, -6);
        }
        @Override
        public double toMillimeters(double value) {
            return value * Math.pow(10, -3);
        }
        @Override
        public double toMicrometers(double value) {
            return value;
        }
        @Override
        public double toNanometers(double value) {
            return value * Math.pow(10, 3);
        }
    },

    NANO {
        @Override
        public double toMeters(double value) {
            return value * Math.pow(10, -9);
        }
        @Override
        public double toMillimeters(double value) {
            return value * Math.pow(10, -6);
        }
        @Override
        public double toMicrometers(double value) {
            return value * Math.pow(10, -3);
        }
        @Override
        public double toNanometers(double value) {
            return value;
        }
    };

    public abstract double toMeters(double value);
    public abstract double toMillimeters(double value);
    public abstract double toMicrometers(double value);
    public abstract double toNanometers(double value);
}
