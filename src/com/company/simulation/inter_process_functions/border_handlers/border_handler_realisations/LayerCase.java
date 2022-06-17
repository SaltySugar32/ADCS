package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.simulation.inter_process_functions.border_handlers.IBorderHandler;
import com.company.simulation.simulation_variables.DenoteFactor;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;

public class LayerCase implements IBorderHandler {

    @Override
    public ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double unused) {
        var newLayers = new ArrayList<LayerDescription>();

        double speedR;
        double A2i;
        double A1i;
        double A0i;

        double startTL = prevLayerDescriptions.get(0).getStartTime();
        double startTR = prevLayerDescriptions.get(1).getStartTime();

        //------------------------------СОЗДАЁМ БЫСТРЫЙ ВОЛНОВОЙ ФРОНТ - ФРОНТ СЖАТИЯ-----------------------------------

        speedR = DenoteFactor.METERS.toMillis(SimulationGlobals.getCharacteristicsSpeedCompression());
        A2i = 0.0;
        A1i = 0.0;
        A0i = prevLayerDescriptions.get(0).getA0();

        LayerDescription secondLayerDescription = new LayerDescription(A0i, A1i, A2i, startTL, WaveType.HALF_SIGNOTON);
        secondLayerDescription.setSpeed(speedR);

        //-----------------------------СОЗДАЁМ МЕДЛЕННЫЙ ВОЛНОВОЙ ФРОНТ - ФРОНТ РАСТЯЖЕНИЯ------------------------------

        speedR = DenoteFactor.METERS.toMillis(SimulationGlobals.getCharacteristicsSpeedStretching());

        double A0L = prevLayerDescriptions.get(0).getA0();
        double A1L = prevLayerDescriptions.get(0).getA1();
        double A2L = 0.0 - prevLayerDescriptions.get(0).getA1() / speedR;

        double A0R = secondLayerDescription.getA0();
        double A1R = secondLayerDescription.getA1();
        double A2R = secondLayerDescription.getA2();

        //Частный случай при CL = 0, xL = 0, Xi = 0
        A2i = (A1R + A2R * speedR - A1L) / (speedR);
        A1i = (A1L + 0.0);
        A0i = A0R + A1R * (startTL - startTR);

        LayerDescription firstLayerDescription = new LayerDescription(A0i, A1i, A2i, startTL, WaveType.HALF_SIGNOTON);

        firstLayerDescription.setSpeed(speedR);

        //----------------------------------------------------ФИНАЛ-----------------------------------------------------

        newLayers.add(firstLayerDescription);
        newLayers.add(secondLayerDescription);

        return newLayers;
    }
}
