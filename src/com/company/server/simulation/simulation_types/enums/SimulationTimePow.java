package com.company.server.simulation.simulation_types.enums;

/**
 * Перечисление, предоставляющее выбор множителя времени (с, мс, мкс, нс).
 * <br>
 * Рекомендуется использование вместе с SimulationTime.setSimulationTimePow().
 * <br>
 * Функция getPow() возвращает степень множителя времени.
 */
public enum SimulationTimePow {
    SECONDS {
        @Override
        public double getPow() {
            return 1;
        }

        @Override
        public String getName() {
            return "с";
        }

        @Override
        public String getCiName() {
            return "с";
        }
    },
    MILLISECONDS {
        @Override
        public double getPow() {
            return Math.pow(10, -3);
        }

        @Override
        public String getName() {
            return "мс";
        }

        @Override
        public String getCiName() {
            return "10^(-3)с";
        }
    },
    MICROSECONDS {
        @Override
        public double getPow() {
            return Math.pow(10, -6);
        }

        @Override
        public String getName() {
            return "мкс";
        }

        @Override
        public String getCiName() {
            return "10^(-6)с";
        }
    },
    NANOSECONDS {
        @Override
        public double getPow() {
            return Math.pow(10, -9);
        }

        @Override
        public String getName() {
            return "нс";
        }

        @Override
        public String getCiName() {
            return "10^(-9)с";
        }
    };
    public abstract double getPow();

    public abstract String getName();

    public abstract String getCiName();
}
