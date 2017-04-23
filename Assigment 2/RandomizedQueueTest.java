import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import com.pronceton.stacks.and.aueues.RandomizedQueue;

public class RandomizedQueueTest extends BaseDequeTest {

    @Test
    public void randomizedQueueEnqueueTest() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        int numberOfItemsInqueue = 5;
        addItemsToQueue(numberOfItemsInqueue, rq);
        List<Integer> lst = convertDequeIteratorToList(rq);
        assertTrue(checkQueueNotInOrder(lst));

    }

    @Test
    public void randomizedQueueDequeueTest() {
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        int numberOfItemsInqueue = 5;
        addItemsToQueue(numberOfItemsInqueue, rq);
        List<Integer> expectedItems = Arrays.asList(1, 2, 3, 4, 5);
        for (int item = 0; item < rq.size(); item++) {
            assertTrue(expectedItems.contains(rq.dequeue()));
        }
    }

    @Test
    public void singleIteratorWithAddFirstTest() {
        int numberOfItems = 5;
        RandomizedQueue<Integer> rq = new RandomizedQueue<>();
        addItemsToQueue(numberOfItems, rq);

        List<Integer> listFromIterator = convertDequeIteratorToList(rq);
        List<Integer> secondListFromIterator = convertDequeIteratorToList(rq);
        assertNotEquals(listFromIterator.hashCode(), secondListFromIterator.hashCode());
        assertNotEquals(System.identityHashCode(listFromIterator), System.identityHashCode(secondListFromIterator));

    }

    private boolean checkQueueNotInOrder(List<Integer> lst) {
        for (int i = 0; i < lst.size() - 1; i++) {
            if (!(lst.get(i) < lst.get(i + 1)) || !(lst.get(i) > lst.get(i + 1))) {
                return true;
            }
        }
        return false;
    }

    private void addItemsToQueue(int n, RandomizedQueue<Integer> queue) {
        for (int item = 1; item <= n; item++) {
            queue.enqueue(item);
        }
    }

}
