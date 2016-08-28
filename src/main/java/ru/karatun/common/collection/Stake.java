package ru.karatun.common.collection;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public interface Stake<E> {

    void add(E element);

    E poll();

}
