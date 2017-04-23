import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdRandom;

@SuppressWarnings("unchecked")
public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] q = (Item[]) new Object[1];
    private int n = 0;

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void enqueue(Item item) {

        if (item == null)
            throw new java.lang.NullPointerException("Bad input, null item");

        if (n == q.length)
            resize(2 * q.length);

        q[n++] = item;

    }

    public Item dequeue() {
        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");

        int index = StdRandom.uniform(n);
        int swapIndex = --n;

        Item item = q[index];
        q[index] = null;

        Item temp = q[swapIndex];
        q[swapIndex] = q[index];
        q[index] = temp;

        if (n > 0 && n == q.length / 4)
            resize(q.length / 2);

        return item;
    }

    public Item sample() {

        if (isEmpty())
            throw new NoSuchElementException("Queue underflow");

        int index = StdRandom.uniform(n);

        return q[index];
    }

    public Iterator<Item> iterator() {
        return new QueueIterator();
    }

    private void resize(int capacity) {

        Item[] copy = (Item[]) new Object[capacity];

        for (int i = 0; i < n; i++) {
            copy[i] = q[i];
        }

        q = copy;

    }

    private class QueueIterator implements Iterator<Item> {
        private Item[] indexArray;
        private int index;
        private int size;
        private int swapIndex;

        private QueueIterator() {
            size = n;
            indexArray = (Item[]) new Object[size];

            for (int i = 0; i < size; i++) {
                indexArray[i] = q[i];

            }

        }

        public boolean hasNext() {
            return size > 0;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("Remove not supported");
        }

        public Item next() {

            if (!hasNext())
                throw new java.util.NoSuchElementException("No more elements to return");

            index = StdRandom.uniform(size);
            swapIndex = --size;

            Item item = indexArray[index];

            indexArray[index] = null;

            Item temp = indexArray[swapIndex];
            indexArray[swapIndex] = indexArray[index];
            indexArray[index] = temp;

            return item;
        }
    }

}
