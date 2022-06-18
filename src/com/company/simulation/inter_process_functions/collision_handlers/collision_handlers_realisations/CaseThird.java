package com.company.simulation.inter_process_functions.collision_handlers.collision_handlers_realisations;

import com.company.simulation.simulation_variables.wave_front.CollidedPairDescription;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public class CaseThird implements ICollisionHandler {
    @Override
    public String shortDescription() {
        return null;
    }

    @Override
    public String longDescription() {
        return null;
    }

    @Override
    public boolean isCorrectCase(CollidedPairDescription collidedPair) {
        return false;
    }

    @Override
    public ArrayList<LayerDescription> calculateWaveFronts(CollidedPairDescription collidedPair) {
        return new ArrayList<>();
    }
}
