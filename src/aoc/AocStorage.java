package aoc;

import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class AocStorage {

    public static class DayData {
        public List<String> attempts1 = new ArrayList<>();
        public List<String> attempts2 = new ArrayList<>();
        public String correct1 = null;
        public String correct2 = null;
        public long lastSubmitEpochMs = 0;
    }

    private Path folder(int year, int day) throws IOException {
        Path f = Path.of("src", "year" + year, "Day" + String.format("%02d", day));
        if (!Files.exists(f)) Files.createDirectories(f);
        return f;
    }

    private Path jsonPath(int year, int day) throws IOException {
        return folder(year, day).resolve("day.json");
    }

    private Path inputPath(int year, int day) throws IOException {
        return folder(year, day).resolve("input");
    }

    public boolean hasInput(int year, int day) throws IOException {
        return Files.exists(inputPath(year, day));
    }

    public String loadInput(int year, int day) throws IOException {
        return Files.readString(inputPath(year, day));
    }

    public void saveInput(int year, int day, String text) throws IOException {
        Files.writeString(inputPath(year, day), text);
    }

    public DayData loadDay(int year, int day) throws IOException {
        Path p = jsonPath(year, day);
        if (!Files.exists(p)) return new DayData();
        return parse(Files.readString(p));
    }

    public void saveDay(int year, int day, DayData d) throws IOException {
        Files.writeString(jsonPath(year, day), stringify(d));
    }

    private String stringify(DayData d) {
        return """
                {
                  "attempts1": %s,
                  "attempts2": %s,
                  "correct1": %s,
                  "correct2": %s,
                  "lastSubmitEpochMs": %d
                }
                """.formatted(
                jsonList(d.attempts1),
                jsonList(d.attempts2),
                jsonString(d.correct1),
                jsonString(d.correct2),
                d.lastSubmitEpochMs
        );
    }

    private String jsonString(String s) {
        if (s == null) return "null";
        return "\"" + s.replace("\"", "\\\"") + "\"";
    }

    private String jsonList(List<String> list) {
        StringJoiner j = new StringJoiner(", ", "[", "]");
        for (String s : list) j.add(jsonString(s));
        return j.toString();
    }

    private DayData parse(String json) {
        DayData d = new DayData();
        d.attempts1 = extractList(json, "attempts1");
        d.attempts2 = extractList(json, "attempts2");
        d.correct1 = extractString(json, "correct1");
        d.correct2 = extractString(json, "correct2");
        d.lastSubmitEpochMs = extractLong(json, "lastSubmitEpochMs");
        return d;
    }

    private long extractLong(String json, String key) {
        int idx = json.indexOf("\"" + key + "\"");
        if (idx == -1) return 0;
        int colon = json.indexOf(":", idx);
        int end = json.indexOf("\n", colon);
        try {
            return Long.parseLong(json.substring(colon + 1, end).trim().replace(",", ""));
        } catch (Exception e) {
            return 0;
        }
    }

    private String extractString(String json, String key) {
        int idx = json.indexOf("\"" + key + "\"");
        if (idx == -1) return null;
        int colon = json.indexOf(":", idx);
        int start = json.indexOf("\"", colon + 1);
        if (start == -1) return null;
        int end = json.indexOf("\"", start + 1);
        if (end == -1) return null;
        return json.substring(start + 1, end);
    }

    private List<String> extractList(String json, String key) {
        int idx = json.indexOf("\"" + key + "\"");
        if (idx == -1) return new ArrayList<>();
        int start = json.indexOf("[", idx);
        int end = json.indexOf("]", start);
        String slice = json.substring(start + 1, end).trim();
        if (slice.isEmpty()) return new ArrayList<>();
        List<String> out = new ArrayList<>();
        for (String part : slice.split(",")) {
            part = part.trim();
            if (part.startsWith("\"") && part.endsWith("\""))
                out.add(part.substring(1, part.length() - 1));
        }
        return out;
    }
}
