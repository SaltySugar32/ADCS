package com.company;

import com.company.Simulation.SimulationClient;
import com.company.Simulation.SimulationClients.SimulationClient_v2;
import com.company.Simulation.SimulationClients.SimulationClient_v1;
import com.company.Simulation.SimulationServer;

public class Main {
    enum clientVersion {
        v1 { //Клиент для отладки работоспособности программного средства
            public SimulationClient getClientVer(SimulationServer ServerThread){
                return new SimulationClient_v1(ServerThread);}
        },
        v2 { //Клиент визуального интерфейса клиента
            public SimulationClient getClientVer(SimulationServer ServerThread){
                return new SimulationClient_v2(ServerThread);}
        };

        public abstract SimulationClient getClientVer(SimulationServer ServerThread);
    }

    // Выбор клиента
    static SimulationClient selectClientVersion(SimulationServer ServerThread) {
        //Затычка
        return clientVersion.v1.getClientVer(ServerThread);
    }

    //Инициализация потоков приложения
    public static void main(String[] args) {
        //Сервер крутится в отдельном потоке, чтобы не затормаживать работу пользовательского интерфейса
        SimulationServer ServerThread = new SimulationServer();

        //Выбор пользовательского интерфейса
        SimulationClient ClientThread = selectClientVersion(ServerThread);

        //Старт рабочих потоков
        ClientThread.start();
        ServerThread.start();
    }
}
