package com.company.simulation.inter_process_functions.collision_handlers.collision_handlers_realisations;

import com.company.simulation.inter_process_functions.layer_generators.shock_wave.ShockWavePositive;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.wave_front.CollidedPairDescription;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveType;

import java.util.ArrayList;

public class CaseFirst implements ICollisionHandler {
    @Override
    public String shortDescription() {
        return "Sigma+ in xiB+ -> xiA- in Sigma+";
    }

    @Override
    public String longDescription() {
        return "Sigma+ in xiB+ -> xiA- in Sigma+";
    }

    @Override
    public boolean isCorrectCase(CollidedPairDescription collidedPair) {
        return true;
    }

    @Override
    public ArrayList<LayerDescription> calculateWaveFronts(CollidedPairDescription collidedPair) {
        var newLayers = new ArrayList<LayerDescription>();

        var layerWrapper = new ArrayList<LayerDescription>();
        //Добавляем основные две характеристики пространств слева и справа
        layerWrapper.add(collidedPair.getFirstLayer());
        layerWrapper.add(collidedPair.getThirdLayer());
        //Добавляем характеристику для костыля
        layerWrapper.add(collidedPair.getSecondLayer());

        var rightLayer = ShockWavePositive.generatePositiveShockWave(layerWrapper, collidedPair.getCollisionX(), collidedPair.getCollisionTime(), WaveType.SHOCK_WAVE);

        newLayers.add(collidedPair.getFirstLayer());
        newLayers.get(0).setSpeed(0.0 - SimulationGlobals.getCharacteristicsSpeedCompression());
        newLayers.get(0).setStartTime(collidedPair.getCollisionTime());

        newLayers.add(rightLayer);

        return newLayers;
    }
}
