package com.company;

import com.company.thread_organization.SimulationServerThread;
import com.company.thread_organization.SimulationSynchronizerThread;
import com.company.user_clients.UserClient;

public class Main {

    /**
     * Получение версии пользовательского интерфейса
     */
    public static UserClient getClient(SimulationSynchronizerThread SynchroThread) {
        return ProgramGlobals.clientVersion.client(SynchroThread);
    }

    /**
     * Инициализация потоков приложения
     */
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

//TODO: алгоритмы генерации волновых фронтов при столкновении
//TODO: проверка столкновения с границей полупространства
//TODO: создание ударной волны на границе

//сильный разрыв = полусигнотон, простой разрыв, ударная волна
//Не обработана ситуация, когда оба волновых фронтов идеально совпали по координате при следующем шаге времени
//Не обработана ситуация, когда последовательно идут друг за другом два идентичных волновых фронта
