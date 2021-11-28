package com.company.Simulation.SimulationVariables;

import com.company.Simulation.SimulationServerThread;

//Перечисление всех указаний для сервера, что нужно выполнить следующим
public enum WhatNextToDo {
    RESUME {
        @Override
        public void nextJob(SimulationServerThread simulationServerThread) {
            simulationServerThread.simResume();
        }
    },
    PAUSE {
        @Override
        public void nextJob(SimulationServerThread simulationServerThread) {
            simulationServerThread.simPause();
        }
    },
    DISABLE {
        @Override
        public void nextJob(SimulationServerThread simulationServerThread) {
            simulationServerThread.simDisable();
        }
    };

    public abstract void nextJob(SimulationServerThread simulationServerThread);
}
