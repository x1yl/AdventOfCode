package year2025.Day03;

import aoc.AocService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

public class Day03 {
    public static void main(String[] args) throws Exception {
        String session = Files.readString(Path.of("src/AocSession")).trim();
        AocService service = new AocService(session);

        int year = 2025;
        int day = 3;

        String rawInput = service.getOrFetchInput(year, day);
        List<String> batteriesList = rawInput.lines().toList();

        // Part 1
        String p1 = Day03Part1.solve(batteriesList);
        System.out.println("Part 1 Answer: " + p1);
        service.submit(year, day, 1, p1);

        // Part 2
        String p2 = Day03Part2.solve(batteriesList);
        System.out.println("Part 2 Answer: " + p2);
        service.submit(year, day, 2, p2);
    }
}
