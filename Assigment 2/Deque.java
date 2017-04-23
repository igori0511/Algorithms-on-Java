import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {

    private Node first = null, last = null;
    private int n = 0;

    private class Node {
        private Item item;
        private Node prev;
        private Node next;
    }

    public boolean isEmpty() {
        return n == 0;
    }

    public int size() {
        return n;
    }

    public void addFirst(Item item) {

        isItemNull(item);

        isItemNull(item);

        Node newNode = new Node();
        newNode.item = item;
        Node oldNode = first;

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            first = newNode;
            newNode.next = oldNode;
            oldNode.prev = newNode;
        }
        ++n;
    }

    public void addLast(Item item) {

        isItemNull(item);

        Node newNode = new Node();
        newNode.item = item;
        Node oldNode = last;

        if (isEmpty()) {
            first = newNode;
            last = newNode;
        } else {
            last = newNode;
            newNode.prev = oldNode;
            oldNode.next = newNode;
        }
        ++n;
    }

    public Item removeFirst() {

        noElemExp();

        Item item = first.item;
        Node oldNode = first;
        first = first.next;
        oldNode.next = null;

        --n;

        if (!isEmpty()) {
            first.prev = null;
        } else {
            last = null;
        }

        return item;
    }

    public Item removeLast() {

        noElemExp();

        Item item = last.item;
        Node oldNode = last;
        last = last.prev;
        oldNode.prev = null;

        --n;

        if (!isEmpty()) {
            last.next = null;
        } else {
            first = null;
        }

        return item;
    }

    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item> {
        private Node current;

        public DequeIterator() {
            current = first;
        }

        public boolean hasNext() {
            return current != null;
        }

        public void remove() {
            throw new java.lang.UnsupportedOperationException("Remove not supported");
        }

        public Item next() {

            if (!hasNext())
                throw new java.util.NoSuchElementException("No more elements to return");

            Item item = current.item;
            current = current.next;

            return item;
        }

    }

    private void isItemNull(Item item) {
        if (item == null)
            throw new NullPointerException("Cannot add null to the deque");
    }

    private void noElemExp() {
        if (isEmpty())
            throw new NoSuchElementException("The deque is empty");
    }

    public static void main(String[] args) {

    }
}
