package com.company.Simulation;

import com.company.Simulation.SimulationWorkspace.SimulationDisabled;
import com.company.Simulation.SimulationWorkspace.SimulationInterProcess;
import com.company.Simulation.SimulationWorkspace.SimulationPaused;
import com.company.Simulation.SimulationWorkspace.SimulationReady;
import com.company.WaveClasses.WaveFront;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SimulationServer extends Thread {
    private enum SimStatus{
        DISABLED {
            @Override
            public ArrayList<WaveFront> simInstance(ArrayList<WaveFront> currentWavePicture) {
                return SimulationDisabled.getResult(currentWavePicture);
            }

            @Override
            public int getStatus() {
                return 0;
            }
        },
        READY {
            @Override
            public ArrayList<WaveFront> simInstance(ArrayList<WaveFront> currentWavePicture) {
                return SimulationReady.getResult(currentWavePicture);
            }

            @Override
            public int getStatus() {
                return 1;
            }
        },
        INTERPROCESS {
            @Override
            public ArrayList<WaveFront> simInstance(ArrayList<WaveFront> currentWavePicture) {
                return SimulationInterProcess.getResult(currentWavePicture);
            }

            @Override
            public int getStatus() {
                return 2;
            }
        },
        PAUSED {
            @Override
            public ArrayList<WaveFront> simInstance(ArrayList<WaveFront> currentWavePicture) {
                return SimulationPaused.getResult(currentWavePicture);
            }

            @Override
            public int getStatus() {
                return 3;
            }
        };
    
        public abstract ArrayList<WaveFront> simInstance(ArrayList<WaveFront> currentWavePicture);
        public abstract int getStatus();
    }
    SimStatus simStatus;

    ArrayList<WaveFront> currentWavePicture = new ArrayList();

    public SimulationServer() {
        this.simStatus = SimStatus.DISABLED;
    }

    public void setSimStatus(int simStatus) {
        switch (simStatus) {
            case 1 -> this.simStatus = SimStatus.READY;
            case 2 -> this.simStatus = SimStatus.INTERPROCESS;
            case 3 -> this.simStatus = SimStatus.PAUSED;
            default -> this.simStatus = SimStatus.DISABLED;
        }
    }
    
    public int getSimStatus() {
        return this.simStatus.getStatus();
    }

    @Override
    public void run() {
        while(true) {
            currentWavePicture = simStatus.simInstance(currentWavePicture);
            System.out.print(".");
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
