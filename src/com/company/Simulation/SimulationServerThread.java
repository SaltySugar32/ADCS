package com.company.Simulation;

import com.company.ProgramGlobals;
import com.company.Simulation.SimulationFunctions.InterProcessComputations;
import com.company.Simulation.SimulationVariables.SimulationGlobals;

import java.util.Stack;

//Сервер НЕ ДОЛЖЕН содержать переменные, отличные от статуса работы самого сервера
public class SimulationServerThread extends Thread {
    
    //Установка изначального состояния сервера
    SimStatuses simStatus;
    Stack<SimStatuses> simStatusesStack;

    {
        simStatus = SimStatuses.PAUSED;
        simStatusesStack = new Stack<>();
    }

    //Список все возможных состояний сервера с классами, в которых они реализованы
    //На данный момент DISABLED, READY, INTERPROCESS, PAUSED
    private enum SimStatuses{
        DISABLED {
            @Override
            public void doSomeShit() {

            }
        },
        INTERPROCESS {
            @Override
            public void doSomeShit() {
                SimulationGlobals.setCurrentWavePicture(InterProcessComputations.getResult(SimulationGlobals.getCurrentWavePicture()));
            }
        },
        PAUSED {
            @Override
            public void doSomeShit() {
                while (isPaused) {
                    onSpinWait();
                }
            }
        };

        //Абстрактные функции для обращения извне ко вложенным классам
        protected abstract void doSomeShit();

        //Переменная, позволяющая выходить из цикла паузы потока
        protected volatile boolean isPaused = true;
    }

    public SimStatuses getSimStatus() {
        return simStatus;
    }

    //-----------------------РАБОТА СО СТЕКОМ ОПЕРАЦИЙ ПОТОКА--------------------------

    public void addInStack(int simStatus) {
        switch (simStatus) {
            case 1 -> simResume();
            case 2 -> simPause();
            default -> simDisable();
        }
    }

    //Выход из приостановки потока симуляции
    private void simResume() {
        simStatus.isPaused = false;
    }

    //Вход в приостановку потока симуляции
    private void simPause() {
        simStatus.isPaused = true;
        simStatusesStack.push(SimStatuses.PAUSED);
    }

    //Отключение потока симуляции
    private void simDisable() {
        simStatusesStack.push(SimStatuses.DISABLED);
    }

    //Заполнение потока симуляции некоторым количеством операций, которые должны быть выполнены за секунду
    public void fillSimStatusesStack() {
        for (int i = 0; i < ProgramGlobals.getOperationsPerSecond(); i++) {
            simStatusesStack.push(SimStatuses.INTERPROCESS);
        }
    }

    public void checkSimStatusesStack() {
        if (!simStatusesStack.isEmpty()) {
            simStatusesStack.clear();
        }
    }

    public void clearSimStatusesStack() {
        simStatusesStack.clear();
    }

    public void doNextOperation() {
        simStatus = simStatusesStack.pop();
        simStatus.doSomeShit();
    }

    //---------------------------------------------------------------------------------

    //Основной поток, в котором крутится сервер
    @Override
    public void run() {
        fillSimStatusesStack();
        addInStack(2);

        while(SimStatuses.DISABLED != simStatus) {
            doNextOperation();
            checkSimStatusesStack();
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
