package year2025.Day04;

import java.util.ArrayList;

import static year2025.Day04.utils.countNeighbors;

public class Day04Part1 {
    public static int solve(ArrayList<String> fileData) {
        int accessiblePaper = 0;

        for (int i = 0; i < fileData.size(); i++) {
            String current = fileData.get(i);
            String previous = i > 0 ? fileData.get(i - 1) : null;
            String next = i < fileData.size() - 1 ? fileData.get(i + 1) : null;

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