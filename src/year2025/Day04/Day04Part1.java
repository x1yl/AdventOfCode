package year2025.Day04;

import java.util.ArrayList;

import static year2025.Day04.utils.countNeighbors;

public class Day04Part1 {
    public static int solve(ArrayList<String> input) {
        int accessiblePaper = 0;

        for (int i = 0; i < input.size(); i++) {
            String current = input.get(i);
            String previous = i > 0 ? input.get(i - 1) : null;
            String next = i < input.size() - 1 ? input.get(i + 1) : null;

            for (int j = 0; j < current.length(); j++) {
                if (current.charAt(j) == '@') {
                    int neighborCount = countNeighbors(current, previous, next, j);
                    if (neighborCount < 4) {
                        accessiblePaper++;
                    }
                }

            }
        }
        return accessiblePaper;
    }

}