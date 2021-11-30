package com.company.UserClients;

import com.company.Simulation.SimulationSynchronizerThread;

import java.util.Scanner;

public class UserClient_v1 extends Thread implements UserClient {

    SimulationSynchronizerThread serverThread;

    public UserClient_v1(SimulationSynchronizerThread SynchroThread) {
        this.serverThread = SynchroThread;
    }

    public void testInterface1() {
        System.out.println("Состояние процесса: " + serverThread.getNextJob());
        System.out.println("Выберите состояние процесса: ");
        Scanner input = new Scanner(System.in);
        serverThread.setNextJob(input.nextInt());

        for (int i = 0; i < 10; i++) {
            System.out.println("Состояние процесса: " + serverThread.getNextJob());
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void run() {
        while (true) {
            testInterface1();
        }
    }
}
