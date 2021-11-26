package com.company.UserClients;

import com.company.Simulation.SimulationServerThread;

import java.util.Scanner;

public class UserClient_v1 extends Thread implements UserClient {

    SimulationServerThread serverThread;

    public UserClient_v1(SimulationServerThread ServerThread) {
        this.serverThread = ServerThread;
    }

    public void testInterface1() {
        System.out.println("Состояние процесса: " + serverThread.getSimStatus());
        System.out.println("Выберите состояние процесса: ");
        Scanner input = new Scanner(System.in);
        serverThread.addInStack(input.nextInt());
        for (int i = 0; i < 10; i++) {
            System.out.println("Состояние процесса: " + serverThread.getSimStatus());
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
