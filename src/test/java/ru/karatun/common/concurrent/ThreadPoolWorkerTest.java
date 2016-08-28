package ru.karatun.common.concurrent;

import org.junit.Test;

import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class ThreadPoolWorkerTest {

    @Test
    public void execute() throws Exception {
        Counter counter = new Counter();
        ThreadPoolWorker threadPoolWorker = new ThreadPoolWorker();
        for (int i = 0; i < 20; i++) {
            threadPoolWorker.execute(counter::add);
        }
        threadPoolWorker.stop();
        threadPoolWorker.join();

        assertEquals(20, counter.get());
    }

}