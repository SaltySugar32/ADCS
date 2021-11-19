package com.company.UserClients;

import com.company.Simulation.SimulationServer;

import java.util.Scanner;

public class UserClient_v1 extends Thread implements UserClient {

    SimulationServer serverThread;

    public UserClient_v1(SimulationServer ServerThread) {
        this.serverThread = ServerThread;
    }

    public void testInterface1() {
        System.out.println("Состояние процесса: " + serverThread.getSimStatus());
        System.out.println("Выберите состояние процесса: ");
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
