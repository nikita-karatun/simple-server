package ru.karatun.simpleserver.concurrent;

import ru.karatun.simpleserver.collection.Stack;
import ru.karatun.simpleserver.collection.StackImpl;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class ConcurrentStakeImpl<E> implements ConcurrentStake<E> {

    private final Stack<E> threadUnsafeStack;

    public ConcurrentStakeImpl() {
        threadUnsafeStack = new StackImpl<E>();
    }

    public ConcurrentStakeImpl(int size) {
        threadUnsafeStack = new StackImpl<E>(size);
    }

    @Override
    public synchronized void add(E element) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        threadUnsafeStack.add(element);
        notifyAll();
    }

    @Override
    public synchronized E poll() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
        E element = threadUnsafeStack.poll();
        notifyAll();
        return element;
    }

    @Override
    public synchronized boolean isFull() {
        return threadUnsafeStack.isFull();
    }

    @Override
    public synchronized boolean isEmpty() {
        return threadUnsafeStack.isEmpty();
    }

}
