package com.company.simulation.inter_process_functions.border_displacement.border_handlers;

import com.company.simulation.inter_process_functions.border_displacement.border_handlers.border_handler_realisations.CompressionToEdge;
import com.company.simulation.inter_process_functions.border_displacement.border_handlers.border_handler_realisations.NullToEdge;
import com.company.simulation.inter_process_functions.border_displacement.border_handlers.border_handler_realisations.StretchingToEdge;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public enum BorderHandler {
    NULL {
        final IBorderHandler borderHandler = new NullToEdge();
        @Override
        public ArrayList<WaveFront> generateNewWaveFront(ArrayList<WaveFront> waveFronts) {
            return borderHandler.generateNewWaveFronts(waveFronts);
        }
    },
    CompressionInBorder {
        final IBorderHandler borderHandler = new CompressionToEdge();
        @Override
        public ArrayList<WaveFront> generateNewWaveFront(ArrayList<WaveFront> waveFronts) {
            return borderHandler.generateNewWaveFronts(waveFronts);
        }
    },
    StretchingInBorder {
        final IBorderHandler borderHandler = new StretchingToEdge();
        @Override
        public ArrayList<WaveFront> generateNewWaveFront(ArrayList<WaveFront> waveFronts) {
            return borderHandler.generateNewWaveFronts(waveFronts);
        }
    };

    public abstract ArrayList<WaveFront> generateNewWaveFront(ArrayList<WaveFront> waveFronts);
}
