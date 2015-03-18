package adt;

import adtInterface.AdtDictionary;
import adtInterface.AdtDictionaryEntry;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/*
 * Ordered linked list based implementation of the Dictionary
 * interface. The nodes of the list are ordered in ascending order by
 * the key-attribute of type K. Duplicate keys are not permitted.
 */
public class OrderedLinkedList<K extends Comparable<? super K>, V>
        implements AdtDictionary<K, V> {

        private OrderedLinkedListEntry<K,V> firstEntry = null;
        private int size = 0;
        private int modificationChecker = 0;
            //modification during iterator => fail


        @Override
        public int size() {
                return size;
        }

        @Override
        public boolean isEmpty() {
                return size == 0;
        }

    @Override
    public boolean contains(K key) {
        try{
            return get(key) != null;
        }catch(NoSuchElementException e){
            return false;
        }
    }

    @Override
        public V get(K key) throws NoSuchElementException {
            OrderedLinkedListEntry<K,V> curr = firstEntry;

            while(!isEmpty() || curr!=null){
                if(curr.getKey()==key){
                    return curr.getValue();
                }else{
                    curr = curr.getNextEntry();
                }
            }
            throw new NoSuchElementException();
        }


/*        @Override
        public void put(K key, V value){
            modificationChecker++;
            OrderedLinkedListEntry newEntry =
                    new OrderedLinkedListEntry<>(key,value);

            OrderedLinkedListEntry<K,V> prev = findPrev(key);

            if(prev == null){
                newEntry.setnextEntry(firstEntry);
            }
            else if(prev.getKey() == key){
                prev.setValue(value);
            }
            else if(prev.getKey().compareTo(key)<=0){
                prev.setnextEntry(newEntry);
                newEntry.setnextEntry(prev.getNextEntry());
            }
            else{
                firstEntry = newEntry;
            }

            size++;
        }

        private OrderedLinkedListEntry<K ,V> findPrev(K searchKey){
            OrderedLinkedListEntry<K,V> prev = firstEntry;
            if((prev!= null)&&(prev.getKey().compareTo(searchKey)<0)){

                OrderedLinkedListEntry<K,V> curr = prev.getNextEntry();

                while((curr!=null) && (curr.getKey().compareTo(searchKey)<= 0)){
                    prev=curr;
                    curr=curr.getNextEntry();
                }
            }
            return prev;
        }*/

        @Override
        public void put(K key, V value) {
            modificationChecker++;
            OrderedLinkedListEntry newEntry =
                    new OrderedLinkedListEntry<>(key,value);

            if(isEmpty()) {
                firstEntry = newEntry;
            }

            OrderedLinkedListEntry<K,V> curr = firstEntry.getNextEntry();
            OrderedLinkedListEntry<K,V> prev = firstEntry;

            for(int i = 0 ; i < size() ; i++){
                if(isEmpty()) {
                    //covers empty & => prev != null
                    firstEntry = newEntry;
                    break;
                }
                else if(prev.getKey().compareTo(key) > 0 ){
                    //covers first of list case
                    firstEntry = newEntry;
                    newEntry.setnextEntry(prev);
                    break;
                }
                else if(curr == null){
                    //covers end of list case
                    prev.setnextEntry(newEntry);
                    break;
                }
                else if(curr.getKey().compareTo(key) == 0){
                    //current is same as key, replace entry
                    prev.setnextEntry(newEntry);
                    newEntry.setnextEntry(curr.getNextEntry());
                    break;
                }
                else if(prev.getKey().compareTo(key) < 0 &&
                        curr.getKey().compareTo(key) > 0 ){
                    //covers between 2 elems

                    prev.setnextEntry(newEntry);
                    newEntry.setnextEntry(curr);
                    break;
                }

                prev = curr;
                curr = curr.getNextEntry();
            }
            size++;
        }

        @Override
        public void remove(K key) throws NoSuchElementException {
            modificationChecker++;
            OrderedLinkedListEntry<K,V> curr = firstEntry;
            OrderedLinkedListEntry<K,V> prev = null;

            while(!isEmpty() | curr!=null) {
                if (curr.getKey() == key) {
                    if(curr.getNextEntry() == null){
                        if(prev == null){
                            firstEntry = null; //first
                        }else{
                            prev.setnextEntry(null); //last
                        }
                    }else if( prev == null){
                        firstEntry = curr.getNextEntry();
                    }else {
                        prev.setnextEntry(curr.getNextEntry());
                    }
                    size--;
                    return;
                } else {
                    prev = curr;
                    curr = curr.getNextEntry();
                }
            }
            throw new NoSuchElementException();
        }

        @Override
        public void clear() {
            modificationChecker++;
            firstEntry = null;
            size = 0;
        }

        @Override
        public Iterator<AdtDictionaryEntry<K, V>> iterator() {
                return new Iterator<AdtDictionaryEntry<K, V>>() {

                    private OrderedLinkedListEntry currentEntry = firstEntry;
                    private final int ff = modificationChecker;

                    @Override
                    public boolean hasNext() {
                        return currentEntry != null;
                    }

                    @Override
                    public AdtDictionaryEntry<K, V> next() throws ConcurrentModificationException{

                        if(ff != modificationChecker){
                            throw new ConcurrentModificationException();
                        }

                        OrderedLinkedListEntry swap = currentEntry;
                        currentEntry = currentEntry.getNextEntry();
                        return swap;
                    }

                    @Override
                    public void remove() throws UnsupportedOperationException{
                        throw new UnsupportedOperationException();
                    }
                };
        }
}
