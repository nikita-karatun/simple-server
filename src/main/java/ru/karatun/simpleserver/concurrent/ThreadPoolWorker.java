package ru.karatun.simpleserver.concurrent;

import java.util.concurrent.Executor;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class ThreadPoolWorker implements Executor {

    private final ConcurrentStack<Runnable> runnableStack = new ConcurrentStackImpl<>(1);
    private final Thread innerThread;
    private volatile boolean stopped = false;

    public ThreadPoolWorker() {
        innerThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!stopped) {
                        Runnable runnable = runnableStack.poll();
                        runnable.run();
                        runnableCompletedCallback();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        innerThread.start();
    }

    public void runnableCompletedCallback() {
    }

    @Override
    public void execute(Runnable command) {
        try {
            runnableStack.add(command);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public synchronized void stop() {
        stopped = true;
        innerThread.interrupt();
    }

    public synchronized void join() throws InterruptedException {
        innerThread.join();
    }

}
