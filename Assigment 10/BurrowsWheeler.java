import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Arrays;

public class BurrowsWheeler {

    private static final int R = 256;

    public static void transform() {

        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();

        CircularSuffixArray csa = new CircularSuffixArray(s);

        for (int i = 0; i < csa.length(); i++)
            if (csa.index(i) == 0) {
                BinaryStdOut.write(i);
                break;
            }

        for (int i = 0; i < s.length(); i++) {
            int idx = (csa.index(i) + csa.length() - 1) % csa.length();
            BinaryStdOut.write(input[idx], 8);
        }

        BinaryStdOut.close();
    }

    public static void inverseTransform() {
        String s = BinaryStdIn.readString();
        char[] input = s.toCharArray();
        char[] sorted = new char[input.length];

        for (int i = 0; i < input.length; i++)
            sorted[i] = input[i];

        Arrays.sort(sorted);

        int[] baseIndex = new int[R];
        int[] next = new int[input.length];

        // First, construct the next array...
        for (int i = 0; i < input.length; i++) {
            next[i] = getNextForChar(sorted[i], input, baseIndex);
        }
        BinaryStdOut.close();
    }

    private static int getNextForChar(char c, char[] input, int[] baseIndex) {
        for (int i = baseIndex[c]; i < input.length; i++) {
            if (input[i] == c) {
                baseIndex[c] = i + 1;
                return i;
            }
        }
        return 0;
    }

    public static void main(String[] args) {
        if (args.length == 0)
            throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
        if (args[0].equals("-"))
            transform();
        else if (args[0].equals("+"))
            inverseTransform();
        else
            throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
    }

}
