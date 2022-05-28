package com.company.thread_organization;

import com.company.ProgramGlobals;
import com.company.simulation.inter_process_functions.InterProcessComputations;
import com.company.simulation.simulation_variables.SimulationGlobals;
import com.company.simulation.simulation_variables.simulation_time.SimulationTime;
import com.company.thread_organization.thread_states.SimulationState;

import java.util.Stack;

//Сервер НЕ ДОЛЖЕН содержать переменные, отличные от статуса работы самого сервера
public class SimulationServerThread extends Thread {

    /**
     * Инициализация стекса статусов симуляции
     * <br> Если попытаться привести его потенциальный внешний вид,
     * образуемый в результате выполнения функций ниже, то выглядеть он будет так:
     * <p>
     * <поле_стека> -> <статус_симуляции>
     * <br> 0 -> <empty_field> / PAUSED / DISABLED - поле приостановки выполнения операций
     * <br> 1 -> INTERPROCESS - если выполняются не операции выше, то выполняется данная операция
     * <br> Больше двух операций существовать не может
     * <p>
     * Такое представление весьма условно, но для понимания вполне сойдет
     */
    Stack<SimulationState> simulationStateStack;

    {
        simulationStateStack = new Stack<>();
        addInSimStatusesStack(SimulationState.INTERPROCESS);
    }

    //-----------------------РАБОТА СО СТЕКОМ ОПЕРАЦИЙ ПОТОКА--------------------------

    /**
     * SETTER для стека статусов симуляции
     */
    public void addInSimStatusesStack(SimulationState simStatus) {
        simulationStateStack.push(simStatus);
    }

    /**
     * GETTER для стека статусов симуляции
     */
    public SimulationState getFromSimStatusesStack() {
        return simulationStateStack.peek();
    }

    /**
     * Выход из приостановки потока симуляции
     */
    public void simResume() {
        if (SimulationState.PAUSED == simulationStateStack.peek())
            simulationStateStack.pop();
    }

    /**
     * Вход в приостановку потока симуляции
     */
    public void simPause() {
        if (SimulationState.INTERPROCESS == simulationStateStack.peek())
            simulationStateStack.push(SimulationState.PAUSED);
    }

    /**
     * Остановка симуляции без выключения потока симуляции
     * с очисткой переменных симуляции.
     */
    public void simStop() {
        if (SimulationState.INTERPROCESS == simulationStateStack.peek())
            simulationStateStack.push(SimulationState.PAUSED);

        SimulationGlobals.getCurrentWavePicture().clear();
        SimulationGlobals.getBorderDisplacementFunctions().clear();
        SimulationTime.setSimulationTime(0.0);
    }

    /**
     * Отключение потока симуляции
     */
    public void simDisable() {
        simulationStateStack.push(SimulationState.DISABLED);
    }

    /**
     * Выполнение следующей операции сервером
     */
    public void doNextComputation() {
        //Так как есть потребность в том, чтобы симуляция выполнялась синхронно с таймером,
        // то добавление паузы до или после выполнения операции обязательно
        simPause();
        SimulationGlobals.setCurrentWavePicture(InterProcessComputations.getResult(SimulationGlobals.getCurrentWavePicture()));
    }

    //---------------------------------------------------------------------------------

    /**
     * Основной поток, в котором крутится сервер
     */
    @Override
    public void run() {
        addInSimStatusesStack(SimulationState.PAUSED);
        //int debug_numOfOperations = 0;

        //Если симуляция не деактивирована, то ...
        while (SimulationState.DISABLED != simulationStateStack.peek()) {
            //System.out.println(debug_numOfOperations++ + " " + SimulationGlobals.getSimulationTime());

            //Если симуляция на паузе, то ждем ...
            while (SimulationState.INTERPROCESS != simulationStateStack.peek()) {
                try {
                    sleep(1000 / (10L * ProgramGlobals.getOperationsPerSecond()));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            //Иначе выполняем следующую операцию ...
            doNextComputation();
            //И снова попадаем на паузу
        }
    }
}