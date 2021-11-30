package com.company.Simulation;

import com.company.ProgramGlobals;
import com.company.Simulation.SimulationVariables.NextJobs;

public class SimulationSynchronizerThread extends Thread {
    //ссылка на поток сервера
    SimulationServerThread simulationServerThread;

    //инициализация синхронизатора сервера - получение ссылки на поток сервера
    public SimulationSynchronizerThread(SimulationServerThread simulationServerThread) {
        this.simulationServerThread = simulationServerThread;
    }

    //переменная, указывающая, что делать следующим
    private volatile NextJobs nextJob;
    {
        nextJob = NextJobs.RESUME;
    }

    //SETTER через int переменную, что делать дальше
    public void setNextJob(int nextJob) {
        switch (nextJob) {
            case 1 -> setNextJob(NextJobs.PAUSE);
            case 2 -> setNextJob(NextJobs.RESUME);
            default -> setNextJob(NextJobs.DISABLE);
        }
    }

    //SETTER, непосредственно указывающий, что делать дальше
    public void setNextJob(NextJobs nextJob) {
        this.nextJob = nextJob;
    }

    public void setNextJobPAUSE() {
        nextJob = NextJobs.PAUSE;
    }

    public void setNextJobRESUME() {
        nextJob = NextJobs.RESUME;
    }

    public void setNextJobDISABLE() {
        nextJob = NextJobs.DISABLE;
    }

    //GETTER, что должно выполняться следующим
    public NextJobs getNextJob() {
        return nextJob;
    }

    @Override
    public void run() {
        int timeOnOperation = 1000 / ProgramGlobals.getOperationsPerSecond();

        while (simulationServerThread.isAlive()) {
            try {
                sleep(timeOnOperation);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            nextJob.nextJob(simulationServerThread);
        }
    }
}
