import edu.princeton.cs.algs4.BinaryStdIn;
import edu.princeton.cs.algs4.BinaryStdOut;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;


public class MoveToFront {

    private static final int CHAR_BITS = 8;

    private static final int ALPHABET_SIZE = 256;

    /**
     * Apply move-to-front encoding, reading from standard input and writing to standard output
     */
    public static void encode() {
        List<Character> moveToFront = createANSIList();
        while (!BinaryStdIn.isEmpty()) {
            char curChar = BinaryStdIn.readChar();
            int alphabetPosition = 0;
            Iterator<Character> moveToFrontIterator = moveToFront.iterator();
            while (moveToFrontIterator.hasNext()) {
                if (moveToFrontIterator.next().equals(Character.valueOf(curChar))) {
                    BinaryStdOut.write(alphabetPosition, CHAR_BITS);
                    char toFront = moveToFront.get(alphabetPosition);
                    moveToFront.remove(alphabetPosition);
                    moveToFront.add(0, toFront);
                    break;
                }
                alphabetPosition++;
            }
        }
        BinaryStdOut.close();
    }

    /**
     * Apply move-to-front decoding, reading from standard input and writing to standard output
     */
    public static void decode() {
        List<Character> moveToFront = createANSIList();
        while (!BinaryStdIn.isEmpty()) {
            int curCharPosition = BinaryStdIn.readChar();
            BinaryStdOut.write(moveToFront.get(curCharPosition), CHAR_BITS);
            char toFront = moveToFront.get(curCharPosition);
            moveToFront.remove(curCharPosition);
            moveToFront.add(0, toFront);
        }
        BinaryStdOut.close();
    }

    private static List<Character> createANSIList() {
        List<Character> ansiList = new LinkedList<Character>();
        for (int alphabetPosition = 0; alphabetPosition < ALPHABET_SIZE; alphabetPosition++)
            ansiList.add((char) alphabetPosition);
        return ansiList;
    }

    public static void main(String[] args) {
        if (args.length == 0)
            throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
        if (args[0].equals("-"))
            encode();
        else if (args[0].equals("+"))
            decode();
        else
            throw new java.lang.IllegalArgumentException("Usage: input '+' for encoding or '-' for decoding");
    }
}
