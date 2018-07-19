//Samuel Akinmulero
//CS310 Riggins
//cssc0900
//04-29-17
//Program 3: Unordered List

package data_structures;

import java.util.Iterator;

//An unordered linked list you need a tail pointer.
public class UnorderedList<E extends Comparable<E>> implements Iterable<E> {
    private Node<E> head; // Create a head pointer
    private Node<E> tail; // Create a tail pointer
    private int curSize; //Initializes size at current point in the list

    public UnorderedList() {
        head = tail = null;
        curSize = 0;
    }

    private class Node<E> {
        E data;
        Node<E> next;

        public Node(E obj) {
            data = obj;
            next = null;
        }
    }


    public void addFirst(E obj) {
        Node<E> newNode = new Node<E>(obj);
        if (head == null)
            tail = newNode;
        newNode.next=head;
        head = newNode;
        curSize++;
    }
    public void add(E obj) {
        Node<E> newNode = new Node<E> (obj);
        if(head == null)
            head = tail = newNode;
        else{
            tail.next = newNode;
            tail = newNode;
        }
        curSize++;
    }

    public E removeFirst() {
        if(head==null)
            return null;

        E obj = head.data;
        if(head==tail)
            head = tail = null;
        else
            head = head.next;
        curSize--;
        return obj;
    }
    public E removeLast() {
        if(isEmpty())
            return null;
        if(head==tail)
            return removeFirst();
        E obj = tail.data;
        Node<E> current = head, previous = null;
        while(current != null){
            previous = current;
            current = current.next;
        }
        previous.next = null;
        tail = previous;
        curSize--;
        return obj;
    }

    public E remove(E obj) {
        if (isEmpty())
            return null;
        E tmpr;
        if (head == tail) {
            tmpr = head.data;
            head = head.next;
            curSize--;
            return tmpr;
        }
        Node<E> curr = head, prev = null, minPrev = null, minCurr = head;
        tmpr = head.data;
        while (curr != null) {
            if (tmpr.compareTo(curr.data) > 0) {
                tmpr = curr.data;
                minPrev = prev;
                minCurr = curr;
            }
            prev = curr;
            curr = curr.next;
        }
        if (minCurr == head)
            head = head.next;
        else if (minCurr == tail) {
            minPrev.next = null;
            tail = minPrev;
        }
        else
            minPrev.next = minCurr.next;
        if (head == null)
            tail = null;
        curSize--;
        return tmpr;
    }

    public E peek() {
        Node<E> curr = head;
        E tmpr = head.data;
        while(curr.next != null) {
            if((tmpr).compareTo(curr.data) > 0)
                tmpr = curr.data;
            curr = curr.next;
        }
        return tmpr;
    }


    public E find(int locate) {
        Node<E> curr = head;
        for(int i = 0; i<=locate; i++)
            if(i == locate)
                return curr.data;
        curr = curr.next;
        return null;
    }

    public boolean isEmpty() {
        return head==null;
    }

    public boolean isFull() {
        return false;
    }

    public int size() {
        return curSize;
    }

    public void clear() {
        head = tail = null;
        curSize = 0;
    }

    public boolean contains(E obj) {
        Node<E> current = head;
        while(current!=null){
            if(((Comparable<E>)current.data).compareTo(obj)==0)
                return true;
            current = current.next;
        }
        return false;
    }
    public Iterator<E> iterator() {
        return new IteratorHelper();
    }

    class IteratorHelper implements Iterator<E>{
        Node<E> index;
        public IteratorHelper(){
            index = head;
        }

        public boolean hasNext(){ //Returns true if there is at least one additional element in the sequence
            return index!=null;
        }
        public E next(){	//Returns next element in the sequence.
            if(!hasNext()){ //If there is not an additional element, nothing is returned
                return null;
            }
            E obj = index.data;
            index = index.next;
            return obj;
        }
        public void remove() { //Removes from the collection the element returned by the most recent call to next()
            E obj = index.data;
        }
    }
}

