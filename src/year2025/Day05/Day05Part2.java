package year2025.Day05;

import java.util.ArrayList;

public class Day05Part2 {
    public static long solve(ArrayList<String> freshProducts) {

        ArrayList<Long> starts = new ArrayList<>();
        ArrayList<Long> ends = new ArrayList<>();

        for (String range : freshProducts) {
            String[] parts = range.split("-");
            starts.add(Long.parseLong(parts[0].trim()));
            ends.add(Long.parseLong(parts[1].trim()));
        }

        return getTotal(starts, ends);
    }

    private static long getTotal(ArrayList<Long> starts, ArrayList<Long> ends) {
        long total = 0;
        long currentStart = starts.getFirst();
        long currentEnd = ends.getFirst();

        for (int i = 1; i < starts.size(); i++) {
            long nextStart = starts.get(i);
            long nextEnd = ends.get(i);

            if (nextStart <= currentEnd + 1 && nextEnd > currentEnd) {
                currentEnd = nextEnd;
            } else if (nextStart > currentEnd + 1) {
                total += (currentEnd - currentStart + 1);
                currentStart = nextStart;
                currentEnd = nextEnd;
            }
        }

        total += (currentEnd - currentStart + 1);
        return total;
    }
}