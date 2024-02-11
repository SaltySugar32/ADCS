package com.company.server.runtime.enums;

/**
 * Список все возможных состояний сервера
 * <br>На данный момент READY, PAUSED, PENDING_PAUSE, WORKING, STOP
 * <br> PENDING_PAUSE требуется для реализации ожидания паузы в потоке выполнения
 */
public enum SimState {
    READY,
    PAUSED,
    PENDING_PAUSE,
    WORKING,
    STOP
}
