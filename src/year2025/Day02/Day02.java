package year2025.Day02;

import aoc.AocService;

import java.util.ArrayList;
import java.util.List;

public class Day02 {
    public static void main(String[] args) throws Exception {
        String session = java.nio.file.Files.readString(java.nio.file.Path.of("src/AocSession")).trim();
        AocService service = new AocService(session);

        int year = 2025;
        int day = 2;

        String input = service.getOrFetchInput(year, day);

        List<Long> productIDs = parseProductIDs(input);

        String p1 = Day02Part1.solve(productIDs);
        System.out.println("Part 1 Answer: " + p1);
        service.submit(year, day, 1, p1);

        String p2 = Day02Part2.solve(productIDs);
        System.out.println("Part 2 Answer: " + p2);
        service.submit(year, day, 2, p2);
    }

    private static List<Long> parseProductIDs(String input) {
        List<Long> productIDs = new ArrayList<>();
        for (String s : input.split(",")) {
            String[] parts = s.split("-");
            long first = Long.parseLong(parts[0].trim());
            long second = Long.parseLong(parts[1].trim());
            for (long i = first; i <= second; i++) {
                productIDs.add(i);
            }
        }
        return productIDs;
    }
}
