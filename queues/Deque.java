/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.StdOut;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first; // link to least recently added node
    private Node last; // link to most recently added node
    private int N; // number of items on the queue

    private class Node { // nested class to define nodes
        Item item;
        Node next;
        Node prev;
    }

    // construct an empty deque
    public Deque() {

    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() {
        return N;
    }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (oldfirst != null) oldfirst.prev = first;
        else last = first;
        N++;
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty()) first = last;
        else if (oldlast != null) oldlast.next = last;
        N++;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Remove method at an empty deque");
        Item item = first.item;
        first = first.next;
        N--;
        if (isEmpty()) last = null;
        else first.prev = null;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Remove method at an empty deque");
        Item item = last.item;
        last = last.prev;
        N--;
        if (last == null) first = null;
        else last.next = null;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new UnsupportedOperationException("This method is not supported!");
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No next element");
            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        Deque<String> deque = new Deque<String>();
        StdOut.println(deque.isEmpty());
        deque.addFirst("one");
        deque.addLast("two");
        deque.addFirst("zero");
        deque.addLast("three");
        StdOut.println(deque.isEmpty());
        StdOut.println(deque.size());
        for (String s : deque) {
            StdOut.println(s);
        }
        deque.removeLast();
        deque.removeLast();
        deque.removeLast();
        deque.removeFirst();
        StdOut.println(deque.size());
        StdOut.println(deque.isEmpty());
        deque.addLast("one");
        deque.addLast("two");
        StdOut.println(deque.size());
        for (String s : deque) {
            StdOut.println(s);
        }
        /* deque.addFirst("one");
        deque.addLast("two");
        deque.addFirst("zero");
        deque.addLast("three");
        StdOut.println(deque.size());
        for (String s : deque) {
            StdOut.println(s);
        }
        StdOut.println(deque.removeFirst());
        StdOut.println("====================");
        for (String s : deque) {
            StdOut.println(s);
        }
        StdOut.println("====================");
        StdOut.println(deque.removeLast());
        StdOut.println("====================");
        for (String s : deque) {
            StdOut.println(s);
        }
        StdOut.println("====================");
        StdOut.println(deque.removeLast());
        StdOut.println("====================");
        for (String s : deque) {
            StdOut.println(s);
        }
        StdOut.println("====================");
        StdOut.println(deque.removeLast());
        StdOut.println("====================");
        for (String s : deque) {
            StdOut.println(s);
        }
        StdOut.println("===================="); */
    }

}
