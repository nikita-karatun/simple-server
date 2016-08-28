package ru.karatun.common.collection;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class ConcurrentStake<E> implements Stake<E> {

    private final Stake<E> threadUnsafeStake;

    public ConcurrentStake() {
        threadUnsafeStake = new StakeImpl<E>();
    }

    public ConcurrentStake(int size) {
        threadUnsafeStake = new StakeImpl<E>(size);
    }

    @Override
    public synchronized void add(E element) {
        waitWhileFull();
        threadUnsafeStake.add(element);
        notifyAll();
    }

    @Override
    public synchronized E poll() {
        waitWhileEmpty();
        E element = threadUnsafeStake.poll();
        notifyAll();
        return element;
    }

    @Override
    public synchronized boolean isFull() {
        return threadUnsafeStake.isFull();
    }

    @Override
    public synchronized boolean isEmpty() {
        return threadUnsafeStake.isEmpty();
    }

    private void waitWhileFull() {
        try {
            while (isFull()) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    private void waitWhileEmpty() {
        try {
            while (isEmpty()) {
                wait();
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
