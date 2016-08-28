package ru.karatun.simpleserver.concurrent;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class ThreadPoolTest {

    @Test
    public void execute() throws Exception {
        Counter counter = new Counter();
        ThreadPool threadPool = new ThreadPool(10);
        for (int i = 0; i < 20; i++) {
            threadPool.execute(counter::add);
        }
        Thread.sleep(10000);
        assertEquals(20, counter.get());
    }

}