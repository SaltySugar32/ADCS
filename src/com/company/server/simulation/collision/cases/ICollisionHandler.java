package com.company.server.simulation.collision.cases;

import com.company.server.simulation.collision.CollidedPairDescription;
import com.company.server.simulation.types.LayerDescription;

import java.util.ArrayList;

public interface ICollisionHandler {

    String shortDescription();

    String longDescription();

    boolean isCorrectCase(CollidedPairDescription collidedPair);

    ArrayList<LayerDescription> calculateWaveFronts(CollidedPairDescription collidedPair);
}
