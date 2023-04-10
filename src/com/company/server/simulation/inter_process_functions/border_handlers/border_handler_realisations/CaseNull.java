package com.company.server.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.server.simulation.simulation_types.layer_description.LayerDescription;

import java.util.ArrayList;

public class CaseNull implements IBorderHandler {

    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed) {
        return null;
    }
}
