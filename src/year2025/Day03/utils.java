package year2025.Day03;

public class utils {
    public static long getLargestCombination(String batteries, int count) {
        StringBuilder result = new StringBuilder();
        int startIndex = 0;

        for (int remaining = count; remaining > 0; remaining--) {
            int endIndex = batteries.length() - remaining;
            char maxDigit = '0';
            int maxIndex = startIndex;

            for (int i = startIndex; i <= endIndex; i++) {
                if (batteries.charAt(i) > maxDigit) {
                    maxDigit = batteries.charAt(i);
                    maxIndex = i;
                }
            }

            result.append(maxDigit);
            startIndex = maxIndex + 1;
        }

        return Long.parseLong(result.toString());
    }
}
