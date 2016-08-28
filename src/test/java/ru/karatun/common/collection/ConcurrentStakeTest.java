package ru.karatun.common.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadFactory;

import static org.junit.Assert.*;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class ConcurrentStakeTest {

    /**
     * One thread adds 50 items to stake with size of 10, 5 other threads meanwhile pools 10 items each.
     * Stake must be empty as a result.
     */
    @Test
    public void add() throws Exception {
        Stake<Integer> stake = new ConcurrentStake<>();

        Thread addingThread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 50; i++) {
                    stake.add(i);
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
                        stake.poll();
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
        assertTrue(stake.isEmpty());
    }

}