package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.ProgramGlobals;
import com.company.simulation.inter_process_functions.layer_generators.SimpleFracture;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;

public class CaseEquals implements IBorderHandler {
    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed) {

        var newLayers = new ArrayList<LayerDescription>();

        LayerDescription newLayer;

        if (Math.abs(Math.abs(speed) - Math.abs(SimulationGlobals.getCharacteristicsSpeedCompression()))
                < ProgramGlobals.getEpsilon()) {
            newLayer = SimpleFracture.generateFastPositive(prevLayerDescriptions, 0.0, prevLayerDescriptions.get(0).getStartTime(), WaveType.SIGNOTON);
        } else {
            newLayer = SimpleFracture.generateSlowPositive(prevLayerDescriptions, 0.0, prevLayerDescriptions.get(0).getStartTime(), WaveType.SIGNOTON);
        }

        newLayers.add(newLayer);

        return newLayers;
    }
}
