package com.company;

import com.company.Simulation.SimulationClient;
import com.company.Simulation.SimulationClients.SimulationClient_v2;
import com.company.Simulation.SimulationClients.SimulationClient_v1;
import com.company.Simulation.SimulationServer;

import java.nio.channels.SelectionKey;

public class Main {
    enum clientVersion {
        v1 {
            public SimulationClient getClientVer(SimulationServer ServerThread){
                return new SimulationClient_v1(ServerThread);}
        },
        v2 {
            public SimulationClient getClientVer(SimulationServer ServerThread){
                return new SimulationClient_v2(ServerThread);}
        };

        public abstract SimulationClient getClientVer(SimulationServer ServerThread);
    };

    static SimulationClient selectClientVersion(SimulationServer ServerThread) {
        //Затычка
        return clientVersion.v1.getClientVer(ServerThread);
    }

    public static void main(String[] args) {
	SimulationServer ServerThread = new SimulationServer();
    SimulationClient ClientThread = selectClientVersion(ServerThread);
    ClientThread.run();
    }
}
