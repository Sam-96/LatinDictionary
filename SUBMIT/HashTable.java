//Samuel Akinmulero
//CS310 Riggins
//cssc0900
//04-29-17
//Program 3: Hash Table
package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
public class HashTable<K extends Comparable<K>, V> implements DictionaryADT<K,V> {
    class HashBruh<K,V> implements Comparable<HashBruh<K,V>> {
        K key;
        V value;

        public HashBruh(K k, V v) {
            this.key = k;
            this.value = v;
        }

        public int compareTo(HashBruh<K,V> n) {
            return ((Comparable<K>)n.key).compareTo((K)(this.key));
        }
    }

    private UnorderedList<HashBruh<K,V>> [] hash_arr;
    int tableSize, hsize,maxSize;
    long modCount;

    public HashTable(int n) {
        maxSize = n;
        hsize=0;
        tableSize = (int) (maxSize * 1.3f);  //Creates table size
        modCount = 0;
        hash_arr = (UnorderedList<HashBruh<K, V>>[]) new UnorderedList[tableSize];
        for (int i = 0; i < tableSize; i++) //Sorts with UnorderedList
            hash_arr[i] = new UnorderedList<HashBruh<K,V>>();
        }

    // Returns true if the dictionary has an object identified by
// key in it, otherwise false.
    public boolean contains(K key){
        int idx = (key.hashCode() & 0x7FFFFFFF) % tableSize;
        UnorderedList<HashBruh<K,V>> inRan = hash_arr[idx];
        for(HashBruh<K,V> indx : inRan) {
            if(indx.key.equals(key))
                return true;
        }
        return false;
    }

    // Adds the given key/value pair to the dictionary.  Returns
// false if the dictionary is full, or if the key is a duplicate.
// Returns true if addition succeeded.
    public boolean add(K key, V value){
        if(isFull() || contains(key))  //Checks fullness and duplicates
            return false;
        HashBruh<K,V> hashBr = new HashBruh<K,V>(key, value);
        int hashVal = (key.hashCode() & 0x7FFFFFFF) % tableSize; //gets the index of the array position that we will add new item in
        hash_arr[hashVal].add(hashBr);
        hsize++; //increasing array size
        return true;
    }

    // Deletes the key/value pair identified by the key parameter.
// Returns true if the key/value pair was found and removed,
// otherwise false.
    public boolean delete(K key){
        int idx = (key.hashCode() & 0x7FFFFFFF) % tableSize;
        for(HashBruh<K,V> indx : hash_arr[idx]) {
            if(indx.key.equals(key)) {
                hash_arr[idx].remove(indx);
                hsize--; //lowers the size of the array
                return true;
            }
        }
        return false;
    }

    // Returns the value associated with the parameter key.  Returns
// null if the key is not found or the dictionary is empty.
    public V getValue(K key){
        int hashVal = (key.hashCode() &0x7FFFFFFF) % tableSize;

        for(HashBruh<K,V> hashBr : hash_arr[hashVal])
            if((key).compareTo(hashBr.key) == 0)
                return hashBr.value;
        return null;
    }

    // Returns the key associated with the parameter value.  Returns
// null if the value is not found in the dictionary.  If more
// than one key exists that matches the given value, returns the
// first one found.
    public K getKey(V value){
        for(int i = 0; i < tableSize; i++) {
            for(HashBruh<K,V> hashBr : hash_arr[i]) {
                if ((((Comparable<V>)hashBr.value).compareTo(value)) == 0)
                    return hashBr.key;
            }   //end if
        }   //end for
        return null;
    }

    // Returns the number of key/value pairs currently stored
// in the dictionary
    public int size(){
        return hsize;
    }

    // Returns true if the dictionary is at max capacity
    public boolean isFull(){
        if(maxSize == tableSize)
            return true;
        return false;
    }

    // Returns true if the dictionary is empty
    public boolean isEmpty(){
        if(hsize == 0)
            return true;
        return false;
    }

    // Returns the Dictionary object to an empty state.
    public void clear(){
        for(int i=0; i<tableSize; i++)
            hash_arr[i].clear();
            hsize = 0;
    }
//        int i;
//        for(i = 0; i < tableSize; i++) {
//            for (HashBruh<K, V> hashBr : hash_arr[i]) {
//                hashBr.key = null;
//                hashBr.value = null;
//            } //end 2nd for loop
//        } //end 1st for loop
//    }

    // Returns an Iterator of the keys in the dictionary, in ascending
// sorted order.  The iterator must be fail-fast.
    public Iterator<K> keys() {return new KeyIteratorHelper<K>();}

//     Returns an Iterator of the values in the dictionary.  The
// order of the values must match the order of the keys.
// The iterator must be fail-fast.
    public Iterator<V> values() { return new ValueIteratorHelper<V>();}

    abstract class IteratorHelper<E> implements Iterator<E> {
        protected HashBruh<K, V>[] nodes;
        protected int index;
        protected long modCheck;
        protected int j;
        protected int size;

        public IteratorHelper() {
            nodes = new HashBruh[hsize];
            index = 0;
            j = 0;
            modCheck = modCount;
            for (int i = 0; i < tableSize; i++)
                if(hash_arr[i] != null)
                for (HashBruh n : hash_arr[i])
                    if(n != null)
                    nodes[j++] = n;
            nodes = shellSort(nodes);  //Calls shell sort
        }

        public boolean hasNext() {
            if (modCheck != modCount)
                throw new ConcurrentModificationException();
            return index < hsize;
        }

        public abstract E next();

        public void remove() {
            throw new UnsupportedOperationException();
        }
	//Orders using shell sort
        private HashBruh<K, V>[] shellSort(HashBruh<K, V>[] array) {
            HashBruh<K, V>[] n = array;
            int in, out, h = 1;
            HashBruh<K,V> tmp;
            size = n.length;
            while (h <= size / 3) //calculate gaps
                h = h * 3 + 1;
            while (h > 0) {
                for (out = h; out < size; out++) {
                    tmp = n[out];
                    in = out;
                    while (in > h - 1 && n[in - h].compareTo(tmp) <= 0) {
                        n[in] = n[in - h];
                        in -= h;
                    }
                    n[in] = tmp;

                }// end for
                h = (h - 1) / 3;
            } //end while
            return n;
        }
    }

        class KeyIteratorHelper<K> extends IteratorHelper<K>{
            public KeyIteratorHelper() {
                super();
            }
            public K next(){
                return (K) nodes[index++].key;
            }
        }

        class ValueIteratorHelper<V> extends IteratorHelper<V>{
            public ValueIteratorHelper() {
                super();
            }
            public V next(){
                return (V) nodes[index++].value;
            }
        }
    }
