package com.company.simulation.inter_process_functions.collision_handlers.collision_handlers_realisations;

import com.company.simulation.simulation_types.layer_description.CollidedPairDescription;
import com.company.simulation.simulation_types.layer_description.LayerDescription;

import java.util.ArrayList;

public interface ICollisionHandler {

    String shortDescription();

    String longDescription();

    boolean isCorrectCase(CollidedPairDescription collidedPair);

    ArrayList<LayerDescription> calculateWaveFronts(CollidedPairDescription collidedPair);
}
