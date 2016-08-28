package ru.karatun.simpleserver.collection;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class StackImplTest {

    @Test
    public void add() throws Exception {
        Stack<Integer> stack = new StackImpl<>();
        fillStack(stack);
        assertEquals(true, stack.isFull());
        for (int i = 0; i < 10; i++) {
            Integer element = stack.poll();
            assertNotNull(element);
        }
        Integer element = stack.poll();
        assertNull(element);
        assertEquals(true, stack.isEmpty());
    }

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void addMoreThanStackSizeIs() throws Exception {
        Stack<Integer> stack = new StackImpl<>();
        for (int i = 0; i < 11; i++) {
            stack.add(i);
        }
    }

    private void fillStack(Stack<Integer> stack) {
        for (int i = 0; i < 10; i++) {
            stack.add(i);
        }
    }

}