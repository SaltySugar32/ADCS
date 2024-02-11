package com.company.server.simulation.border.cases;

import com.company.ProgramGlobals;
import com.company.server.simulation.enums.WaveType;
import com.company.server.simulation.collision.functions.SimpleFracture;
import com.company.server.runtime.vars.SimGlobals;
import com.company.server.simulation.types.LayerDescription;

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
