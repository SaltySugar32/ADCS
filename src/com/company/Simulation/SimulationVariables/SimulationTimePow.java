package com.company.Simulation.SimulationVariables;

/**
 * Функция, предоставляющая выбор множителя времени (с, мс, мкс, нс).
 *
 * Рекомендуется использование вместе с SimulationGlobals.setSimulationTimePow().
 *
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
