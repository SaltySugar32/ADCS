package com.company.server.functions.border_handlers.border_handler_realisations;

import com.company.ProgramGlobals;
import com.company.server.enums.WaveType;
import com.company.server.functions.layer_generators.SimpleFracture;
import com.company.server.vars.SimGlobals;
import com.company.server.types.LayerDescription;

import java.util.ArrayList;

public class CaseEdge implements IBorderHandler {
    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed) {

        //Справа - пусто
        LayerDescription secondLayer = new LayerDescription(0.0, 0.0, 0.0, 0.0, WaveType.NULL);
        prevLayerDescriptions.add(secondLayer);

        LayerDescription newLayer;

        if (Math.abs(Math.abs(speed) - Math.abs(SimGlobals.getCharacteristicsSpeedCompression()))
                < ProgramGlobals.getEpsilon()) {
            newLayer = SimpleFracture.generateFastPositive(prevLayerDescriptions, 0.0, prevLayerDescriptions.get(0).getLayerStartTime(), WaveType.HALF_SIGNOTON);
        } else {
            newLayer = SimpleFracture.generateSlowPositive(prevLayerDescriptions, 0.0, prevLayerDescriptions.get(0).getLayerStartTime(), WaveType.HALF_SIGNOTON);
        }

        var newLayers = new ArrayList<LayerDescription>();
        newLayers.add(newLayer);

        return newLayers;
    }
}
