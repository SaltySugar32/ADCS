package com.company.server.simulation.border.cases;

import com.company.server.simulation.types.LayerDescription;

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
