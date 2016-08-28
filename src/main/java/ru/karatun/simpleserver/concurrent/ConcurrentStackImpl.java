package ru.karatun.simpleserver.concurrent;

import ru.karatun.simpleserver.collection.Stack;
import ru.karatun.simpleserver.collection.StackImpl;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class ConcurrentStackImpl<E> implements ConcurrentStack<E> {

    private final Stack<E> threadUnsafeStack;

    public ConcurrentStackImpl() {
        threadUnsafeStack = new StackImpl<E>();
    }

    public ConcurrentStackImpl(int size) {
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
