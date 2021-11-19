package com.company.Simulation;

import com.company.Simulation.SimulationStatuses.SimulationDisabled;
import com.company.Simulation.SimulationStatuses.SimulationInterProcess;
import com.company.Simulation.SimulationStatuses.SimulationPaused;
import com.company.Simulation.SimulationStatuses.SimulationReady;
import com.company.Simulation.SimulationVariables.SimulationGlobals;
import com.company.Simulation.SimulationVariables.WaveFront;

import java.util.ArrayList;

//Сервер НЕ ДОЛЖЕН содержать переменные, отличные от статуса работы самого сервера
public class SimulationServer extends Thread {
    
    //Установка изначального состояния сервера
    SimulationServer.SimStatus simStatus;
    {
        simStatus = SimulationServer.SimStatus.READY;
    }

    //Список все возможных состояний сервера с классами, в которых они реализованы
    //На данный момент DISABLED, READY, INTERPROCESS, PAUSED
    private enum SimStatus{
        DISABLED {
            @Override
            public ArrayList<WaveFront> simInstance(ArrayList<WaveFront> currentWavePicture) {
                return SimulationDisabled.getResult(currentWavePicture);
            }

            @Override
            public int getStatus() {
                return 0;
            }
        },
        READY {
            @Override
            public ArrayList<WaveFront> simInstance(ArrayList<WaveFront> currentWavePicture) {
                return SimulationReady.getResult(currentWavePicture);
            }

            @Override
            public int getStatus() {
                return 1;
            }
        },
        INTERPROCESS {
            @Override
            public ArrayList<WaveFront> simInstance(ArrayList<WaveFront> currentWavePicture) {
                return SimulationInterProcess.getResult(currentWavePicture);
            }

            @Override
            public int getStatus() {
                return 2;
            }
        },
        PAUSED {
            @Override
            public ArrayList<WaveFront> simInstance(ArrayList<WaveFront> currentWavePicture) {
                return SimulationPaused.getResult(currentWavePicture);
            }

            @Override
            public int getStatus() {
                return 3;
            }
        };

        //Абстрактные функции для обращения извне ко вложенным классам
        public abstract ArrayList<WaveFront> simInstance(ArrayList<WaveFront> currentWavePicture);
        public abstract int getStatus();
    }

    //Функция, позволяющая манипулировать статусом работы сервера
    public void setSimStatus(int simStatus) {
        switch (simStatus) {
            case 1 -> this.simStatus = SimStatus.READY;
            case 2 -> this.simStatus = SimStatus.INTERPROCESS;
            case 3 -> this.simStatus = SimStatus.PAUSED;
            default -> this.simStatus = SimStatus.DISABLED;
        }
    }

    //Функция, возвращающая статус работы сервера
    public int getSimStatus() {
        return this.simStatus.getStatus();
    }

    //Основной поток, в котором крутится сервер
    @Override
    public void run() {
        while(SimStatus.DISABLED != simStatus) {
            SimulationGlobals.setCurrentWavePicture(simStatus.simInstance(SimulationGlobals.getCurrentWavePicture()));
            try {
                sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
