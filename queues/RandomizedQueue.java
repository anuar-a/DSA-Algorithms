import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a; // stack items
    private int N = 0; // number of items

    // construct an empty randomized queue
    public RandomizedQueue() {
        a = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() {
        return N;
    }

    // Move randomized queue to a new randomized queue of size max.
    private void resize(int max) {
        Item[] temp = (Item[]) new Object[max];
        for (int i = 0; i < N; i++)
            temp[i] = a[i];
        a = temp;
    }

    private void swap(int i, int j) {
        Item temp = a[i];
        a[i] = a[j];
        a[j] = temp;
    }

    // Add item to top of randomized queue.
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("Argument is null");
        }
        if (N == a.length) resize(2 * a.length);
        a[N++] = item;
    }

    // Remove item from top of randomized queue.
    public Item dequeue() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Remove method at an empty deque");
        swap(StdRandom.uniform(N), N - 1);
        Item item = a[--N];
        a[N] = null; // Avoid loitering (see text).
        if (N > 0 && N == a.length / 4) resize(a.length / 2);
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty())
            throw new java.util.NoSuchElementException("Sample method at an empty deque");
        return a[StdRandom.uniform(N)];
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() {
        StdRandom.shuffle(a, 0, N);
        return new RandomIterator();
    }

    // Support LIFO iteration.
    private class RandomIterator implements Iterator<Item> {
        private int i = N;

        public boolean hasNext() {
            return i > 0;
        }

        public Item next() {
            if (!hasNext()) throw new NoSuchElementException("No next element");
            return a[--i];
        }

        public void remove() {
            throw new UnsupportedOperationException("This method is not supported!");
        }
    }

    // unit testing (required)
    public static void main(String[] args) {
        RandomizedQueue<String> rqueue = new RandomizedQueue<String>();
        rqueue.enqueue("one");
        rqueue.enqueue("two");
        rqueue.enqueue("three");
        rqueue.enqueue("four");
        for (String s : rqueue) {
            StdOut.println(s);
        }
        StdOut.println(rqueue.dequeue());
        StdOut.println(rqueue.dequeue());
        StdOut.println(rqueue.dequeue());
        StdOut.println(rqueue.dequeue());
        StdOut.println(rqueue.isEmpty());
        StdOut.println(rqueue.size());
        rqueue.enqueue("five");
        rqueue.enqueue("six");
        for (String s : rqueue) {
            StdOut.println(s);
        }
        StdOut.println(rqueue.isEmpty());
        StdOut.println(rqueue.size());
    }


}
