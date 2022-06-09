package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.simulation.simulation_variables.wave_front.LayerDescription;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public interface ICollisionHandler {

    WaveFront calculateWaveFront(ArrayList<LayerDescription> layerDescriptions);
}
