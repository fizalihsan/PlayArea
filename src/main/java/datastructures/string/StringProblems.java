package datastructures.string;

/**
 * Comment here about the class
 * User: Fizal
 * Date: 1/1/14
 * Time: 6:50 PM
 */
public class StringProblems {

    /**
     * Replaces the given character 'match' with 'replacement' in the 'input' string. Scans from right-to-left for O(n) efficiency.
     * Left-to-right would lead to O(n^2).
     *
     * @param input
     * @param match
     * @param replacement
     * @return
     */
    static String replaceAll(String input, char match, String replacement) {
        char[] inputChars = input.toCharArray();
        int count = 0;

        for (char inputChar : inputChars) {
            if (inputChar == match) count++;
        }

        if (count > 0) {
            char[] replaceChars = replacement.toCharArray();
            int newLength = inputChars.length + (count * (replaceChars.length - 1));
            char[] resultChars = new char[newLength];

            int i = inputChars.length - 1;
            while (i >= 0) {
                if (inputChars[i] == match) {
                    for (int j = replaceChars.length - 1; j >= 0; j--) {
                        resultChars[--newLength] = replaceChars[j];
                    }
                } else {
                    resultChars[--newLength] = inputChars[i];
                }
                i--;
            }
            return new String(resultChars);
        }
        return input;
    }

    //TODO implement this http://en.wikipedia.org/wiki/Knuth%E2%80%93Morris%E2%80%93Pratt_algorithm
    static void findRepeatingPattern() {

    }
}
