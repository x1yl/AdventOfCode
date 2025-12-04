package year2025.Day03;

import java.util.List;

import static year2025.Day03.utils.getLargestCombination;

public class Day03Part2 {
    public static String solve(List<String> batteriesList) {
        long partTwoAnswer = 0;
        for (String batteries : batteriesList) {
            partTwoAnswer += getLargestCombination(batteries, 12);
        }
        return String.valueOf(partTwoAnswer);
    }

}
