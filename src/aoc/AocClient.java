package aoc;

import java.net.URI;
import java.net.http.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class AocClient {

    private final HttpClient client = HttpClient.newHttpClient();
    private final String session;
    private final String userAgent;

    public AocClient(String session) throws Exception {
        this.session = session;
        this.userAgent = Files.readString(Path.of("src/UserAgent")).trim();
    }

    public String fetchInput(int year, int day) throws Exception {
        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://adventofcode.com/" + year + "/day/" + day + "/input"))
                .header("Cookie", "session=" + session)
                .header("User-Agent", userAgent)
                .GET()
                .build();

        HttpResponse<String> resp = client.send(req, HttpResponse.BodyHandlers.ofString());
        return resp.body();
    }

    public HttpResponse<String> submit(int year, int day, int part, String answer) throws Exception {
        String body = "level=" + part + "&answer=" + answer;

        HttpRequest req = HttpRequest.newBuilder()
                .uri(URI.create("https://adventofcode.com/" + year + "/day/" + day + "/answer"))
                .header("Cookie", "session=" + session)
                .header("User-Agent", userAgent)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpRequest.BodyPublishers.ofString(body))
                .build();

        return client.send(req, HttpResponse.BodyHandlers.ofString());
    }
}
