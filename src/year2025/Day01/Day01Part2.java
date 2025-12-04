package year2025.Day01;

public class Day01Part2 {

    public static String solve(String input) {
        int start = 50;
        int count = 0;

        String[] lines = input.split("\n");

        for (String current : lines) {
            if (current.isEmpty()) continue;

            int num = 0;
            int dir = 0;

            if (current.charAt(0) == 'L') {
                num = Integer.parseInt(current.substring(1));
                dir = -1;
            } else if (current.charAt(0) == 'R') {
                num = Integer.parseInt(current.substring(1));
                dir = 1;
            }

            for (int j = 0; j < num; j++) {
                start += dir;

                if (start > 99) {
                    start -= 100;
                } else if (start < 0) {
                    start += 100;
                }

                if (start == 0) {
                    count++;
                }
            }
        }

        return Integer.toString(count);
    }
}

