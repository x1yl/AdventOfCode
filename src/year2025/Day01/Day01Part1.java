package year2025.Day01;

public class Day01Part1 {

    public static String solve(String input) {
        int start = 50;
        int count = 0;

        String[] lines = input.split("\n");

        for (String current : lines) {
            if (current.isEmpty()) continue;

            if (current.charAt(0) == 'L') {
                int num = Integer.parseInt(current.substring(1));
                start -= num;
            } else if (current.charAt(0) == 'R') {
                int num = Integer.parseInt(current.substring(1));
                start += num;
            }

            while (start > 99 || start < 0) {
                if (start < 0) start += 100;
                if (start > 99) start -= 100;
            }

            if (start == 0) count++;
        }

        return Integer.toString(count);
    }
}

