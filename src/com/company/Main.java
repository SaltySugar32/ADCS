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

//TODO: не создаётся самый первый волновой фронт
//TODO: создание нового волнового фронта в случае противоположных по типу деформации волновых фронтов
//TODO: обработка столкновений волновых фронтов
//TODO: кнопка стоп

//TODO: EqualsCase в хендлере границы - speedR?
//TODO: Как обращаться к du/dx?
//TODO: ВОПРОС НА ЗАСЫПКУ - linearFunction.getB == CL?

//TODO: обработка исключения - при вводе графика функции не должно быть более одного располагающегося на одной координате x значения U

//TODO: DOFIGA
//Вычисление системы уравнений из четырех элементов
//Предыдущий = новый;
//Новый = следующий.
//Преобразуем два уравнения в системе так, чтобы они приняли следующий вид:
//Предыдущий = новый:
//  Равенство членов 0-й степени производной
//  Равенство членов 1-й степени производной
//Новый = следующий:
//  Равенство членов 0-й степени производной
//  Равенство членов 1-й степени производной

//В данных системах имеем четыре неизвестные: A1_i, A2_i, A0_i, V_i
//Выражаем из формулы поиска скорости волнового фронта U-,x === A1_i
//Подставляем на соответствующее место выраженное уравнение (ручками нужно предварительно всё подготовить)
//Таким образом получаем все четыре неизвестные

//Не обработана ситуация, когда оба волновых фронтов идеально совпали по координате при следующем шаге времени
//Не обработана ситуация, когда последовательно идут друг за другом два идентичных волновых фронта


/*
        double speedL = DenoteFactor.MILLI.toMillimeters(computeCharSpeed());
        double A0L = prevWaveFronts.get(0).getA0();
        double A1L = prevWaveFronts.get(0).getA1();
        double A2L = 0.0 - prevWaveFronts.get(0).getA1() / speedL;
        double startTL = prevWaveFronts.get(0).getStartTime();

        double speedR = prevWaveFronts.get(1).getSpeed();
        double A0R = prevWaveFronts.get(1).getA0();
        double A1R = prevWaveFronts.get(1).getA1();
        double A2R = prevWaveFronts.get(1).getA2();
        double startTR = prevWaveFronts.get(0).getStartTime();

        double startCoordinate = 0.0; //Сдвиг координаты

        double A2i = (A1R + A2R * speedR - A1L + A2L * speedL) / (speedR - speedL);
        double A1i = (A1L + A2L * speedL - A2i * speedL);
        double A0i = A0R + A1R * (startTL - startTR) - A2i * startCoordinate;

        WaveFront newWaveFront = new WaveFront(A0i, A1i, A2i, startTL, DenoteFactor.MILLI);

        newWaveFront.setSpeed(speedL);

        return newWaveFront;
 */