package aoc;

import java.time.LocalDate;

public class Aoc {

    public static void main(String[] args) throws Exception {
        if (args.length == 0) {
            System.out.println("Usage:");
            System.out.println("  java aoc.Aoc today");
            System.out.println("  java aoc.Aoc generate <year> <day>");
            return;
        }

        String cmd = args[0];

        switch (cmd) {
            case "today" -> {
                LocalDate d = LocalDate.now();
                new Generator().generate(d.getYear(), d.getDayOfMonth());
            }
            case "generate" -> {
                if (args.length != 3) {
                    System.out.println("Usage: java aoc.Aoc generate <year> <day>");
                    return;
                }
                int year = Integer.parseInt(args[1]);
                int day = Integer.parseInt(args[2]);
                new Generator().generate(year, day);
            }
            default -> System.out.println("Unknown command: " + cmd);
        }
    }
}
