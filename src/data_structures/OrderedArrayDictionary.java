//Samuel Akinmulero
//CS310 Riggins
//cssc0900
//04-29-17
//Program 3: OrderedArrayDictionary

package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;


public class OrderedArrayDictionary<K extends Comparable<K>,V> implements DictionaryADT<K , V> {
    class DicArr<K, V> implements Comparable<DicArr<K, V>> {
        K key;
        V value;
        public DicArr (K k, V v) {
            this.key = k;
            this.value = v;
        }

        public int compareTo(DicArr<K, V> n) {
            return (((Comparable<K>)n.key).compareTo(this.key));
        }
    }

    DicArr<K,V> [] arr;
    int asize, tSize;
    long modCount;

    public OrderedArrayDictionary(int n) {
        asize = 0;
        tSize = n;
        modCount = 0;
        arr = new DicArr[tSize];
    } //end constructor


    // Returns true if the dictionary has an object identified by
// key in it, otherwise false.
    public boolean contains(K key) {
        for (int i = 0; i <= asize-1; i++)
            if ((key).compareTo(arr[i].key) == 0)
                return true;
        return false;
    }

    // Adds the given key/value pair to the dictionary.  Returns
// false if the dictionary is full, or if the key is a duplicate.
// Returns true if addition succeeded.
    public boolean add(K key, V value){
        if (contains(key) || isFull()) //Checks if dictionary is full or has duplicated terms
            return false;
        if (isEmpty()) {
            arr[0] = new DicArr<K,V>(key, value); //If empty start here
        }
        int track = bin(key, 0, asize-1);
        int i;
        for (i = asize-1; i >= track; i--) //Add terms to dictionary
            arr[i+1] = arr[i];
        arr[track] = new DicArr<>(key, value);
        asize++;
        modCount++;
        return true;
    }
        //Searches through the dictionary
    private int bin (K key, int low, int high) {
        if(high < low)
            return low;

        int midd = (low+high) >> 1;
        if((key).compareTo(arr[midd].key) <= 0)
            return bin(key, low, midd-1);
        return bin(key, midd+1, high);
    }

    // Deletes the key/value pair identified by the key parameter.
// Returns true if the key/value pair was found and removed,
// otherwise false.
    public boolean delete(K key){
        if(isEmpty())
            return false;
        if(contains(key) == false) //Checks if dictionary is empty or has duplicated terms
            return false;
        else {
            int track = bin(key, 0, asize-1);
            arr[track].value = null;        //Sets key and value to null
            arr[track].key = null;
            for (int i = track; i < asize; i++)     //Deletes terms accordingly
                arr[i] = arr[i+1];
            asize--;
            modCount++;
            return true;
        }
    }

    // Returns the value associated with the parameter key.  Returns
// null if the key is not found or the dictionary is empty.
    public V getValue(K key) {
        if (contains(key) || isEmpty()) {
            int track = bin(key, 0, asize - 1);
            return arr[track].value;
        }
        return null;
    }

    // Returns the key associated with the parameter value.  Returns
// null if the value is not found in the dictionary.  If more
// than one key exists that matches the given value, returns the
// first one found.
    public K getKey(V value){
        for(int i = 0; i <= tSize; i++) {
            if((arr[i].value).equals(value))
                return arr[i].key;
        }
        return null;
    }

    // Returns the number of key/value pairs currently stored
// in the dictionary
    public int size(){
        return asize;
    }

    // Returns true if the dictionary is at max capacity
    public boolean isFull(){
        if(asize == tSize)
            return true;
        return false;

    }

    // Returns true if the dictionary is empty
    public boolean isEmpty(){
        if(asize == 0)
            return true;
        return false;
    }

    // Returns the Dictionary object to an empty state.
    public void clear(){
        asize = 0;
        modCount = 0;
        modCount++;
    }

    // Returns an Iterator of the keys in the dictionary, in ascending
// sorted order.  The iterator must be fail-fast.
    public Iterator<K> keys(){
        return new KeyIteratorHelper<K>();
    }

    // Returns an Iterator of the values in the dictionary.  The
// order of the values must match the order of the keys.
// The iterator must be fail-fast.
    public Iterator<V> values(){
        return new ValueIteratorHelper<V>();
    }

    abstract class IteratorHelper<E> implements Iterator<E>{
        protected DicArr<K,V>[] nodes;
        protected int index;
        protected long modCheck;
        protected int j;

        public IteratorHelper() {
            nodes = new DicArr[tSize];
            index = 0;
            j = 0;
            modCheck = modCount;
            for(int i = 0; i < tSize; i++)
                nodes[j++] = arr[i];
        }

        public boolean hasNext(){
            if(modCheck != modCount)
                throw new ConcurrentModificationException();
            return index < asize;
        }

        public abstract E next();

        public void remove(){
            throw new UnsupportedOperationException();
        }
    }

    class KeyIteratorHelper<K> extends IteratorHelper<K> {
        public KeyIteratorHelper() {
            super();
        }
        public K next(){
            return (K) nodes[index++].key;
        }
    }

    class ValueIteratorHelper<V> extends IteratorHelper<V> {
        public ValueIteratorHelper() {
            super();
        }
        public V next(){
            return (V) nodes[index++].value;
        }
    }

}
