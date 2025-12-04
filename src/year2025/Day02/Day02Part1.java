package year2025.Day02;

import java.util.List;

public class Day02Part1 {
    public static String solve(List<Long> productIDs) {
        long sum = 0;
        for (Long p : productIDs) {
            if (isInValidProductID(p)) sum += p;
        }
        return String.valueOf(sum);
    }

    private static boolean isInValidProductID(long productID) {
        String pID = productID + "";
        int halfLength = pID.length() / 2;
        String firstHalf = pID.substring(0, halfLength);
        String secondHalf = pID.substring(halfLength);
        return firstHalf.equals(secondHalf);
    }
}
