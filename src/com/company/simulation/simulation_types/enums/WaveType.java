package com.company.simulation.simulation_types.enums;

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
    SHOCK_WAVE {
        final Color color = new Color(240, 75, 75);

        @Override
        public Color getColor() {
            return color;
        }
    },
    SIMPLE_FRACTURE {
        final Color color = new Color(75, 240, 75);

        @Override
        public Color getColor() {
            return color;
        }
    },
    HALF_SIGNOTON {
        final Color color = new Color(75, 75, 240);

        @Override
        public Color getColor() {
            return color;
        }
    };

    public abstract Color getColor();
}
