package ru.karatun.common.collection;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public interface ConcurrentStake<E> {

    void add(E element) throws InterruptedException;

    E poll() throws InterruptedException;

    boolean isFull();

    boolean isEmpty();
}
