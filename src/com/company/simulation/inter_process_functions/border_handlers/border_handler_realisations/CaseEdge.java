package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.ProgramGlobals;
import com.company.simulation.inter_process_functions.layer_generators.Signoton;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

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
            newLayer = Signoton.generateFastPositive(prevLayerDescriptions, WaveType.HALF_SIGNOTON);
            System.out.println("FAST");
        } else {
            newLayer = Signoton.generateSlowPositive(prevLayerDescriptions, WaveType.HALF_SIGNOTON);
        }

        var newLayers = new ArrayList<LayerDescription>();
        newLayers.add(newLayer);

        return newLayers;
    }
}
