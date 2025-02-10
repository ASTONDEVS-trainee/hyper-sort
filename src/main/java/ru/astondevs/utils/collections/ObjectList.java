package ru.astondevs.utils.collections;

import java.util.Arrays;

public class ObjectList<T extends Comparable<T>> {
    private static final int DEFAULT_CAPACITY = 10;
    private Object[] elements;
    private int size;

    public ObjectList() {
        this.elements = new Object[DEFAULT_CAPACITY];
        this.size = 0;
    }

    public ObjectList(int initialCapacity) {
        if (initialCapacity < 0) {
            throw new IllegalArgumentException("Illegal Capacity: " + initialCapacity);
        }
        this.elements = new Object[initialCapacity];
        this.size = 0;
    }

    public void add(T element) {
        ensureCapacity(size + 1);
        elements[size++] = element;
    }

    @SafeVarargs
    public final void add(T... elements) {
        for (T element : elements) {
            add(element);
        }

    }

    public void addAll(T[] elementsToAdd) {
        ensureCapacity(size + elementsToAdd.length);
        System.arraycopy(elementsToAdd, 0, elements, size, elementsToAdd.length);
        size += elementsToAdd.length;
    }

    @SuppressWarnings("unchecked")
    public T get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return (T) elements[index];
    }

    public int size() {
        return size;
    }

    public T[] sort() {
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Arrays.copyOf(elements, size, Comparable[].class); // Копируем массив
        TimSort.sort(arr);
        System.arraycopy(arr, 0, elements, 0, size); // Копируем обратно
        return arr;
    }

    public int indexOf(T key) {
        @SuppressWarnings("unchecked")
        T[] arr = (T[]) Arrays.copyOf(elements, size);
        return BinSearch.binarySearch(arr, key);
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public void clear() {
        for (int i = 0; i < size; i++) {
            elements[i] = null;
        }
        size = 0;
    }

    public boolean contains(T element) {
        return indexOf(element) >= 0;
    }

    private void ensureCapacity(int minCapacity) {
        if (minCapacity > elements.length) {
            int newCapacity = elements.length * 2;
            if (newCapacity < minCapacity) {
                newCapacity = minCapacity;
            }
            elements = Arrays.copyOf(elements, newCapacity);
        }
    }

    @Override
    public String toString() {
        return Arrays.toString(Arrays.copyOf(elements, size));
    }
}