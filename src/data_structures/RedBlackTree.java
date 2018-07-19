//Samuel Akinmulero
//CS310 Riggins
//cssc0900
//04-29-17
//Program 3: Red Black Tree
//WORKS FASHO
package data_structures;

import java.util.Map;
import java.util.TreeMap;
import java.util.Iterator;

public class RedBlackTree<K extends Comparable<K>,V> implements DictionaryADT<K,V>{
    class RBT<K, V> implements Comparable<RBT<K, V>> {
        K key;
        V value;
        public RBT (K k, V v) {
            this.key = k;
            this.value = v;
        }

        public int compareTo(RBT<K, V> n) {
            return (((Comparable<K>)n.key).compareTo(this.key));
        }
    }

    TreeMap<K,V> branch;
    int rbsize;

    public RedBlackTree() {
        branch = new TreeMap<K,V>();
        rbsize = 0;
    }

    // Returns true if the dictionary has an object identified by
// key in it, otherwise false.
    public boolean contains(K key){
        return branch.containsKey(key);
    }

    // Adds the given key/value pair to the dictionary.  Returns
// false if the dictionary is full, or if the key is a duplicate.
// Returns true if addition succeeded.
    public boolean add(K key, V value) {
        if (contains(key) || isFull())
            return false;
        return branch.put(key, value) != null;
    }

    // Deletes the key/value pair identified by the key parameter.
// Returns true if the key/value pair was found and removed,
// otherwise false.
    public boolean delete(K key) {
        return branch.remove(key) != null;
    }

    // Returns the value associated with the parameter key.  Returns
// null if the key is not found or the dictionary is empty.
    public V getValue(K key){
        return branch.get(key);
    }

    // Returns the key associated with the parameter value.  Returns
// null if the value is not found in the dictionary.  If more
// than one key exists that matches the given value, returns the
// first one found.
    public K getKey(V value) {
        Iterator<Map.Entry<K,V>> iter = branch.entrySet().iterator();
        while(iter.hasNext()) {
            Map.Entry<K,V> tmp = iter.next();
            if (((Comparable<K>)tmp.getValue()).compareTo((K) value) == 0)
                return tmp.getKey();
        }
        return null;
    }

    // Returns the number of key/value pairs currently stored
// in the dictionary
    public int size(){
        return rbsize;
    }

    // Returns true if the dictionary is at max capacity
    public boolean isFull(){
        return false;//Red Black Trees never reach max capacity
    }

    // Returns true if the dictionary is empty
    public boolean isEmpty(){
        if(branch.size() == 0)
            return true;
        return false;
    }

    // Returns the Dictionary object to an empty state.
    public void clear(){
        branch.clear();
    }

    // Returns an Iterator of the keys in the dictionary, in ascending
// sorted order.  The iterator must be fail-fast.
    public Iterator<K> keys(){
        return branch.keySet().iterator();
    }

    // Returns an Iterator of the values in the dictionary.  The
// order of the values must match the order of the keys.
// The iterator must be fail-fast.
    public Iterator<V> values(){
        return branch.values().iterator();
    }


}