package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.simulation.inter_process_functions.collision_handlers.collision_handler_realisations.CompressionInBorder;
import com.company.simulation.inter_process_functions.collision_handlers.collision_handler_realisations.NullCollisionHandler;
import com.company.simulation.inter_process_functions.collision_handlers.collision_handler_realisations.StretchingInBorder;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public enum CollisionHandler {
    NULL {
        final ICollisionHandler collisionHandler = new NullCollisionHandler();
        @Override
        public ArrayList<WaveFront> generateNewWaveFronts(ArrayList<WaveFront> waveFronts) {
            return collisionHandler.generateNewWaveFronts(waveFronts);
        }
    },
    CompressionInBorder {
        final ICollisionHandler collisionHandler = new CompressionInBorder();
        @Override
        public ArrayList<WaveFront> generateNewWaveFronts(ArrayList<WaveFront> waveFronts) {
            return collisionHandler.generateNewWaveFronts(waveFronts);
        }
    },
    StretchingInBorder {
        final ICollisionHandler collisionHandler = new StretchingInBorder();
        @Override
        public ArrayList<WaveFront> generateNewWaveFronts(ArrayList<WaveFront> waveFronts) {
            return collisionHandler.generateNewWaveFronts(waveFronts);
        }
    };

    public abstract ArrayList<WaveFront> generateNewWaveFronts(ArrayList<WaveFront> waveFronts);
}
