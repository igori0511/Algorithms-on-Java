import java.util.Arrays;

public class CircularSuffixArray {

    private String input;

    private Integer[] index;

    public CircularSuffixArray(String s) {
        if (s == null || s.equals(""))
            throw new IllegalArgumentException("Can't get suffix array for empty string!");
        input = s;
        index = new Integer[length()];
        for (int i = 0; i < index.length; i++)
            index[i] = i;

        // algorithm: not to store strings; just compare them using number of shifts
        Arrays.sort(index, (first, second) -> {
            // get start indexes of chars to compare
            int firstIndex = first;
            int secondIndex = second;
            // for all characters
            for (int i = 0; i < input.length(); i++) {
                // if out of the last char then start from beginning
                if (firstIndex > input.length() - 1)
                    firstIndex = 0;
                if (secondIndex > input.length() - 1)
                    secondIndex = 0;
                // if first string > second
                if (input.charAt(firstIndex) < input.charAt(secondIndex))
                    return -1;
                else if (input.charAt(firstIndex) > input.charAt(secondIndex))
                    return 1;
                // watch next chars
                firstIndex++;
                secondIndex++;
            }
            // equal strings
            return 0;
        });
    }

    public int length() {
        return input.length();
    }

    public int index(int i) {
        return index[i];
    }

    public static void main(String[] args) {
        CircularSuffixArray csa = new CircularSuffixArray("AAA\n");
        for (int i = 0; i < csa.length(); i++) System.out.println(csa.index(i));
        System.out.println();
        csa = new CircularSuffixArray("ABRACADABRA!");
        for (int i = 0; i < csa.length(); i++) System.out.println(csa.index(i));
    }

}
