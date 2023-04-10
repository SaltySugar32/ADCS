package com.company.server.functions.border_handlers.border_handler_realisations;

import com.company.server.types.LayerDescription;

import java.util.ArrayList;

public class CaseNull implements IBorderHandler {

    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed) {
        return null;
    }
}
