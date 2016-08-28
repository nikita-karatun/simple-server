package ru.karatun.common.collection;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class ConcurrentStakeImpl<E> implements ConcurrentStake<E> {

    private final Stake<E> threadUnsafeStake;

    public ConcurrentStakeImpl() {
        threadUnsafeStake = new StakeImpl<E>();
    }

    public ConcurrentStakeImpl(int size) {
        threadUnsafeStake = new StakeImpl<E>(size);
    }

    @Override
    public synchronized void add(E element) throws InterruptedException {
        while (isFull()) {
            wait();
        }
        threadUnsafeStake.add(element);
        notifyAll();
    }

    @Override
    public synchronized E poll() throws InterruptedException {
        while (isEmpty()) {
            wait();
        }
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

}
