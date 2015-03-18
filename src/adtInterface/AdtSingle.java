package adtInterface;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Mickey on 18/03/2015.
 */
public interface AdtSingle<T> extends Iterator<AdtSingleEntry<T>> {
    /**
     *
     * @return the number of key-value associations stored in this adt
     */
    public int size();

    /**
     *
     * @return true if and only if this adt is empty
     */
    public boolean isEmpty();

    /**
     * Returns true iff the key is present in the adt
     * @param data
     *          The key to look for inside the adt
     * @return true iff the key is present in the adt
     */
    public boolean contains(T data);



    public T poll();


    public T peek();

    /**
     * Removes all entries from the adt
     */
    public void clear();
}
