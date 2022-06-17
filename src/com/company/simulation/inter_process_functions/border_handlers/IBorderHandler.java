package com.company.simulation.inter_process_functions.border_handlers;

import com.company.simulation.simulation_variables.wave_front.LayerDescription;

import java.util.ArrayList;

public interface IBorderHandler {
    ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed);
}
