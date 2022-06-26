package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.ProgramGlobals;
import com.company.simulation.inter_process_functions.layer_generators.SimpleFracture;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_types.layer_description.LayerDescription;
import com.company.simulation.simulation_types.enums.WaveType;

import java.util.ArrayList;

public class CaseEdge implements IBorderHandler {
    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed) {

        //Справа - пусто
        LayerDescription secondLayer = new LayerDescription(0.0, 0.0, 0.0, 0.0, WaveType.NULL);
        prevLayerDescriptions.add(secondLayer);

        LayerDescription newLayer;

        if (Math.abs(Math.abs(speed) - Math.abs(SimulationGlobals.getCharacteristicsSpeedCompression()))
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
