package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.simulation.simulation_variables.wave_front.CollidedPairDescription;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public interface ICollisionHandler {

    String shortDescription();

    String longDescription();

    boolean isCorrectCase(CollidedPairDescription collidedPair);

    ArrayList<LayerDescription> calculateWaveFronts(CollidedPairDescription collidedPair);
}
