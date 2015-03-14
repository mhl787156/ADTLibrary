package adt;


import adtInterface.AdtEntry;

// Implementation class representing nodes of the binary search tree.
public class BinarySearchTreeEntry<K, V> implements AdtEntry<K, V> {

    private K key;
    private V value;
    private BinarySearchTreeEntry leftEntry;
    private BinarySearchTreeEntry rightEntry;

    public BinarySearchTreeEntry(K key, V value){
        this.key = key;
        this.value = value;
    }

    public BinarySearchTreeEntry(K key, V value,
                                 BinarySearchTreeEntry leftentry,
                                 BinarySearchTreeEntry rightEntry){
        this.key = key;
        this.value = value;
        this.leftEntry = leftentry;
        this.rightEntry = rightEntry;
    }

    public void setLeftEntry(BinarySearchTreeEntry entry){
        leftEntry = entry;
    }

    public void setRightEntry(BinarySearchTreeEntry entry){
        rightEntry = entry;
    }

    public BinarySearchTreeEntry getLeftEntry(){
        return leftEntry;
    }

    public BinarySearchTreeEntry getRightEntry(){
        return rightEntry;
    }

    public boolean hasNext(){
        return leftEntry != null || rightEntry != null;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    public void setValue(V value){
        this.value = value;
    }
}
