package year2025.Day02;

import java.util.List;

public class Day02Part2 {
    public static String solve(List<Long> productIDs) {
        long sum = 0;
        for (Long p : productIDs) {
            if (isInValidProductIDPart2(p)) sum += p;
        }
        return String.valueOf(sum);
    }

    private static boolean isInValidProductIDPart2(long productID) {
        String pID = productID + "";
        int length = pID.length();

        for (int i = 2; i <= length; i++) {
            boolean inValidProductID = true;
            int sectionLength = pID.length() / i;

            boolean end = false;
            for (int j = 2; sectionLength * j <= length; j++) {
                end = false;
                String section = pID.substring(sectionLength * (j - 1), sectionLength * j);
                String previousSection = pID.substring(sectionLength * (j - 2), sectionLength * (j - 1));

                if (sectionLength * j == length) end = true;
                if (!section.equals(previousSection)) {
                    inValidProductID = false;
                    break;
                }
            }
            if (inValidProductID && end) return true;
        }
        return false;
    }
}
