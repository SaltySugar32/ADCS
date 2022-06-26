package com.company.simulation.inter_process_functions.border_handlers.border_handler_realisations;

import com.company.simulation.simulation_types.layer_description.LayerDescription;

import java.util.ArrayList;

public interface IBorderHandler {
    /**
     * Обработчик создания нового волнового фронта на границе полупространства
     * @param prevLayerDescriptions пара "граничное воздействие – слой деформации"
     * @param speed скорость нового волнового фронта
     * @return множество новых волновых фронтов
     */
    ArrayList<LayerDescription> generateNewWaveFront(ArrayList<LayerDescription> prevLayerDescriptions, double speed);
}
