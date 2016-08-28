package ru.karatun.simpleserver.collection;

/**
 * Created by Nikita Karatun
 * 28.08.16.
 */
public class StakeImpl<E> implements Stake<E> {

    private static final int DEFAULT_SIZE = 10;

    private int currentSize = 0;

    private final Object[] array;

    public StakeImpl() {
        this(DEFAULT_SIZE);
    }

    public StakeImpl(int size) {
        this.array = new Object[size];
    }

    @Override
    public void add(E element) {
        array[currentSize] = element;
        currentSize++;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E poll() {
        E element;
        if (currentSize != 0) {
            currentSize--;
            element = (E) array[currentSize];
            array[currentSize] = null;
        } else {
            element = null;
        }
        return element;
    }

    @Override
    public boolean isFull() {
        return currentSize == array.length;
    }

    @Override
    public boolean isEmpty() {
        return currentSize == 0;
    }

}
