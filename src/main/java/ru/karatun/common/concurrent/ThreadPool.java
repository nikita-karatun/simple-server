package ru.karatun.common.concurrent;

import ru.karatun.common.collection.ConcurrentStake;
import ru.karatun.common.collection.ConcurrentStakeImpl;

import java.util.concurrent.Executor;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class ThreadPool implements Executor {

    private final ConcurrentStake<CustomThreadPoolWorker> idleWorkerStake;

    private class CustomThreadPoolWorker extends ThreadPoolWorker {
        @Override
        public void runnableCompletedCallback() {
            try {
                idleWorkerStake.add(this);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ThreadPool(int size) {
        idleWorkerStake = new ConcurrentStakeImpl<>(size);
        for (int i = 0; i < size; i++) {
            try {
                idleWorkerStake.add(new CustomThreadPoolWorker());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void execute(Runnable command) {
        try {
            ThreadPoolWorker threadPoolWorker = idleWorkerStake.poll();
            threadPoolWorker.execute(command);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
