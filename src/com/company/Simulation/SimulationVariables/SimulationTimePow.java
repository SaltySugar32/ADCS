package com.company.Simulation.SimulationVariables;

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
            return 0;
        }
        
    },
    MILLISECONDS {
        @Override
        public double getPow() {
            return -3;
        }
    },
    MICROSECONDS {
        @Override
        public double getPow() {
            return -6;
        }
    },
    NANOSECONDS {
        @Override
        public double getPow() {
            return -9;
        }
    };
    public abstract double getPow();
}
