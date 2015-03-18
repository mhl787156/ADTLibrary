package adt;

import adtInterface.AdtDictionary;
import adtInterface.AdtDictionaryEntry;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created by Mickey on 18/03/2015.
 */
public class RedBlackTree<K extends Comparable<K>,V> implements AdtDictionary<K,V> {
    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean contains(K key) {
        return false;
    }

    @Override
    public V get(K key) throws NoSuchElementException {
        return null;
    }

    @Override
    public void put(K key, V value) {

    }

    @Override
    public void remove(K key) throws NoSuchElementException {

    }

    @Override
    public void clear() {

    }

    @Override
    public Iterator<AdtDictionaryEntry<K, V>> iterator() {
        return null;
    }
}
