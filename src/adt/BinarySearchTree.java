package adt;

import adtInterface.AdtDictionary;
import adtInterface.AdtDictionaryEntry;

import java.util.*;

/*
 * Binary search tree based implementation of the Dictionary
 * interface. The nodes of the tree are ordered by an associated key-attribute
 * key of type K, such that every node's left subtree contains only nodes 
 * whose key-attributes are less than key, and every node's right subtree
 * contains only nodes whose key-attributes are greater than key. A
 * linear order is defined on keys through the Comparable interface.
 * Duplicate keys are not permitted.
 */
public class BinarySearchTree<K extends Comparable<? super K>, V> implements
        AdtDictionary<K, V> {

    private BinarySearchTreeEntry root;
    private int size = 0;
    private int modificationChecker = 0;
            //modification during iterator => fail

    @Override
    public int size() {return size;}

    @Override
    public boolean isEmpty() {
                return size == 0;
        }

    @Override
    public boolean contains(K key) {
        try{
            return get(key) != null;
        }
        catch(NoSuchElementException e){
            return false;
        }
    }

    @Override
    public V get(K key) throws NoSuchElementException {
            return getEntry(root, key).getValue();
        }

    private BinarySearchTreeEntry<K,V> getEntry
                (BinarySearchTreeEntry entry , K searchKey)
                throws NoSuchElementException {
            if(entry != null){
                K key = (K) entry.getKey();

                if(key==searchKey){
                    return entry;
                }
                else if(searchKey.compareTo(key) < 0){
                    return getEntry(entry.getLeftEntry() , searchKey);
                }
                else{
                    return getEntry(entry.getRightEntry() , searchKey);
                }
            }
            throw new NoSuchElementException();
        }

    @Override
    public void put(K key, V value) {
            modificationChecker++;

            root = putEntry(root,key,value);

            size++;
        }

    private BinarySearchTreeEntry<K,V> putEntry
                    (BinarySearchTreeEntry entry,
                    K key ,V newValue){

            if(entry == null){
                return new BinarySearchTreeEntry(key, newValue);
            }
            else{
                if(key == entry.getKey()){
                    entry.setValue(newValue);
                }else{
                    if(key.compareTo((K)entry.getKey()) < 0){
                        entry.setLeftEntry(
                            putEntry(entry.getLeftEntry() , key,newValue));
                    }else{
                        entry.setRightEntry(
                                putEntry(entry.getRightEntry() , key,newValue));
                    }
                }
            }
            return entry;
        }

    @Override
    public void remove(K key) throws NoSuchElementException {
            modificationChecker++;

            root = deleteEntry(root , key);

            size--;
        }

    private BinarySearchTreeEntry<K,V> deleteEntry
                (BinarySearchTreeEntry entry, K key)
                throws NoSuchElementException{
            if(entry == null){
                throw new NoSuchElementException();
            }else{
                if(entry.getKey() == key){
                    entry = deleteNode(entry);
                }else if(key.compareTo((K)entry.getKey()) < 0){
                    entry.setLeftEntry(
                            deleteEntry(entry.getLeftEntry() , key)
                    );
                }else{
                    entry.setRightEntry(
                            deleteEntry(entry.getRightEntry() , key)
                    );
                }

                return entry;
            }
        }

    private BinarySearchTreeEntry<K,V> deleteNode
                (BinarySearchTreeEntry entry){
            if(!entry.hasNext()){
                return null;
            }
            else{
                if(entry.getRightEntry() == null){
                    return entry.getLeftEntry();
                }
                else if(entry.getLeftEntry() == null){
                    return entry.getRightEntry();
                }
                else{
                    BinarySearchTreeEntry replacement 
                            = findLeftMost(entry.getRightEntry());
                    BinarySearchTreeEntry newRight
                            = deleteLeftMost(entry.getRightEntry());
                    replacement.setRightEntry(newRight);
                    replacement.setLeftEntry(entry.getLeftEntry());
                    return replacement;
                }
            }
        }

    private BinarySearchTreeEntry findLeftMost
            (BinarySearchTreeEntry entry) {

        if(entry.getLeftEntry() == null){
            return entry;
        }

        return findLeftMost(entry.getLeftEntry());
    }

    private BinarySearchTreeEntry deleteLeftMost
            (BinarySearchTreeEntry entry) {
        if(entry.getLeftEntry() == null){
            return entry.getRightEntry();
        }
        BinarySearchTreeEntry newChild
                = deleteLeftMost(entry.getLeftEntry());
        entry.setLeftEntry(newChild);
        return entry;
    }

    @Override
    public void clear() {
            modificationChecker++;
            root = null;
            size = 0;
        }

    @Override
    public Iterator<AdtDictionaryEntry<K, V>> iterator() {

                return new Iterator<AdtDictionaryEntry<K, V>>() {

                    private BinarySearchTreeEntry currEntry = root;
                    private Stack<BinarySearchTreeEntry> stack
                            = new Stack<>();

                    private Stack<BinarySearchTreeEntry> visited
                            = new Stack<>();

                    private final int ff = modificationChecker;
                    private boolean hasStarted = false;

                    @Override
                    public boolean hasNext() {
                        if(!hasStarted) {
                            return currEntry != null;
                        }
                        return !stack.isEmpty() ||
                                (currEntry != null && currEntry.hasNext()
                                && !visited(currEntry.getLeftEntry()));
                        //checking right child will break it...
                    }

                    @Override
                    public AdtDictionaryEntry<K, V> next() throws ConcurrentModificationException {
                        //Breadth first Traversal
                        if (ff != modificationChecker) {
                            throw new ConcurrentModificationException();
                        }

                        //get to leftMost entry at start of iterator
                        if(!hasStarted) {
                            hasStarted = true;
                            while (currEntry != null) {
                                stack.push(currEntry);
                                currEntry = currEntry.getLeftEntry();
                            }
                        }

                        currEntry = stack.pop();
                        visited.push(currEntry);

                        if(currEntry!= null
                                && currEntry.getRightEntry() != null){

                            BinarySearchTreeEntry newEntry =
                                    currEntry.getRightEntry();

                            //push right entry onto stack

                            //push all subsequent left entries of the right
                            // entry onto stack
                            while(newEntry != null){
                                stack.push(newEntry);
                                newEntry = newEntry.getLeftEntry();
                            }

                        }
                        return currEntry;
                    }

                    private boolean visited(BinarySearchTreeEntry e){
                        return visited.contains(e);
                    }

                    @Override
                    public void remove() throws
                            UnsupportedOperationException {
                        throw new UnsupportedOperationException();
                    }
                };
        }
}
