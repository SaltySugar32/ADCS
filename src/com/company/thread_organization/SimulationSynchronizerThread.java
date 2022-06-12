package com.company.thread_organization;

import com.company.ProgramGlobals;
import com.company.thread_organization.thread_states.NextThreadState;

public class SimulationSynchronizerThread extends Thread {
    /**
     * ссылка на поток сервера
     */
    SimulationServerThread simulationServerThread;

    /**
     * инициализация синхронизатора сервера - получение ссылки на поток сервера
     */
    public SimulationSynchronizerThread(SimulationServerThread simulationServerThread) {
        this.simulationServerThread = simulationServerThread;
    }

    /**
     * переменная, указывающая, что делать следующим
     */
    private volatile NextThreadState nextThreadState;

    {
        nextThreadState = NextThreadState.PAUSE;
    }

    /**
     * SETTER, непосредственно указывающий, что делать дальше
     */
    public void setNextJob(NextThreadState nextThreadState) {
        this.nextThreadState = nextThreadState;
    }

    /**
     * Указать симуляции, что она должна быть поставлена на паузу
     */
    public void setNextJobPAUSE() {
        nextThreadState = NextThreadState.PAUSE;
    }

    /**
     * Указать симуляции, что её работа должна быть возобновлена
     */
    public void setNextJobRESUME() {
        nextThreadState = NextThreadState.RESUME;
    }

    /**
     * Указать симуляции, что её работа должна быть остановлена, но не прекращена полностью
     */
    public void setNextJobSTOP() {
        simulationServerThread.simStop();
        nextThreadState = NextThreadState.PAUSE;
    }

    /**
     * Указать симуляции, что она должна быть отключена
     */
    public void setNextJobDISABLE() {
        nextThreadState = NextThreadState.DISABLE;
    }

    /**
     * GETTER, что должно выполняться следующим
     */
    public NextThreadState getNextJob() {
        return nextThreadState;
    }

    /**
     * Основной поток, в котором крутится управление операциями сервера
     */
    @Override
    public void run() {

        while (simulationServerThread.isAlive()) {
            try {
                sleep(1000 / ProgramGlobals.getOperationsPerSecond());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nextThreadState.nextJob(simulationServerThread);
        }
    }
}
