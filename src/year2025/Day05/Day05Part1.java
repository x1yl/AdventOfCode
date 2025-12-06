package year2025.Day05;

import java.util.ArrayList;

public class Day05Part1 {
    public static int solve(ArrayList<String> freshProducts, ArrayList<Long> products) {
        int count = 0;
        for (Long product : products) {
            for (String freshProduct : freshProducts) {
                String[] parts = freshProduct.split("-");
                long first = Long.parseLong(parts[0].trim());
                long second = Long.parseLong(parts[1].trim());
                if (product >= first && product <= second) {
                    count++;
                    break;
                }
            }
        }
        return count;
    }
}
