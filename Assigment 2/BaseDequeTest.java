import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import com.pronceton.stacks.and.aueues.Deque;
import com.pronceton.stacks.and.aueues.RandomizedQueue;

public class BaseDequeTest {

    protected List<Integer> convertDequeIteratorToList(Deque<Integer> testDeque) {
        Iterable<Integer> iterable = () -> testDeque.iterator();
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toCollection(ArrayList::new));
    }

    protected List<Integer> convertDequeIteratorToList(RandomizedQueue<Integer> testDeque) {
        Iterable<Integer> iterable = () -> testDeque.iterator();
        return StreamSupport.stream(iterable.spliterator(), false).collect(Collectors.toCollection(ArrayList::new));
    }

}
