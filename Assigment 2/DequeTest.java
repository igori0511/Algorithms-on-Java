import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.junit.Test;

import com.pronceton.stacks.and.aueues.Deque;

public class DequeTest extends BaseDequeTest {

    @Test
    public void addFirstTest() {
        Deque<Integer> testDeque = new Deque<>();
        int numberOfItems = 5;
        addToFirst(testDeque, numberOfItems);
        assertTrue(testDeque.size() == numberOfItems);
    }

    @Test
    public void addLastTest() {
        Deque<Integer> testDeque = new Deque<>();
        int numberOfItems = 5;
        addToLast(testDeque, numberOfItems);
        assertTrue(testDeque.size() == numberOfItems);
    }

    @Test
    public void removeFirst() {
        Deque<Integer> testDeque = new Deque<>();
        int numberOfItems = 5;
        addToFirst(testDeque, numberOfItems);
        for (int expectedItem = numberOfItems; expectedItem > 0; expectedItem--) {
            int itemInDeque = testDeque.removeFirst();
            assertEquals(itemInDeque, expectedItem);
        }

    }

    @Test
    public void removeLast() {
        Deque<Integer> testDeque = new Deque<>();
        int numberOfItems = 5;
        addToLast(testDeque, numberOfItems);
        for (int expectedItem = numberOfItems; expectedItem > 0; expectedItem--) {
            int itemInDeque = testDeque.removeLast();
            assertEquals(itemInDeque, expectedItem);
        }

    }

    @Test
    public void singleIteratorWithAddFirstTest() {
        int numberOfItems = 5;
        Deque<Integer> testDeque = new Deque<>();
        List<Integer> expectedList = Arrays.asList(5, 4, 3, 2, 1);
        addToFirst(testDeque, numberOfItems);

        List<Integer> listFromIterator = convertDequeIteratorToList(testDeque);
        assertEquals(listFromIterator, expectedList);

    }

    @Test
    public void multipleIteratorInstancesWithAddFirstTest() throws InterruptedException {
        int threadPoolCapacity = 500;
        int numberOfItems = 100;
        int threadPoolTerminationTime = 10;
        Deque<Integer> testDeque = new Deque<>();
        List<Integer> expectedList = new ArrayList<>();
        for (int item = numberOfItems; item > 0; item--) {
            expectedList.add(item);
        }
        addToFirst(testDeque, numberOfItems);
        ExecutorService threadPool = simulateMultipleCallsToDequeIterator(threadPoolCapacity, testDeque, expectedList);

        do {
            assertTrue(testDeque.size() == numberOfItems);
        } while (!threadPool.awaitTermination(threadPoolTerminationTime, TimeUnit.SECONDS));

        List<Integer> listFromIterator = convertDequeIteratorToList(testDeque);
        assertEquals(listFromIterator, expectedList);
        assertTrue(testDeque.size() == numberOfItems);
    }

    @Test
    public void multipleIteratorInstancesWithAddLastTest() throws InterruptedException {
        int threadPoolCapacity = 500;
        int numberOfItems = 100;
        int threadPoolTerminationTime = 10;
        Deque<Integer> testDeque = new Deque<>();
        List<Integer> expectedList = new ArrayList<>();
        for (int item = 1; item <= numberOfItems; item++) {
            expectedList.add(item);
        }
        addToLast(testDeque, numberOfItems);
        ExecutorService threadPool = simulateMultipleCallsToDequeIterator(threadPoolCapacity, testDeque, expectedList);

        do {
            assertTrue(testDeque.size() == numberOfItems);
        } while (!threadPool.awaitTermination(threadPoolTerminationTime, TimeUnit.SECONDS));

        List<Integer> listFromIterator = convertDequeIteratorToList(testDeque);
        assertEquals(listFromIterator, expectedList);
        assertTrue(testDeque.size() == numberOfItems);
    }

    @Test
    public void dequeSizeWithRemoveFirstTest() {
        Deque<Integer> testDeque = new Deque<>();
        int numberOfItems = 5;
        addToFirst(testDeque, numberOfItems);
        assertTrue(testDeque.size() == numberOfItems);
        removeAllItemsFromDequeWithRemoveFirst(testDeque);
        assertTrue(testDeque.size() == 0);

    }

