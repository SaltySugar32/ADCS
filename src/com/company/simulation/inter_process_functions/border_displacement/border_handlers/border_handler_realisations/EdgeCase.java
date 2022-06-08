package com.company.simulation.inter_process_functions.border_displacement.border_handlers.border_handler_realisations;

import com.company.simulation.inter_process_functions.border_displacement.border_handlers.IBorderHandler;
import com.company.simulation.simulation_variables.wave_front.WaveFront;

import java.util.ArrayList;

public class EdgeCase implements IBorderHandler {
    @Override
    public WaveFront generateNewWaveFront(ArrayList<WaveFront> prevWaveFronts, double speed) {

        double A1L = prevWaveFronts.get(0).getA1();
        double startTL = prevWaveFronts.get(0).getStartTime();

        //Частный случай - справа 0
        double A2i = (- A1L) / (speed);
        double A1i = (A1L + 0.0);
        double A0i = 0.0;

        WaveFront newWaveFront = new WaveFront(A0i, A1i, A2i, startTL);

        newWaveFront.setSpeed(speed);

        return newWaveFront;
    }
}
