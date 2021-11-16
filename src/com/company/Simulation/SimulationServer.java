package com.company.Simulation;

import com.company.WaveClasses.WaveFront;

import java.util.ArrayList;

public class SimulationServer extends Thread {
    public enum Status{DISABLED, READY, INTERPROCESS, PAUSED};
    Status status;

    ArrayList<WaveFront> wavePicture = new ArrayList();

    public SimulationServer() {
        this.status = Status.DISABLED;
    }

    @Override
    public void run() {

    }
}
