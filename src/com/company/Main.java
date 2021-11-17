package com.company;

import com.company.Simulation.SimulationVariables.SimulationGlobals;
import com.company.UserClients.UserClient;
import com.company.UserClients.UserClient_v2;
import com.company.UserClients.UserClient_v1;
import com.company.Simulation.SimulationServer;

public class Main {
    //Инициализация потоков приложения
    public static void main(String[] args) {
        //Сервер крутится в отдельном потоке, чтобы не затормаживать работу пользовательского интерфейса
        SimulationServer ServerThread = new SimulationServer();

        //Выбор пользовательского интерфейса
        UserClient ClientThread = ProgramGlobals.getClient(ServerThread);

        //Старт рабочих потоков
        ClientThread.start();
        ServerThread.start();
    }
}
