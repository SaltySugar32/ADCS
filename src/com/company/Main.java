package com.company;

import com.company.server.simulation.collision.CollisionSwitcher;
import com.company.server.runtime.SimServer;
import com.company.client.ui.UserClient;

public class Main {

    /**
     * Получение версии пользовательского интерфейса
     */
    public static UserClient getClient(SimServer SynchroThread) {
        return ProgramGlobals.clientVersion.client(SynchroThread);
    }

    /**
     * Инициализация потоков приложения
     */
    public static void main(String[] args) {
        //Инициализация потока синхронизации вычислений сервера со временем
        SimServer SynchroThread = new SimServer();

        //Выбор пользовательского интерфейса
        UserClient ClientThread = getClient(SynchroThread);

        //Старт рабочих потоков
        SynchroThread.start();
        ClientThread.start();

        //Вообще говоря, костыль
        //CollisionSwitcher.initCollisionHandlers();
        CollisionSwitcher.initCollisionHandlers_new();
    }
}

//TODO: перепроверить столкновение с границей, а именно оффсет
//TODO: перепроверить алгоритм столкновений

// Возможная ошибка - один из волновых фронтов после столкновения пары волновых фронтов помечается на удаление из-за того, что он лишь редактируется, а не создаётся новым с нуля.

//сильный разрыв = полусигнотон, простой разрыв, ударная волна
//Не обработана ситуация, когда оба волновых фронтов идеально совпали по координате при следующем шаге времени
//Не обработана ситуация, когда последовательно идут друг за другом два идентичных волновых фронта

/*TODO: идея решения СНЛАУ
 * Нужно произвести вывод такой, чтобы каждое из выражений зависело друг от друга
 * Затем проверять, в каком направлении стоит двигать один коэффициент относительно другого для достижения максимального схождения
 * И решать эту еб*нину
 * Может даже сработает
 */