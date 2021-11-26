package com.company;

import com.company.UserClients.UserClient;
import com.company.Simulation.SimulationServerThread;

public class Main {

    //Получение версии пользовательского интерфейса
    public static UserClient getClient(SimulationServerThread ServerThread) {
        return ProgramGlobals.clientVersion.client(ServerThread);
    }

    //Инициализация потоков приложения
    public static void main(String[] args) {
        //Сервер крутится в отдельном потоке, чтобы не затормаживать работу пользовательского интерфейса
        SimulationServerThread ServerThread = new SimulationServerThread();

        //Выбор пользовательского интерфейса
        UserClient ClientThread = getClient(ServerThread);

        //Старт рабочих потоков
        ClientThread.start();
        ServerThread.start();
    }
}
