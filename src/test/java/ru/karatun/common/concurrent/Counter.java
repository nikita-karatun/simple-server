package ru.karatun.common.concurrent;

public class Counter {
    private int count;

    public synchronized void add() {
        count++;
    }

    public synchronized int get() {
        return count;
    }
}