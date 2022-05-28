package com.company.simulation.inter_process_functions.collision_handlers;

import com.company.ProgramGlobals;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public class CollisionTypeSwitcher {


    public static CollisionHandler switchCollisionHandler(ArrayList<WaveFront> waveFronts) {
        //f (waveFronts.size() == 1) {
            if (waveFronts.get(0).getA1() > ProgramGlobals.getEpsilon()) {
                return CollisionHandler.StretchingInBorder;
            } else if (waveFronts.get(0).getA1() < -ProgramGlobals.getEpsilon()) {
                return CollisionHandler.CompressionInBorder;
            } else
                return CollisionHandler.NULL;

        //}

        //return CollisionHandler.NULL;
    }
}
