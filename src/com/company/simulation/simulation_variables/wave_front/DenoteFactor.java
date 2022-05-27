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
    NULL {
        @Override
        public double toMillimeters(double value) {
            return value * Math.pow(10, 3);
        }
    },

    MILLI {
        @Override
        public double toMillimeters(double value) {
            return value;
        }
    },

    MICRO {
        @Override
        public double toMillimeters(double value) {
            return value * Math.pow(10, -3);
        }
    },

    NANO {
        @Override
        public double toMillimeters(double value) {
            return value * Math.pow(10, -6);
        }
    };

    public abstract double toMillimeters(double value);
}
