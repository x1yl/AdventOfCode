package year2025.Day05;


import aoc.AocService;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;


public class Day05 {
    public static void main(String[] args) throws Exception {
        String session = Files.readString(Path.of("src/AocSession")).trim();


        AocService service = new AocService(session);


        int year = 2025;
        int day  = 5;


        String input = service.getOrFetchInput(year, day);

        InputDataResult result = readInputData(input);

        ArrayList<String> freshProducts = result.freshProducts();
        ArrayList<Long> products = result.products();

        int p1 = Day05Part1.solve(freshProducts, products);
        System.out.println("Part 1 Answer: " + p1);
        //service.submit(year, day, 1, String.valueOf(p1));


        long p2 = Day05Part2.solve(freshProducts);
        System.out.println("Part 2 Answer: " + p2);
        //service.submit(year, day, 2, String.valueOf(p2));
    }

    public record InputDataResult(ArrayList<String> freshProducts, ArrayList<Long> products) {}

    public static InputDataResult readInputData(String input) {
        ArrayList<String> freshProducts = new ArrayList<>();
        ArrayList<Long> products = new ArrayList<>();

        if (input == null || input.isEmpty()) {
            return new InputDataResult(freshProducts, products);
        }

        String[] lines = input.split("\n");
        for (String line : lines) {
            if (line.contains("-")) {
                freshProducts.add(line);
            } else if (!line.isEmpty()) {
                products.add(Long.parseLong(line));
            }
        }

        freshProducts.sort((a, b) -> {
            long startA = Long.parseLong(a.split("-")[0]);
            long startB = Long.parseLong(b.split("-")[0]);
            return Long.compare(startA, startB);
        });

        return new InputDataResult(freshProducts, products);
    }
}