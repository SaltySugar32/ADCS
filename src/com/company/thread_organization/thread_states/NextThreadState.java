package com.company.thread_organization.thread_states;

import com.company.thread_organization.SimulationServerThread;

/**
 * Перечисление всех указаний для сервера, что нужно выполнить следующим
 */
public enum NextThreadState {
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
