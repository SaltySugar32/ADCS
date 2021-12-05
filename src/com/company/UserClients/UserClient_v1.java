package com.company.UserClients;

import com.company.Simulation.SimulationSynchronizerThread;

import java.util.Scanner;

public class UserClient_v1 extends Thread implements UserClient {

    SimulationSynchronizerThread synchroThread;

    public UserClient_v1(SimulationSynchronizerThread SynchroThread) {
        this.synchroThread = SynchroThread;
    }

    public void testInterface1() {
        System.out.println("Состояние процесса: " + synchroThread.getNextJob());
        System.out.println("Выберите состояние процесса: ");
        Scanner input = new Scanner(System.in);
        synchroThread.setNextJob(input.nextInt());

        for (int i = 0; i < 10; i++) {
            System.out.println("Состояние процесса: " + synchroThread.getNextJob());
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
