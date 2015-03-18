package adt;

import adtInterface.IStack;

/**
 * Created by Mickey on 18/03/2015.
 */
public class Stack<T> implements IStack<T> {
    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public void push(T elem) throws StackOverflowError {

    }

    @Override
    public T peek() {
        return null;
    }

    @Override
    public T pop() {
        return null;
    }

    @Override
    public T poll() {
        return null;
    }
}
