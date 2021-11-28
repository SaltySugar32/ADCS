package com.company;

import com.company.Simulation.SimulationServerThread;
import com.company.Simulation.SimulationSynchronizerThread;
import com.company.UserClients.UserClient;

public class Main {

    //Получение версии пользовательского интерфейса
    public static UserClient getClient(SimulationSynchronizerThread SynchroThread) {
        return ProgramGlobals.clientVersion.client(SynchroThread);
    }

    //Инициализация потоков приложения
    public static void main(String[] args) {
        //Сервер крутится в отдельном потоке, чтобы не затормаживать работу пользовательского интерфейса
        SimulationServerThread ServerThread = new SimulationServerThread();

        //Инициализация потока синхронизации вычислений сервера со временем
        SimulationSynchronizerThread SynchroThread = new SimulationSynchronizerThread(ServerThread);

        //Выбор пользовательского интерфейса
        UserClient ClientThread = getClient(SynchroThread);


        //Старт рабочих потоков
        ServerThread.start();
        SynchroThread.start();
        ClientThread.start();
    }
}
