package com.company.server.runtime.vars;

public class SimTicks {
    private static final int maxTickrate = 100;

    private static volatile int tickrate = 25;

    public static int getTickrate() {
        return tickrate;
    }

    public static void setTickrate(int tickrate) {
        SimTicks.tickrate = tickrate;
    }

    public static long getWaitTime() {
        return 1000 / tickrate;
    }

    public static double computeDiscretion(double number) {
        return number * maxTickrate / tickrate;
    }

    public static int computeDiscretion(int number) {
        return number * maxTickrate / tickrate;
    }

    public static long computeDiscretion(long number) {
        return number * maxTickrate / tickrate;
    }
}
