package com.company.Simulation.SimulationClients;

import com.company.Simulation.SimulationClient;
import com.company.Simulation.SimulationServer;

import java.util.Scanner;

public class SimulationClient_v1 extends Thread implements SimulationClient {
    SimulationServer serverThread;

    public SimulationClient_v1(SimulationServer ServerThread) {
        this.serverThread = ServerThread;
    }

    public void testInterface1() {
        System.out.println("Состояние процесса: " + serverThread.getSimStatus());
        System.out.println("Выберете состояние процесса: ");
        Scanner input = new Scanner(System.in);
        serverThread.setSimStatus(input.nextInt());
    }

    @Override
    public void run() {
        while (true) {
            testInterface1();
        }
    }
}
