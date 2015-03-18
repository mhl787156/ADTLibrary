package adtInterface;

/**
 * Created by Mickey on 18/03/2015.
 */
public interface IStack<T> {

    public boolean isEmpty();

    public void push(T elem) throws StackOverflowError;

    public T peek();

    public T pop();

    public T poll();
}
