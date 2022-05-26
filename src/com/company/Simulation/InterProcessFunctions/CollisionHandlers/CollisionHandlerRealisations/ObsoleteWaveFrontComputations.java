package com.company.Simulation.InterProcessFunctions.CollisionHandlers.CollisionHandlerRealisations;

import com.company.Simulation.SimulationVariables.SimulationGlobals;

public class ObsoleteWaveFrontComputations {

    /**
     * Вычисление напряжения Эйлера-Коши на стыке волн деформации
     */
    public static double computeTension(double lilDeformations)
    {
        if (0 > lilDeformations)
        {
            //return \lambda + 2 * \mu + 2 * \nu
            return SimulationGlobals.getLameLambda() + 2 * SimulationGlobals.getLameMu() + 2 * SimulationGlobals.getCoefficientNu();
        } else if (0 < lilDeformations)
        {
            //return \lambda + 2 * \mu - 2 * \nu
            return SimulationGlobals.getLameLambda() + 2 * SimulationGlobals.getLameMu() - 2 * SimulationGlobals.getCoefficientNu();
        } else return 0;
    }

    /**
     * Вычисление характеристической скорости
     */
    public static double computeCharSpeed(double lilDeformations)
    {
        //return \sigma / \rho === (\lambda + 2 * \mu +- 2 * \nu) / \rho
        return computeTension(lilDeformations) / SimulationGlobals.getMaterialDensity();
    }

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
