package com.company.server.enums;

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
        public double toNone(double value) {
            return value;
        }
        @Override
        public double toMillis(double value) {
            return value * Math.pow(10, 3);
        }
        @Override
        public double toMicros(double value) {
            return value * Math.pow(10, 6);
        }
        @Override
        public double toNanos(double value) {
            return value * Math.pow(10, 9);
        }
    },

    MILLI {
        @Override
        public double toNone(double value) {
            return value * Math.pow(10, -3);
        }
        @Override
        public double toMillis(double value) {
            return value;
        }
        @Override
        public double toMicros(double value) {
            return value * Math.pow(10, 3);
        }
        @Override
        public double toNanos(double value) {
            return value * Math.pow(10, 6);
        }
    },

    MICRO {
        @Override
        public double toNone(double value) {
            return value * Math.pow(10, -6);
        }
        @Override
        public double toMillis(double value) {
            return value * Math.pow(10, -3);
        }
        @Override
        public double toMicros(double value) {
            return value;
        }
        @Override
        public double toNanos(double value) {
            return value * Math.pow(10, 3);
        }
    },

    NANO {
        @Override
        public double toNone(double value) {
            return value * Math.pow(10, -9);
        }
        @Override
        public double toMillis(double value) {
            return value * Math.pow(10, -6);
        }
        @Override
        public double toMicros(double value) {
            return value * Math.pow(10, -3);
        }
        @Override
        public double toNanos(double value) {
            return value;
        }
    };

    public abstract double toNone(double value);
    public abstract double toMillis(double value);
    public abstract double toMicros(double value);
    public abstract double toNanos(double value);
}
