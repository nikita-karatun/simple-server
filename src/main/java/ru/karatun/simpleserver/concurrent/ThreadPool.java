package ru.karatun.simpleserver.concurrent;

import java.util.concurrent.Executor;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class ThreadPool implements Executor {

    private final ConcurrentStack<CustomThreadPoolWorker> idleWorkerStack;

    private class CustomThreadPoolWorker extends ThreadPoolWorker {
        @Override
        public void runnableCompletedCallback() {
            try {
                idleWorkerStack.add(this);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public ThreadPool(int size) {
        idleWorkerStack = new ConcurrentStackImpl<>(size);
        for (int i = 0; i < size; i++) {
            try {
                idleWorkerStack.add(new CustomThreadPoolWorker());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    @Override
    public void execute(Runnable command) {
        try {
            ThreadPoolWorker threadPoolWorker = idleWorkerStack.poll();
            threadPoolWorker.execute(command);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
