//Samuel Akinmulero
//CS310 Riggins
//cssc0900
//04-29-17
//Program 3: Binary Search Tree
//compiles FASHO


package data_structures;

import java.util.ConcurrentModificationException;
import java.util.Iterator;

public class BinarySearchTree<K extends Comparable<K>,V> implements DictionaryADT<K,V> {
    protected class Node<K, V> {
        protected K key;
        protected V value;
        protected Node<K, V> leftChild;
        protected Node<K, V> rightChild;

        public Node(K k, V v) {
            key = k;
            value = v;
            leftChild = rightChild = null;
        }
    }

    protected Node<K, V> root;
    protected K foundKey;
    protected int currSize;
    protected int modCount;

    public BinarySearchTree() {
        root = null;
        currSize = 0;
        modCount = 0;
    }

    // Returns true if the dictionary has an object identified by
// key in it, otherwise false.
    public boolean contains(K key) {
        if (isEmpty()) return false;
        return contains(key, root);
    }

    protected boolean contains(K k, Node<K, V> n) {
        if (n == null)
            return false;
        if (((Comparable<K>) k).compareTo((K) n.key) == 0)
            return true;
        else if (((Comparable<K>) k).compareTo((K) n.key) < 0)
            return contains(k, n.leftChild); // go left
        else
            return contains(k, n.rightChild); //go right
    }


    // Adds the given key/value pair to the dictionary.  Returns
// false if the dictionary is full, or if the key is a duplicate.
// Returns true if addition succeeded.
    public boolean add(K key, V value) {
        if(contains(key) || isFull())
            return false;
        if (root == null)
            root = new Node<K, V>(key, value);
        else
            insert(key, value, root, null, false);
        currSize++;
        modCount++;
        return true;
    }

    protected void insert(K k, V v, Node<K, V> n, Node<K, V> parent, boolean wasLeft) {
        if (n == null)
            if (wasLeft) parent.leftChild = new Node<K, V>(k, v);
            else parent.rightChild = new Node<K, V>(k, v);
        else if (((Comparable<K>) k).compareTo((K) n.key) < 0)
            insert(k, v, n.leftChild, n, true);
        else
            insert(k, v, n.rightChild, n, false);
    }

    // Deletes the key/value pair identified by the key parameter.
// Returns true if the key/value pair was found and removed,
// otherwise false.
    public boolean delete(K key) {
        if (isEmpty()) return false;
        if (!delete(key, root, null, false))
            return false;
        currSize--;
        modCount++;
        return true;
    }

    private boolean delete(K k, Node<K, V> n, Node<K, V> parent, boolean wasL) {
        if (n == null) return false;
        else if (((Comparable<K>) k).compareTo((K) n.key) < 0) //go left to look
            return delete(k, n.leftChild, n, true);
        else if (((Comparable<K>) k).compareTo((K) n.key) > 0) //go right to look
            return delete(k, n.rightChild, n, false);
            // 0 Children
        else {
         if (n.leftChild == null && n.rightChild == null) {
             if(n == root)
                 root = null;
             else if (wasL)
                 parent.leftChild = null; //Set parents children to null
             else parent.rightChild = null;
         }
                // 1 Child
            else if (n.rightChild == null) {
             if(n == root)
                 root = n.leftChild;
             else if (wasL)
                 parent.leftChild = n.leftChild;
             else
             parent.rightChild = n.leftChild;// Replace the node's left child
         }
            else if (n.leftChild == null) { // with parent's right child & vice versa
             if(n == root)
                 root = n.rightChild;
             else if(wasL)
                 parent.leftChild = n.rightChild;
             else
                parent.leftChild = n.rightChild;
         }
                // 2 Children
            else {
                Node<K, V> tmp = getSuccessor(n.rightChild);
                n.key = tmp.key;
                n.value = tmp.value;
                tmp.key = k;
                return delete(k,n.rightChild,n,false);
            }
        }
        return true;
    }

    private Node<K, V> getSuccessor(Node<K, V> n) {
      if(n.leftChild == null)
          return n;
      return getSuccessor(n.leftChild);
    }

    // Returns the value associated with the parameter key.  Returns
// null if the key is not found or the dictionary is empty.
    public V getValue(K key) {
        return find(key, root);
    }

    private V find(K key, Node<K, V> n) {
        if (n == null) return null;
        if (((Comparable<K>) key).compareTo(n.key) < 0)
            return find(key, n.leftChild);  //go to the left
        if (((Comparable<K>) key).compareTo(n.key) > 0)
            return find(key, n.rightChild); //go to the right
        return (V) n.value;                 // found!
    }

    // Returns the key associated with the parameter value.  Returns
// null if the value is not found in the dictionary.  If more
// than one key exists that matches the given value, returns the
// first one found.
    public K getKey(V value) {
        foundKey = null;
        findKey(root,value);
        return foundKey; //return foundKey;
    }

    private void findKey(Node<K, V> n, V value) {
        if (n == null) return;
            findKey(n.leftChild, value);
            if(((Comparable<V>)value).compareTo(n.value) == 0)
                foundKey = n.key;
            findKey(n.rightChild, value);
    }

    // Returns the number of key/value pairs currently stored
// in the dictionary
    public int size(){
        return currSize;
    }

    // Returns true if the dictionary is at max capacity
    public boolean isFull(){
        return false;
    }

    // Returns true if the dictionary is empty
    public boolean isEmpty(){
        if(root == null)
            return true;
        return false;
    }

    // Returns the Dictionary object to an empty state.
    public void clear(){
        root = null;
        currSize=0;
        modCount=0;
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
        protected Node<K,V> [] nodes ;
        protected int index;
        protected long modCheck;
        protected int j = 0;

        public IteratorHelper(){
            nodes = new Node[currSize];
            index = 0;
            modCheck = modCount;
            inOrderFill(root);
        }

        private void inOrderFill(Node<K,V> n){
            if(n == null) return;
            inOrderFill(n.leftChild);
            nodes[j++] = n;
            inOrderFill(n.rightChild);
        }

        public boolean hasNext(){
            if(modCheck != modCount)
                throw new ConcurrentModificationException();
            return index < currSize;
        }

        public abstract E next();

        public void remove(){
            throw new UnsupportedOperationException();
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