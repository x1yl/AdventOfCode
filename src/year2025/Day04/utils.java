package year2025.Day04;

public class utils {
    public static int countNeighbors(String current, String previous, String next, int index) {
        int count = 0;

        if (index > 0 && current.charAt(index - 1) == '@') {
            count++;
        }
        if (index < current.length() - 1 && current.charAt(index + 1) == '@') {
            count++;
        }
        count = countNeighborsFromAdjacentRow(current, previous, count, index);
        count = countNeighborsFromAdjacentRow(current, next, count, index);

        return count;
    }

    private static int countNeighborsFromAdjacentRow(String current, String adjacentRow, int currentCount, int index) {
        if (adjacentRow == null) {
            return currentCount;
        }
        if (index > 0 && adjacentRow.charAt(index - 1) == '@') {
            currentCount++;
        }
        if (index < current.length() - 1 && adjacentRow.charAt(index + 1) == '@') {
            currentCount++;
        }
        if (adjacentRow.charAt(index) == '@') {
            currentCount++;
        }

        return currentCount;
    }
}
