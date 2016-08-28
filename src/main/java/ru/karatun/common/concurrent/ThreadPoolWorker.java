package ru.karatun.common.concurrent;

import ru.karatun.common.collection.ConcurrentStake;
import ru.karatun.common.collection.ConcurrentStakeImpl;
import ru.karatun.common.collection.Stake;

import java.util.concurrent.Executor;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
class ThreadPoolWorker implements Executor {

    private final ConcurrentStake<Runnable> runnableStake = new ConcurrentStakeImpl<>(1);
    private final Thread innerThread;
    private volatile boolean stopped = false;

    public ThreadPoolWorker() {
        innerThread = new Thread() {
            @Override
            public void run() {
                try {
                    while (!stopped) {
                        Runnable runnable = runnableStake.poll();
                        runnable.run();
                    }
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        };
        innerThread.start();
    }

    @Override
    public void execute(Runnable command) {
        try {
            runnableStake.add(command);
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