import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Permutation {
    public static void main(String[] args) {
        int numSub = Integer.parseInt(args[0]);

        RandomizedQueue<String> randomSet = new RandomizedQueue<String>();

        while (!StdIn.isEmpty()) {
            String str = StdIn.readString();
            randomSet.enqueue(str);
        }

        for (int i = 0; i < numSub; i++) {
            StdOut.println(randomSet.dequeue());
        }

    }
}