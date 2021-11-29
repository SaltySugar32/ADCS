package com.company.Simulation;

import com.company.ProgramGlobals;
import com.company.Simulation.SimulationVariables.WhatNextToDo;

public class SimulationSynchronizerThread extends Thread {
    //ссылка на поток сервера
    SimulationServerThread simulationServerThread;

    //инициализация синхронизатора сервера - получение ссылки на поток сервера
    public SimulationSynchronizerThread(SimulationServerThread simulationServerThread) {
        this.simulationServerThread = simulationServerThread;
    }

    //переменная, указывающая, что делать следующим
    private volatile WhatNextToDo whatNextToDo;
    {
        whatNextToDo = WhatNextToDo.RESUME;
    }

    //SETTER через int переменную, что делать дальше
    public void setWhatNextToDo(int nextJob) {
        switch (nextJob) {
            case 1 -> setWhatNextToDo(WhatNextToDo.PAUSE);
            case 2 -> setWhatNextToDo(WhatNextToDo.RESUME);
            default -> setWhatNextToDo(WhatNextToDo.DISABLE);
        }
    }

    //SETTER, непосредственно указывающий, что делать дальше
    public void setWhatNextToDo(WhatNextToDo whatNextToDo) {
        this.whatNextToDo = whatNextToDo;
    }

    public void setNextJobPAUSE() {
        whatNextToDo = WhatNextToDo.PAUSE;
    }

    public void setNextJobRESUME() {
        whatNextToDo = WhatNextToDo.RESUME;
    }

    public void setNextJobDISABLE() {
        whatNextToDo = WhatNextToDo.DISABLE;
    }

    //GETTER, что должно выполняться следующим
    public WhatNextToDo getWhatNextToDo() {
        return whatNextToDo;
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
            whatNextToDo.nextJob(simulationServerThread);
        }
    }
}
