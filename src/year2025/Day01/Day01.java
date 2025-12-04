package year2025.Day01;

import aoc.AocService;

import java.nio.file.Files;
import java.nio.file.Path;

public class Day01 {
    public static void main(String[] args) throws Exception {
        String session = Files.readString(Path.of("src/AocSession")).trim();
        AocService service = new AocService(session);

        int year = 2025;
        int day  = 1;

        String input = service.getOrFetchInput(year, day);

        String p1 = Day01Part1.solve(input);
        System.out.println("Part 1 Answer: " + p1);
        service.submit(year, day, 1, p1);

        String p2 = Day01Part2.solve(input);
        System.out.println("Part 2 Answer: " + p2);
        service.submit(year, day, 2, p2);
    }
}
