package com.company.simulation.inter_process_functions.border_displacement.border_handlers;

import com.company.ProgramGlobals;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public class BorderSwitcher {

    /**
     * Функция, выбирающая вид создаваемого волнового фронта
     * @param waveFronts Два волновых фронта - новосоздаваемый и следующий
     * @return Тип создаваемого волнового фронта
     */
    public static BorderHandler switchBorderHandler(ArrayList<WaveFront> waveFronts) {

        if (waveFronts.get(0).getA1() > ProgramGlobals.getEpsilon()) {
            //creationE > 0

            //nextWaveE < 0
            if (waveFronts.size() > 1 && waveFronts.get(1).getA1() < -ProgramGlobals.getEpsilon())
                return BorderHandler.CompressionInBorder; //TODO: BorderHandler

            //nextWaveE >= 0
            return BorderHandler.StretchingInBorder;

        } else if (waveFronts.get(0).getA1() < -ProgramGlobals.getEpsilon()) {
            //creationE < 0

            //nextWaveE > 0
            if (waveFronts.size() > 1 && waveFronts.get(1).getA1() > ProgramGlobals.getEpsilon())
                return BorderHandler.StretchingInBorder; //TODO: BorderHandler

            //nextWaveE <= 0
            return BorderHandler.CompressionInBorder;

        }
        return BorderHandler.NULL;
    }
}
