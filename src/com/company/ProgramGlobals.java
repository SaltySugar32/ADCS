package com.company;

import com.company.thread_organization.SimulationSynchronizerThread;
import com.company.user_clients.UserClient;
import com.company.user_clients.UserClient_v1;
import com.company.user_clients.UserClient_v2;

/**Файл с глобальными переменными сборки*/
public record ProgramGlobals() {

    //------------------ВЕРСИЯ ПОЛЬЗОВАТЕЛЬСКОГО ИНТЕРФЕЙСА--------------------

    /**Возвращает текущую версию клиента:
     * <br>Дебаг - v1
     * <br>Продакшн - v2
     */
    static final ClientVersion clientVersion = ClientVersion.v2;

    enum ClientVersion {
        v1 { //Клиент для отладки работоспособности программного средства

            public UserClient client(SimulationSynchronizerThread SynchroThread) {
                return new UserClient_v1(SynchroThread);
            }
        },
        v2 { //Клиент визуального интерфейса клиента

            public UserClient client(SimulationSynchronizerThread SynchroThread) {
                return new UserClient_v2(SynchroThread);
            }
        };

        public abstract UserClient client(SimulationSynchronizerThread SynchroThread);
    }

    //-------------------------------------------------------------------------

    //---------------------------КАДРОВАЯ ЧАСТОТА------------------------------

    static volatile int framesPerSecond;

    static {
        framesPerSecond = 5;
    }

    public static int getFramesPerSecond() {
        return framesPerSecond;
    }

    public static void setFramesPerSecond(int framesPerSecond) {
        ProgramGlobals.framesPerSecond = framesPerSecond;
    }

    //-------------------------------------------------------------------------


    //----------------------КОЛИЧЕСТВО ОПЕРАЦИЙ В СЕКУНДУ----------------------

    static volatile int operationsPerSecond;

    static {
        operationsPerSecond = 100;
    }

    public static int getOperationsPerSecond() {
        return operationsPerSecond;
    }

    public static void setOperationsPerSecond(int operationsPerSecond) {
        ProgramGlobals.operationsPerSecond = operationsPerSecond;
    }

    //-------------------------------------------------------------------------

    static double epsilon;

    static {
        epsilon = Math.pow(10, -12);
    }

    public static double getEpsilon() {
        return epsilon;
    }
}
