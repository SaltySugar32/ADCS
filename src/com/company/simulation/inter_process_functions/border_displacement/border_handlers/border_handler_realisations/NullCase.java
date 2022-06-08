package com.company.simulation.inter_process_functions.border_displacement.border_handlers.border_handler_realisations;

import com.company.simulation.inter_process_functions.border_displacement.border_handlers.IBorderHandler;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public class NullCase implements IBorderHandler {
    @Override
    public WaveFront generateNewWaveFront(ArrayList<WaveFront> prevWaveFronts, double speed) {
        return null;
    }
}
