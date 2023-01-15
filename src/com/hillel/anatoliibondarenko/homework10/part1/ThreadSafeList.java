package com.hillel.anatoliibondarenko.homework10.part1;

import java.util.Arrays;
import java.util.Collection;
import java.util.Objects;

public class ThreadSafeList<E> {

    /**
     * The lock protecting all mutators.  (We have a mild preference
     * for builtin monitors over ReentrantLock when either will do.)
     */
    final transient Object lock = new Object();

    /**
     * The array, accessed only via getArray/setArray.
     */
    private transient volatile Object[] array;

    /**
     * Creates an empty list.
     */
    public ThreadSafeList() {
        setArray(new Object[0]);
    }


    private Object[] getArray() {
        return array;
    }

    private void setArray(Object[] a) {
        array = a;
    }

    static <E> E elementAt(Object[] a, int index) {
        return (E) a[index];
    }

    /**
     * Returns the element at the specified position in this list.
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E get(int index) {
        return elementAt(getArray(), index);
    }

    /**
     * Appends the specified element to the end of this list.
     *
     * @param e element to be appended to this list
     * @return {@code true} (as specified by {@link Collection#add})
     */
    public boolean add(E e) {
        synchronized (lock) {
            Object[] es = getArray();
            int len = es.length;
            es = Arrays.copyOf(es, len + 1);
            es[len] = e;
            setArray(es);
            return true;
        }
    }

    /**
     * Removes the element at the specified position in this list.
     * Shifts any subsequent elements to the left (subtracts one from their
     * indices).  Returns the element that was removed from the list.
     *
     * @throws IndexOutOfBoundsException {@inheritDoc}
     */
    public E remove(int index) {
        synchronized (lock) {
            Object[] es = getArray();
            int len = es.length;
            E oldValue = elementAt(es, index);
            int numMoved = len - index - 1;
            Object[] newElements;
            if (numMoved == 0)
                newElements = Arrays.copyOf(es, len - 1);
            else {
                newElements = new Object[len - 1];
                System.arraycopy(es, 0, newElements, 0, index);
                System.arraycopy(es, index + 1, newElements, index,
                        numMoved);
            }
            setArray(newElements);
            return oldValue;
        }
    }

    /**
     * Removes the first occurrence of the specified element from this list,
     * if it is present.  If this list does not contain the element, it is
     * unchanged.  More formally, removes the element with the lowest index
     * {@code i} such that {@code Objects.equals(o, get(i))}
     * (if such an element exists).  Returns {@code true} if this list
     * contained the specified element (or equivalently, if this list
     * changed as a result of the call).
     *
     * @param o element to be removed from this list, if present
     * @return {@code true} if this list contained the specified element
     */
    public boolean remove(Object o) {
        Object[] snapshot = getArray();
        int index = indexOfRange(o, snapshot, 0, snapshot.length);
        return index >= 0 && remove(o, snapshot, index);
    }

    /**
     * A version of remove(Object) using the strong hint that given
     * recent snapshot contains o at the given index.
     */
    private boolean remove(Object o, Object[] snapshot, int index) {
        synchronized (lock) {
            Object[] current = getArray();
            int len = current.length;
            if (snapshot != current) findIndex:{
                int prefix = Math.min(index, len);
                for (int i = 0; i < prefix; i++) {
                    if (current[i] != snapshot[i]
                            && Objects.equals(o, current[i])) {
                        index = i;
                        break findIndex;
                    }
                }
                if (index >= len)
                    return false;
                if (current[index] == o)
                    break findIndex;
                index = indexOfRange(o, current, index, len);
                if (index < 0)
                    return false;
            }
            Object[] newElements = new Object[len - 1];
            System.arraycopy(current, 0, newElements, 0, index);
            System.arraycopy(current, index + 1,
                    newElements, index,
                    len - index - 1);
            setArray(newElements);
            return true;
        }
    }

    /**
     * static version of indexOf, to allow repeated calls without
     * needing to re-acquire array each time.
     *
     * @param o    element to search for
     * @param es   the array
     * @param from first index to search
     * @param to   one past last index to search
     * @return index of element, or -1 if absent
     */
    private static int indexOfRange(Object o, Object[] es, int from, int to) {
        if (o == null) {
            for (int i = from; i < to; i++)
                if (es[i] == null)
                    return i;
        } else {
            for (int i = from; i < to; i++)
                if (o.equals(es[i]))
                    return i;
        }
        return -1;
    }

    /**
     * Returns a string representation of this list.  The string
     * representation consists of the string representations of the list's
     * elements in the order they are returned by its iterator, enclosed in
     * square brackets ({@code "[]"}).  Adjacent elements are separated by
     * the characters {@code ", "} (comma and space).  Elements are
     * converted to strings as by {@link String#valueOf(Object)}.
     *
     * @return a string representation of this list
     */
    public String toString() {
        return Arrays.toString(getArray());
    }
}
