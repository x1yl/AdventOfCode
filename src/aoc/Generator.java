package aoc;

import java.nio.file.*;

public class Generator {

    public void generate(int year, int day) throws Exception {
        Path base = Path.of("src", "year" + year,
                "Day" + String.format("%02d", day));

        Files.createDirectories(base);

        Path p1 = base.resolve("Day" + String.format("%02d", day) + "Part1.java");
        Path p2 = base.resolve("Day" + String.format("%02d", day) + "Part2.java");
        Path runner = base.resolve("Day" + String.format("%02d", day) + ".java");
        Path json = base.resolve("day.json");
        Path input = base.resolve("input");

        if (!Files.exists(p1))
            Files.writeString(p1, templateP1(year, day));

        if (!Files.exists(p2))
            Files.writeString(p2, templateP2(year, day));

        if (!Files.exists(runner))
            Files.writeString(runner, templateRunner(year, day));

        if (!Files.exists(json))
            Files.writeString(json, templateJson());

        if (!Files.exists(input) || Files.readString(input).isBlank()) {
            String session = Files.readString(Path.of("src/AocSession")).trim();
            AocService service = new AocService(session);
            String in = service.getOrFetchInput(year, day);
            Files.writeString(input, in);
        }

        System.out.println("Created/updated files for year " + year + " day " + day);
    }

    private String templateP1(int year, int day) {
        return """
                package year%1$d.Day%2$02d;

                public class Day%2$02dPart1 {
                    public static String solve(String input) {
                        return "";
                    }
                }
                """.formatted(year, day);
    }

    private String templateP2(int year, int day) {
        return """
                package year%1$d.Day%2$02d;

                public class Day%2$02dPart2 {
                    public static String solve(String input) {
                        return "";
                    }
                }
                """.formatted(year, day);
    }

    private String templateRunner(int year, int day) {
        return """
                package year%1$d.Day%2$02d;

                import aoc.AocService;
                
                import java.nio.file.Files;
                import java.nio.file.Path;

                public class Day%2$02d {
                    public static void main(String[] args) throws Exception {
                        String session = Files.readString(Path.of("src/AocSession")).trim();

                        AocService service = new AocService(session);

                        int year = %1$d;
                        int day  = %2$d;

                        String input = service.getOrFetchInput(year, day);

                        String p1 = Day%2$02dPart1.solve(input);
                        System.out.println("Part 1 Answer: " + p1);
                        service.submit(year, day, 1, p1);

                        String p2 = Day%2$02dPart2.solve(input);
                        System.out.println("Part 2 Answer: " + p2);
                        service.submit(year, day, 2, p2);
                    }
                }
                """.formatted(year, day);
    }

    private String templateJson() {
        return """
                {
                  "attempts1": [],
                  "attempts2": [],
                  "correct1": null,
                  "correct2": null,
                  "lastSubmitEpochMs": 0
                }
                """;
    }
}
