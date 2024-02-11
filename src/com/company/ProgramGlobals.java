package com.company;

import com.company.server.runtime.vars.SimTicks;
import com.company.server.runtime.enums.LastError;
import com.company.server.runtime.vars.SimTime;
import com.company.server.runtime.SimServer;
import com.company.client.ui.UserClient;
import com.company.client.ui.UserClient_v2;

/**Файл с глобальными переменными сборки*/
public class ProgramGlobals {

    //------------------ВЕРСИЯ ПОЛЬЗОВАТЕЛЬСКОГО ИНТЕРФЕЙСА--------------------

    /**Возвращает текущую версию клиента:
     * <br>Дебаг - v1
     * <br>Продакшн - v2
     */
    static final ClientVersion clientVersion = ClientVersion.v2;

    enum ClientVersion {
        v2 { //Клиент визуального интерфейса клиента

            public UserClient client(SimServer SynchroThread) {
                return new UserClient_v2(SynchroThread);
            }
        };

        public abstract UserClient client(SimServer SynchroThread);
    }

    //-------------------------------------------------------------------------

    //---------------------------КАДРОВАЯ ЧАСТОТА------------------------------

    static volatile int framesPerSecond;

    static {
        framesPerSecond = 25;
    }

    public static int getFramesPerSecond() {
        return framesPerSecond;
    }

    public static void setFramesPerSecond(int framesPerSecond) {
        ProgramGlobals.framesPerSecond = framesPerSecond;
    }

    //-------------------------------------------------------------------------

    //----------------------МНОЖИТЕЛЬ ВРЕМЕНИ СИМУЛЯЦИИ------------------------

    static double simulationTimeMultiplier = 0.2;

    public static double getSimulationTimeMultiplier() {
        return simulationTimeMultiplier;
    }

    public static void setSimulationTimeMultiplier(double simulationTimeMultiplier) {
        ProgramGlobals.simulationTimeMultiplier = simulationTimeMultiplier;
        SimTime.setSimulationTimeDelta(simulationTimeMultiplier);
    }

    //-------------------------------------------------------------------------

    //----------------------КОЛИЧЕСТВО ОПЕРАЦИЙ В СЕКУНДУ----------------------

    public static int getOperationsPerSecond() {
        return SimTicks.getTickrate();
    }

    public static void setOperationsPerSecond(int operationsPerSecond) {
        SimTicks.setTickrate(operationsPerSecond);
    }

    //-------------------------------------------------------------------------

    //--------------------------ТОЧНОСТЬ ВЫЧИСЛЕНИЙ----------------------------
    static double epsilon;

    static {
        epsilon = Math.pow(10, -9);
    }

    public static double getEpsilon() {
        return epsilon;
    }

    //-------------------------------------------------------------------------

    //--------------------------УРОВЕНЬ ЛОГИРОВАНИЯ----------------------------

    /**
     * 0: Без логов
     * 1: border_handlers
     * 2: collision_handlers
     * 3: отображение только столкновений
     * 99: Все основные логи
     */
    final static double logLevel = 0;

    public static double getLogLevel() {
        return logLevel;
    }

    //-------------------------------------------------------------------------

    //-----------------------ПОСЛЕДНЯЯ ВОЗНИКШАЯ ОШИБКА------------------------

    static volatile LastError lastErrorType = LastError.NULL;

    /**
     * Получение последней произошедшей ошибки
     * @return LastError код ошибки
     */
    public static LastError getLastErrorType() {
        return lastErrorType;
    }

    public static void setLastErrorType(LastError lastErrorType) {
        ProgramGlobals.lastErrorType = lastErrorType;
    }

    //-------------------------------------------------------------------------
}
