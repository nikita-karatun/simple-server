package ru.karatun.simpleserver.collection;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public interface Stack<E> {

    void add(E element);

    E poll();

    boolean isFull();

    boolean isEmpty();

}
