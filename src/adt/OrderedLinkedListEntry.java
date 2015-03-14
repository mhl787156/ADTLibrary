package adt;


import adtInterface.AdtEntry;

public class OrderedLinkedListEntry<K, V> implements AdtEntry<K, V> {

    private K key;
    private V value;
    private OrderedLinkedListEntry nextEntry;

    public OrderedLinkedListEntry(K key, V value){
        this.key = key;
        this.value = value;
    }

    public OrderedLinkedListEntry(K key, V value,
                                  OrderedLinkedListEntry entry){
        this.key = key;
        this.value = value;
        this.nextEntry = entry;
    }

    public void setnextEntry(OrderedLinkedListEntry nextEntry){
        this.nextEntry = nextEntry;
    }

    public void setValue(V value){
        this.value = value;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public V getValue() {
        return value;
    }

    public OrderedLinkedListEntry getNextEntry(){
        return nextEntry;
    }

    public boolean hasNext(){
        return nextEntry != null;
    }

}