    @Test
    public void dequeSizeWithRemoveLastTest() {
        Deque<Integer> testDeque = new Deque<>();
        int numberOfItems = 5;
        addToLast(testDeque, numberOfItems);
        assertTrue(testDeque.size() == numberOfItems);
        removeAllItemsFromDequeWithRemoveLast(testDeque);
        assertTrue(testDeque.size() == 0);

    }

    @Test
    public void singleIteratorWithAddFirstAndAddLastTest() {
        int numberOfItemsAddFirst = 3;
        int numberOfItemsAddLast = 2;
        int expectedLastValue = 2;
        int expectedFirstValue = 3;
        Deque<Integer> testDeque = new Deque<>();
        addToFirst(testDeque, numberOfItemsAddFirst);
        addToLast(testDeque, numberOfItemsAddLast);
        assertEquals((int) testDeque.removeFirst(), expectedFirstValue);
        assertEquals((int) testDeque.removeLast(), expectedLastValue);

    }

    @Test(expected = NoSuchElementException.class)
    public void singleIteratorWithAddFirstAndAddLastWithOneItemInDequeTest() {
        int numberOfItemsAddFirst = 1;
        int expectedFirstValue = 1;
        Deque<Integer> testDeque = new Deque<>();
        addToFirst(testDeque, numberOfItemsAddFirst);
        assertEquals((int) testDeque.removeFirst(), expectedFirstValue);
        testDeque.removeLast();

    }

    @Test(expected = NoSuchElementException.class)
    public void singleIteratorWithAddFirstAndAddLastWithOneItemInDequeSecondTest() {
        int numberOfItemsAddFirst = 1;
        int expectedFirstValue = 1;
        Deque<Integer> testDeque = new Deque<>();
        addToFirst(testDeque, numberOfItemsAddFirst);
        assertEquals((int) testDeque.removeLast(), expectedFirstValue);
        testDeque.removeFirst();

    }

    @Test(expected = NullPointerException.class)
    public void nullItemAddToFirstTest() {
        Deque<Integer> testDeque = new Deque<>();
        testDeque.addFirst(null);
    }

    @Test(expected = NullPointerException.class)
    public void nullItemAddToLastTest() {
        Deque<Integer> testDeque = new Deque<>();
        testDeque.addLast(null);
    }

    @Test(expected = NoSuchElementException.class)
    public void removeItemFromEmptyDequeWithRemoveFirstTest() {
        Deque<Integer> testDeque = new Deque<>();
        testDeque.removeFirst();
    }

    @Test(expected = NoSuchElementException.class)
    public void removeItemFromEmptyDequeWithRemoveLastTest() {
        Deque<Integer> testDeque = new Deque<>();
        testDeque.removeLast();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void callIteratorRemoveMethodTest() {
        Deque<Integer> testDeque = new Deque<>();
        Iterator<Integer> iterator = testDeque.iterator();
        iterator.remove();
    }

    @Test(expected = NoSuchElementException.class)
    public void callIteratorNextOnEmptyDequeTest() {
        Deque<Integer> testDeque = new Deque<>();
        Iterator<Integer> iterator = testDeque.iterator();
        iterator.next();
    }

    private ExecutorService simulateMultipleCallsToDequeIterator(int threadPoolCapacity, Deque<Integer> testDeque,
            List<Integer> expectedList) {
        ExecutorService threadPool = Executors.newFixedThreadPool(threadPoolCapacity);

        for (int i = 0; i < threadPoolCapacity; i++) {
            Runnable task = () -> {
                List<Integer> listFromIterator = convertDequeIteratorToList(testDeque);
                assertEquals(listFromIterator, expectedList);
            };
            threadPool.submit(task);
        }
        threadPool.shutdown();
        return threadPool;
    }

    private void addToFirst(Deque<Integer> testDeque, int numberOfItems) {
        for (int item = 1; item <= numberOfItems; item++) {
            testDeque.addFirst(item);
        }
    }

    private void addToLast(Deque<Integer> testDeque, int numberOfItems) {
        for (int item = 1; item <= numberOfItems; item++) {
            testDeque.addLast(item);
        }
    }

    private void removeAllItemsFromDequeWithRemoveFirst(Deque<Integer> testDeque) {
        while (testDeque.size() > 0) {
            testDeque.removeFirst();
        }
    }

    private void removeAllItemsFromDequeWithRemoveLast(Deque<Integer> testDeque) {
        while (testDeque.size() > 0) {
            testDeque.removeLast();
        }
    }

}
