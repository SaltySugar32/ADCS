package com.company.server.functions.collision_handlers.collision_handlers_realization;

import com.company.server.types.CollidedPairDescription;
import com.company.server.types.LayerDescription;

import java.util.ArrayList;

public interface ICollisionHandler {

    String shortDescription();

    String longDescription();

    boolean isCorrectCase(CollidedPairDescription collidedPair);

    ArrayList<LayerDescription> calculateWaveFronts(CollidedPairDescription collidedPair);
}
