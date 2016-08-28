package ru.karatun.common.collection;

import org.junit.Test;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class StakeImplTest {

    @Test
    public void add() throws Exception {
        Stake<Integer> stake = new StakeImpl<>();
        fillStake(stake);
        for (int i = 0; i < 10; i++) {
            Integer element = stake.poll();
            assertNotNull(element);
        }
        Integer element = stake.poll();
        assertNull(element);
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void addMoreThanStakeSizeIs() throws Exception {
        Stake<Integer> stake = new StakeImpl<>();
        fillStake(stake);
        for (int i = 0; i < 11; i++) {
            stake.poll();
        }
    }

    private void fillStake(Stake<Integer> stake) {
        for (int i = 0; i < 10; i++) {
            stake.add(i);
        }
    }

}