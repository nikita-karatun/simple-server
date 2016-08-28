package ru.karatun.simpleserver.collection;

import org.junit.Test;
import ru.karatun.simpleserver.concurrent.ConcurrentStack;
import ru.karatun.simpleserver.concurrent.ConcurrentStackImpl;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class ConcurrentStackTest {

    /**
     * One thread adds 50 items to stack with size of 10, 5 other threads meanwhile pools 10 items each.
     * Stack must be empty as a result.
     */
    @Test
    public void add() throws Exception {
        ConcurrentStack<Integer> stack = new ConcurrentStackImpl<>();

        Thread addingThread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    try {
                        stack.add(i);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        };
        addingThread.start();

        Thread.sleep(1000);

        List<Thread> pollingThreads = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            pollingThreads.add(new Thread() {
                @Override
                public void run() {
                    for (int j = 0; j < 10; j++) {
                        try {
                            stack.poll();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }
            });
        }
        for (Thread pollingThread : pollingThreads) {
            pollingThread.start();
            Thread.sleep(1000);
        }

        addingThread.join();
        for (Thread pollingThread : pollingThreads) {
            pollingThread.join();
        }
        assertTrue(stack.isEmpty());
    }

}