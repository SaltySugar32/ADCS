package com.company.server.runtime;

import com.company.ProgramGlobals;
import com.company.server.runtime.enums.SimState;
import com.company.server.runtime.functions.InterProcessComputations;
import com.company.server.runtime.enums.LastError;
import com.company.server.runtime.vars.SimGlobals;
import com.company.server.runtime.vars.SimTicks;
import com.company.server.runtime.vars.SimTime;

public class SimServer extends Thread {
    /**
     * Состояние потока симуляции
     */
    SimState currentState = SimState.PAUSED;
    
    /**
     * GETTER, возвращающий состояние потока симуляции
     */
    public SimState getSimState() {
        return currentState;
    }
    
    /**
     * Указать симуляции, что она должна быть поставлена на паузу
     */
    public void pendingPAUSE() {
        if (currentState == SimState.WORKING) {
            currentState = SimState.PENDING_PAUSE;
            return;
        }

        currentState = SimState.PAUSED;
    }

    /**
     * Указать симуляции, что её работа должна быть возобновлена
     */
    public void pendingREADY() {
        if (currentState == SimState.PAUSED) {
            currentState = SimState.READY;
        }
    }

    /**
     * Указать симуляции, что она должна быть перезапущена
     */
    public void pendingRESET() {
        new Thread(resetSimulation).start();
    }

    /**
     * Перезапуск симуляции
     */
    Runnable resetSimulation = () -> {
        currentState = SimState.PENDING_PAUSE;

        while (currentState != SimState.PAUSED) {
            try {
                Thread.sleep(SimTicks.getWaitTime());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        SimGlobals.getCurrentWavePicture().clear();
        SimTime.setSimulationTime(0.0);
        ProgramGlobals.setLastErrorType(LastError.NULL);
    };

    /**
     * Инициализация нового цикла симуляции
     * <br>
     * Если по окончании цикла симуляция не ожидает паузы, то происходит разблокировка выполнения
     */
    Runnable nextTick = () -> {
        SimGlobals.setCurrentWavePicture(InterProcessComputations.getResult(SimGlobals.getCurrentWavePicture()));

        if (currentState == SimState.WORKING) {
            currentState = SimState.READY;
        }
    };

    /**
     * Основной цикл выполнения потока симуляции
     */
    @Override
    public void run() {
        //Пока симуляция востребована, не завершаем поток
        while (currentState != SimState.STOP) {
            //Пока выполнение заблокировано, происходит ожидание
            while (currentState != SimState.READY) {
                //Если симуляция в ожидании паузы, то ставим на паузу
                if (currentState == SimState.PENDING_PAUSE) {
                    currentState = SimState.PAUSED;
                }

                try {
                    Thread.sleep(SimTicks.getWaitTime());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            currentState = SimState.WORKING;
            new Thread(nextTick).start();
        }
    }
}
