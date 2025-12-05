package year2025.Day04;

import java.util.ArrayList;

import static year2025.Day04.utils.countNeighbors;

public class Day04Part2 {
    public static int solve(ArrayList<String> input) {
        int accessiblePaper = 0;
        boolean loop =true;

        while (loop) {
            loop = false;
            ArrayList<String> newInput = new ArrayList<>();
            for (int i = 0; i < input.size(); i++) {
                String current = input.get(i);
                String previous = i > 0 ? input.get(i - 1) : null;
                String next = i < input.size() - 1 ? input.get(i + 1) : null;
                String newCurrent = current;

                for (int j = 0; j < current.length(); j++) {
                    if (current.charAt(j) == '@') {
                        int neighborCount = countNeighbors(current, previous, next, j);
                        if (neighborCount < 4) {
                            newCurrent = newCurrent.substring(0, j) + "x" + current.substring(j + 1);
                            loop = true;
                            accessiblePaper++;
                        }
                    }
                }
                newInput.add(newCurrent);
            }
            input = newInput;
        }
        return accessiblePaper;
    }

}