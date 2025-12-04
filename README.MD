# Advent of Code - Java Solutions

My solutions to all completed Advent of Code Days. Includes a Java-based framework for solving Advent of Code puzzles with automated input fetching, answer submission, and local caching. NOTE: The framework in this repo is ***SOURCE AVALIABLE ONLY***. If you are interested in adapting or using the framework please create a pull request.

## Features

- 🎄 Automatic puzzle input downloading
- 📝 Answer submission with validation
- 💾 Local caching of inputs and attempts
- ⏱️ Rate limiting to comply with AoC guidelines
- 🎨 Color-coded console output
- 🚀 Template generation for new puzzles

## Automation Compliance

**This tool follows the [automation guidelines](https://www.reddit.com/r/adventofcode/wiki/faqs/automation) on the /r/adventofcode community wiki.** Specifically:

- **Outbound calls are throttled** to every 5 minutes in `AocService.submit()` (see `FIVE_MINUTES_MS` constant)
- **Once inputs are downloaded, they are cached locally** in `AocStorage.saveInput()` and reused via `AocStorage.hasInput()` / `AocStorage.loadInput()`
    - If you suspect your input is corrupted, you can manually delete the `input` file in the day's folder and run again to re-download
- **The User-Agent header** is set to `github.com/x1yl/AdventOfCode (by kevin@kevinzheng.fyi)` in `AocClient` as per the guidelines

## Project Structure


```
src/
├── aoc/                          # Core framework
│   ├── Aoc.java                  # Main entry point
│   ├── AocClient.java            # HTTP client for AoC API
│   ├── AocService.java           # High-level service layer
│   ├── AocStorage.java           # File-based persistence
│   ├── AocAnswerTracker.java     # Answer bounds tracking
│   ├── Generator.java            # Template generator
│   └── ConsoleColors.java        # Terminal colors
├── AocSession                     # Your session cookie (not committed)
└── year2025/                      # Solutions by year
    └── Day01/
        ├── Day01.java             # Runner
        ├── Day01Part1.java        # Part 1 solution
        ├── Day01Part2.java        # Part 2 solution
        ├── input                  # Cached puzzle input
        └── day.json               # Attempt history
```
## Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/x1yl/AdventOfCode.git
   cd AdventOfCode
   ```

2. **Add your session cookie**

   Create a file `src/AocSession` containing your Advent of Code session cookie:
   ```bash
   echo "your_session_cookie_here" > src/AocSession
   ```

   To get your session cookie:
    - Log in to [adventofcode.com](https://adventofcode.com)
    - Open browser DevTools (F12)
    - Go to Application/Storage → Cookies
    - Copy the value of the `session` cookie

## Usage

### Generate Templates for Today's Puzzle
``` bash
java aoc.Aoc today
```
### Generate Templates for a Specific Day
``` bash
java aoc.Aoc generate 2025 1
```
This creates:
- `DayXXPartY.java` files with solution templates
- `DayXX.java` runner that fetches input and submits answers
- `input` file with your puzzle input (auto-downloaded)
- `day.json` for tracking attempts

### Run a Solution
```
bash
# From project root
cd src
javac year2025/Day01/*.java aoc/*.java
java year2025.Day01.Day01
```
Or from your IDE, just run the `DayXX.java` file directly.

## How It Works

1. **Input Fetching**: On first run, `AocService` downloads your puzzle input via `AocClient` and caches it locally in `AocStorage`
2. **Solving**: Your solution code in `DayXXPartY.solve()` processes the input and returns the answer as a string
3. **Submission**: `AocService.submit()` sends your answer to AoC, respecting the 5-minute rate limit
4. **Tracking**: All attempts are logged in `day.json` with timestamps, and bounds checking warns you if your answer is outside known ranges

## Solution Template

Each day follows this pattern:
```
java
package year2025.Day01;

public class Day01Part1 {
    public static String solve(String input) {
        // Your solution here
        return "answer";
    }
}
```
The runner handles everything else:
```
java
String input = service.getOrFetchInput(year, day);
String p1 = Day01Part1.solve(input);
service.submit(year, day, 1, p1);
```
## Rate Limiting Details

The framework enforces a **5-minute cooldown** between submissions to the same puzzle. If you attempt to submit too quickly, you'll see:
```

Submission blocked: last submission was less than 5 minutes ago.
You must wait X more seconds.
```
This protects both you and the AoC servers from excessive requests.

## Answer Tracking

The framework tracks:
- All attempted answers for each part
- Correct answers (auto-saved when "That's the right answer" is detected)
- Last submission timestamp
- Bounds warnings (if your answer is outside the min/max of previous numeric attempts)

## Contributing

Feel free to fork and adapt this framework for your own use! If you find bugs or have suggestions, please open an issue.

## License

This project is open source. Use it however you like for your Advent of Code journey! 🎄

---