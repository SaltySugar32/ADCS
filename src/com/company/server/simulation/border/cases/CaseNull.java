package com.company.server.simulation.border.cases;

import com.company.server.simulation.types.LayerDescription;

import java.util.ArrayList;

public class CaseNull implements IBorderHandler {

    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed) {
        return null;
    }
}
