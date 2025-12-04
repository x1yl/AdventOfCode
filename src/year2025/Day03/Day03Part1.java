package year2025.Day03;

import java.util.List;

import static year2025.Day03.utils.getLargestCombination;

public class Day03Part1 {
    public static String solve(List<String> batteriesList) {
        long partOneAnswer = 0;
        for (String batteries : batteriesList) {
            partOneAnswer += getLargestCombination(batteries, 2);
        }
        return String.valueOf(partOneAnswer);
    }

}
