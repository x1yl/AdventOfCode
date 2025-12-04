package aoc;

public class AocService {

    private final AocClient client;
    private final AocStorage storage;
    private final AocAnswerTracker tracker;

    private static final long FIVE_MINUTES_MS = 300_000;

    public AocService(String session) {
        this.client = new AocClient(session);
        this.storage = new AocStorage();
        this.tracker = new AocAnswerTracker(storage);
    }

    public String getOrFetchInput(int year, int day) throws Exception {
        if (storage.hasInput(year, day)) return storage.loadInput(year, day);

        String in = client.fetchInput(year, day);
        storage.saveInput(year, day, in);
        return in;
    }

    public void submit(int year, int day, int part, String answer) throws Exception {
        AocStorage.DayData d = storage.loadDay(year, day);

        long now = System.currentTimeMillis();
        long last = d.lastSubmitEpochMs;

        if (last != 0 && now - last < FIVE_MINUTES_MS) {
            long remain = FIVE_MINUTES_MS - (now - last);
            long sec = remain / 1000;
            System.out.println("Submission blocked: last submission was less than 5 minutes ago.");
            System.out.println("You must wait " + sec + " more seconds.");
            System.exit(0);
        }

        if (isNum(answer)) {
            int val = Integer.parseInt(answer);
            AocAnswerTracker.Bounds b = tracker.bounds(year, day, part);

            if (b.min().isPresent() && val <= b.min().getAsInt())
                System.out.println("Warning: Answer <= known minimum (" + b.min().getAsInt() + ")");

            if (b.max().isPresent() && val >= b.max().getAsInt())
                System.out.println("Warning: Answer >= known maximum (" + b.max().getAsInt() + ")");
        }

        if (part == 1) d.attempts1.add(answer);
        else d.attempts2.add(answer);

        d.lastSubmitEpochMs = now;
        storage.saveDay(year, day, d);

        var resp = client.submit(year, day, part, answer);
        String text = extractVisibleText(resp.body());
        System.out.println("Submission response:");
        System.out.println(text);

        if (text.contains("That's the right answer")) {
            if (part == 1) d.correct1 = answer;
            else d.correct2 = answer;
            storage.saveDay(year, day, d);
        }
    }

    private boolean isNum(String s) {
        try { Integer.parseInt(s); return true; }
        catch (Exception e) { return false; }
    }

    private String extractVisibleText(String html) {
        return html.replaceAll("<[^>]+>", " ")
                .replace("&nbsp;", " ")
                .replace("&gt;", ">")
                .replace("&lt;", "<")
                .replaceAll("\\s+", " ")
                .trim();
    }
}
