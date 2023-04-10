package com.company.server;

public class SimTime {
    private static volatile int tickrate = 25;

    public static int getTickrate() {
        return tickrate;
    }

    public static void setTickrate(int tickrate) {
        SimTime.tickrate = tickrate;
    }

    public static long getWaitTime() {
        return 1000 / tickrate;
    }

    public static double computeDiscretion(double number) {
        return number / tickrate;
    }

    public static int computeDiscretion(int number) {
        return number / tickrate;
    }

    public static long computeDiscretion(long number) {
        return number / tickrate;
    }
}
