package aoc;

import java.util.*;

public record AocAnswerTracker(AocStorage storage) {

    public record Bounds(OptionalInt min, OptionalInt max) {
    }

    public Bounds bounds(int year, int day, int part) throws Exception {
        var d = storage.loadDay(year, day);
        List<String> list = part == 1 ? d.attempts1 : d.attempts2;

        List<Integer> nums = new ArrayList<>();
        for (String s : list) {
            try {
                nums.add(Integer.parseInt(s));
            } catch (Exception ignore) {
            }
        }

        if (nums.isEmpty()) {
            return new Bounds(OptionalInt.empty(), OptionalInt.empty());
        }

        return new Bounds(
                OptionalInt.of(nums.stream().min(Integer::compare).orElseThrow()),
                OptionalInt.of(nums.stream().max(Integer::compare).orElseThrow())
        );
    }
}
