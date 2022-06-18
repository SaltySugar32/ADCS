package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class CaseNull implements IBorderHandler {

    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed) {
        return null;
    }
}
