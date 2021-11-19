package com.company;

import com.company.Simulation.SimulationVariables.SimulationGlobals;
import com.company.UserClients.UserClient;
import com.company.Simulation.SimulationServer;

public class Main {

    //Получение версии пользовательского интерфейса
    public static UserClient getClient(SimulationServer ServerThread) {
        return ProgramGlobals.clientVersion.client(ServerThread);
    }

    //Инициализация потоков приложения
    public static void main(String[] args) {
        //Сервер крутится в отдельном потоке, чтобы не затормаживать работу пользовательского интерфейса
        SimulationServer ServerThread = new SimulationServer();

        //Выбор пользовательского интерфейса
        UserClient ClientThread = getClient(ServerThread);

        //Старт рабочих потоков
        ClientThread.start();
        ServerThread.start();
    }
}
