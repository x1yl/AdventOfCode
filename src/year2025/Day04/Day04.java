package year2025.Day04;

import aoc.AocService;

import java.util.ArrayList;

public class Day04 {
    public static void main(String[] args) throws Exception {
        String session = java.nio.file.Files.readString(
                java.nio.file.Path.of("src/AocSession")
        ).trim();

        AocService service = new AocService(session);

        int year = 2025;
        int day  = 4;

        String inputs = service.getOrFetchInput(year, day);

        ArrayList<String> input = readInputData(inputs);

        int p1 = Day04Part1.solve(input);
        System.out.println("Part 1 Answer: " + p1);
        //service.submit(year, day, 1, String.valueOf(p1));

        int p2 = Day04Part2.solve(input);
        System.out.println("Part 2 Answer: " + p2);
        service.submit(year, day, 2, String.valueOf(p2));
    }

    public static ArrayList<String> readInputData(String input) {
        ArrayList<String> fileData = new ArrayList<>();
        if (input == null || input.isEmpty()) {
            return fileData;
        }

        String[] lines = input.split("\n");
        for (String line : lines) {
            if (!line.trim().isEmpty()) {
                fileData.add(line.trim());
            }
        }
        return fileData;
    }

}
