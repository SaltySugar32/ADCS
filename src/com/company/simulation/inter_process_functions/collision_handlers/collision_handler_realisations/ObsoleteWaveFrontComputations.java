package com.company.simulation.inter_process_functions.collision_handlers.collision_handler_realisations;

import com.company.simulation.simulation_variables.SimulationGlobals;

public class ObsoleteWaveFrontComputations {

    /**
     * Формула для вычисления скорости новообразованной ударной волны
     */
    public static double computeNewWaveFrontSpeed(double speedLeft, double speedRight, double displacementPos, double displacementNeg)
    {
        //return sqrt( a^2 - (a^2 - b^2) * (U^+_,x) / (U^+_,x - U^-_,x) )
        return speedLeft * speedLeft - (Math.pow(speedLeft, 2) - Math.pow(speedRight, 2)) * (displacementPos) / (displacementPos - displacementNeg);
    }

    /**
     * U-,x = U+,x - ((a^2 - b^2) * U+,x) / (a^2 - G^2)
     */
    public static double computeWaveDisplacementNeg(double speedLeft, double speedRight, double displacementPos, double newSpeed)
    {
        return displacementPos - ((speedLeft - speedRight) * displacementPos) / (Math.pow(speedLeft, 2) * newSpeed);
    }

    /**
     * U+,x = U-,x * (a^2 - G^2) / (b^2 - G^2)
     */
    public static double computeWaveDisplacementPos(double speedLeft, double speedRight, double displacementNeg, double newSpeed)
    {
        return displacementNeg * (Math.pow(speedLeft, 2) - Math.pow(newSpeed, 2)) / (Math.pow(speedRight, 2) - Math.pow(newSpeed, 2));
    }
}
