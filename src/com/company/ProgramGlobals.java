package com.company;

import com.company.Simulation.SimulationServerThread;
import com.company.Simulation.SimulationSynchronizerThread;
import com.company.UserClients.UserClient;
import com.company.UserClients.UserClient_v1;
import com.company.UserClients.UserClient_v2;

//Файл с глобальными переменными сборки
public record ProgramGlobals() {
    //------------------ВЕРСИЯ ПОЛЬЗОВАТЕЛЬСКОГО ИНТЕРФЕЙСА--------------------

    static final ClientVersion clientVersion = ClientVersion.v2;

     enum ClientVersion {
        v1 { //Клиент для отладки работоспособности программного средства
            public UserClient client(SimulationSynchronizerThread SynchroThread){
                return new UserClient_v1(SynchroThread);}
        },
        v2 { //Клиент визуального интерфейса клиента
            public UserClient client(SimulationSynchronizerThread SynchroThread){
                return new UserClient_v2(SynchroThread);}
        };

        public abstract UserClient client(SimulationSynchronizerThread SynchroThread);
    }

    //-------------------------------------------------------------------------

    //-------------------------------------------------------------------------


    //-------------------------------------------------------------------------

    //---------------------------КАДРОВАЯ ЧАСТОТА------------------------------

    static int framesPerSecond = 5;

    public static int getFramesPerSecond() {
        return framesPerSecond;
    }

    public static void setFramesPerSecond(int framesPerSecond) {
        ProgramGlobals.framesPerSecond = framesPerSecond;
    }

    //-------------------------------------------------------------------------


    //----------------------КОЛИЧЕСТВО ОПЕРАЦИЙ В СЕКУНДУ----------------------

    static int operationsPerSecond = 100;

    public static int getOperationsPerSecond() {
        return operationsPerSecond;
    }

    public static void setOperationsPerSecond(int operationsPerSecond) {
        ProgramGlobals.operationsPerSecond = operationsPerSecond;
    }

    //-------------------------------------------------------------------------

}
