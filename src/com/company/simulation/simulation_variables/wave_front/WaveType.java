package com.company.simulation.simulation_variables.wave_front;

import java.awt.*;

/**
 * Тип волнового фронта с цветом, ему соответствующим.
 */
public enum WaveType {
    NULL {
        final Color color = new Color(0, 0, 0);

        @Override
        public Color getColor() {
            return color;
        }
    },
    RED {
        final Color color = new Color(240, 75, 75);

        @Override
        public Color getColor() {
            return color;
        }
    },
    GREEN {
        final Color color = new Color(75, 240, 75);

        @Override
        public Color getColor() {
            return color;
        }
    },
    BLUE {
        final Color color = new Color(75, 75, 240);

        @Override
        public Color getColor() {
            return color;
        }
    };

    public abstract Color getColor();
}
