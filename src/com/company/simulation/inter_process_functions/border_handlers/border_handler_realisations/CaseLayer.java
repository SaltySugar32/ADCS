package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.simulation.inter_process_functions.layer_generators.SimpleFracture;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;

public class CaseLayer implements IBorderHandler {

    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double unused) {
        var newLayers = new ArrayList<LayerDescription>();

        //------------------------------СОЗДАЁМ БЫСТРЫЙ ВОЛНОВОЙ ФРОНТ - ФРОНТ СЖАТИЯ-----------------------------------

        double startTL = prevLayerDescriptions.get(0).getLayerStartTime();
        double speedR = SimulationGlobals.getCharacteristicsSpeedCompression();
        double A0i = prevLayerDescriptions.get(0).getA0();

        LayerDescription secondLayerDescription
                = new LayerDescription(A0i, 0.0, 0.0, startTL, WaveType.HALF_SIGNOTON);
        secondLayerDescription.setSpeed(speedR);

        //-----------------------------СОЗДАЁМ МЕДЛЕННЫЙ ВОЛНОВОЙ ФРОНТ - ФРОНТ РАСТЯЖЕНИЯ------------------------------

        var layerWrapper = new ArrayList<LayerDescription>();
        layerWrapper.add(prevLayerDescriptions.get(0));
        layerWrapper.add(secondLayerDescription);

        LayerDescription firstLayerDescription
                = SimpleFracture.generateSlowPositive(layerWrapper, 0.0, prevLayerDescriptions.get(0).getLayerStartTime(), WaveType.HALF_SIGNOTON);

        //----------------------------------------------------ФИНАЛ-----------------------------------------------------

        newLayers.add(firstLayerDescription);
        newLayers.add(secondLayerDescription);

        return newLayers;
    }
}
