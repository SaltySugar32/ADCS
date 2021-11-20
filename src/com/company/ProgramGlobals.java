package com.company;

import com.company.Simulation.SimulationServer;
import com.company.UserClients.UserClient;
import com.company.UserClients.UserClient_v1;
import com.company.UserClients.UserClient_v2;

//Файл с глобальными переменными сборки
public record ProgramGlobals() {
    //------------------ВЕРСИЯ ПОЛЬЗОВАТЕЛЬСКОГО ИНТЕРФЕЙСА--------------------

    static final ClientVersion clientVersion = ClientVersion.v2;

     enum ClientVersion {
        v1 { //Клиент для отладки работоспособности программного средства
            public UserClient client(SimulationServer ServerThread){
                return new UserClient_v1(ServerThread);}
        },
        v2 { //Клиент визуального интерфейса клиента
            public UserClient client(SimulationServer ServerThread){
                return new UserClient_v2(ServerThread);}
        };

        public abstract UserClient client(SimulationServer ServerThread);
    }

    //-------------------------------------------------------------------------

    //---------------------------КАДРОВАЯ ЧАСТОТА------------------------------

    static final int framesPerSecond = 15;

    //-------------------------------------------------------------------------


    //----------------------КОЛИЧЕСТВО ОПЕРАЦИЙ В СЕКУНДУ----------------------

    static final int operationsPerSecond = 100;

    //-------------------------------------------------------------------------

}
