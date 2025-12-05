package aoc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AocService {

    private final AocClient client;
    private final AocStorage storage;
    private final AocAnswerTracker tracker;

    private static final long FIVE_MINUTES_MS = 300_000;

    public AocService(String session) throws Exception {
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

        // Rate limit check
        if (last != 0 && now - last < FIVE_MINUTES_MS) {
            long remain = FIVE_MINUTES_MS - (now - last);
            long sec = remain / 1000;
            System.out.println("Submission blocked: last submission was less than 5 minutes ago.");
            System.out.println("You must wait " + sec + " more seconds.");
            System.exit(0);
        }

        // ---- NEW: Prevent submission when outside bounds ----
        if (isNum(answer)) {
            int val = Integer.parseInt(answer);
            AocAnswerTracker.Bounds b = tracker.bounds(year, day, part);

            boolean bad = false;

            if (b.min().isPresent() && val <= b.min().getAsInt()) {
                System.out.println(ConsoleColors.YELLOW +
                        "Warning: Answer <= known minimum (" + b.min().getAsInt() + ")" +
                        ConsoleColors.RESET);
                bad = true;
            }

            if (b.max().isPresent() && val >= b.max().getAsInt()) {
                System.out.println(ConsoleColors.YELLOW +
                        "Warning: Answer >= known maximum (" + b.max().getAsInt() + ")" +
                        ConsoleColors.RESET);
                bad = true;
            }

            // If the number is outside known bounds → CANCEL submission
            if (bad) {
                System.out.println(ConsoleColors.RED +
                        "Submission cancelled: answer is outside known bounds." +
                        ConsoleColors.RESET);
                return;   // <-- stops execution here
            }
        }
        // -----------------------------------------------------

        // Track attempt & update timestamp
        if (part == 1) d.attempts1.add(answer);
        else d.attempts2.add(answer);

        d.lastSubmitEpochMs = now;
        storage.saveDay(year, day, d);

        // Submit to AoC
        var resp = client.submit(year, day, part, answer);
        String text = extractAoCMessage(resp.body());
        System.out.println("Submission response:");
        System.out.println(text);

        // Save the correct answer if success
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

    private String extractAoCMessage(String html) {
        if (html == null || html.isBlank()) return "";

        // 1) Try to extract the first <article>...</article> block
        String article = extractTagBlock(html, "article");
        String source;
        if (article != null) {
            source = article;
        } else {
            // 2) Fallback: first <main>..</main> or first <p>..</p>
            String main = extractTagBlock(html, "main");
            if (main != null) source = main;
            else {
                String p = extractTagBlock(html, "p");
                source = p != null ? p : html; // last resort: whole document
            }
        }

        // 3) Remove script/style blocks from the selected source
        source = source.replaceAll("(?is)<script.*?</script>", " ");
        source = source.replaceAll("(?is)<style.*?</style>", " ");

        // 4) Remove all remaining tags
        source = source.replaceAll("(?i)<[^>]+>", " ");

        // 5) Unescape common named entities and numeric entities
        source = unescapeHtmlEntities(source);

        // 6) Collapse whitespace
        return source.replaceAll("\\s+", " ").trim();
    }

    /* helper: extract first <tag ...> ... </tag> (DOTALL, case-insensitive) */
    private String extractTagBlock(String html, String tag) {
        Pattern p = Pattern.compile("(?is)<" + tag + "\\b[^>]*>(.*?)</" + tag + ">");
        Matcher m = p.matcher(html);
        if (m.find()) return m.group(1);
        return null;
    }

    /* helper: unescape HTML entities (common named + numeric decimal & hex) */
    private String unescapeHtmlEntities(String s) {
        if (s == null) return null;

        // commonly named entities
        s = s.replace("&nbsp;", " ");
        s = s.replace("&amp;", "&");
        s = s.replace("&lt;", "<");
        s = s.replace("&gt;", ">");
        s = s.replace("&quot;", "\"");
        s = s.replace("&#39;", "'");

        // numeric decimal: &#1234;
        Pattern dec = Pattern.compile("&#(\\d+);");
        Matcher md = dec.matcher(s);
        StringBuilder sb = new StringBuilder();
        while (md.find()) {
            try {
                int code = Integer.parseInt(md.group(1));
                md.appendReplacement(sb, Matcher.quoteReplacement(new String(Character.toChars(code))));
            } catch (Exception e) {
                md.appendReplacement(sb, "");
            }
        }
        md.appendTail(sb);
        s = sb.toString();

        // numeric hex: &#x1F4A9; or &#X1F4A9;
        Pattern hex = Pattern.compile("&#x([0-9a-fA-F]+);");
        Matcher mh = hex.matcher(s);
        sb = new StringBuilder();
        while (mh.find()) {
            try {
                int code = Integer.parseInt(mh.group(1), 16);
                mh.appendReplacement(sb, Matcher.quoteReplacement(new String(Character.toChars(code))));
            } catch (Exception e) {
                mh.appendReplacement(sb, "");
            }
        }
        mh.appendTail(sb);
        return sb.toString();
    }
}
