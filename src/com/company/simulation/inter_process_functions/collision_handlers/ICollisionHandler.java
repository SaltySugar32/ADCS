package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.simulation.simulation_variables.wave_front.CollidedPairDescription;
import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public interface ICollisionHandler {

    ArrayList<LayerDescription> calculateWaveFronts(CollidedPairDescription collidedPair);
}
