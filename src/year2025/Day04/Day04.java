package year2025.Day04;

import aoc.AocService;

public class Day04 {
    public static void main(String[] args) throws Exception {
        String session = java.nio.file.Files.readString(
                java.nio.file.Path.of("src/AocSession")
        ).trim();

        AocService service = new AocService(session);

        int year = 2025;
        int day  = 4;

        String input = service.getOrFetchInput(year, day);

        String p1 = Day04Part1.solve(input);
        System.out.println("Part 1 Answer: " + p1);
        service.submit(year, day, 1, p1);

        String p2 = Day04Part2.solve(input);
        System.out.println("Part 2 Answer: " + p2);
        service.submit(year, day, 2, p2);
    }
}
