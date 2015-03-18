package adtInterface;

import java.util.NoSuchElementException;

/**
 * The Dictionary interface provides a simplified interface for implementations
 * of abstract data types which map keys to values. Each key is associated with
 * at most one value.
 * 
 * Given a Dictionary and a key, the associated value can be looked up provided
 * they are non-null.
 * 
 * Keys are Comparable objects.
 * 
 * The iterator returns adt-entries in ascending order by key.
 * 
 * <K> is type for the comparable key element <V> is type for the associated
 * value element
 */
public interface AdtDictionary<K extends Comparable<? super K>, V> extends
        Iterable<AdtDictionaryEntry<K, V>> {

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
     * @param key
     *          The key to look for inside the adt
     * @return ture iff the key is present in the adt
     */
    public boolean contains(K key);

    /**
     * Returns the value associated with the key. There can be at most one value
     * associated with each key.
     * 
     * @param key
     *            The key to look for inside the adt
     * @return the value associated with the key
     * @throws NoSuchElementException
     *             if given key does not exist in the adt
     */
    public V get(K key) throws NoSuchElementException;

    /**
     * Creates a adt entry associating the given key and value. If the
     * adt previously contained an entry for this key, the old value is
     * replaced with the given value.
     * 
     * @param key
     *            The key to associate the value with
     * @param value
     *            The value to be associated with the key
     */
    public void put(K key, V value);

    /**
     * Removes the entry for the key from the adt if it is present.
     * 
     * @param key
     *            The key to remove from the adt
     * @throws NoSuchElementException
     *             if the key is not in the adt
     */
    public void remove(K key) throws NoSuchElementException;

    /**
     * Removes all entries from the adt
     */
    public void clear();

}
