import java.io.IOException;
import java.nio.file.*;
import java.util.*;
/*
Hk
2/29/24
Cz
 */
public class MarkovChain {

    // Maps a word to a list of words that may follow it based on training data.
    private final HashMap<String, ArrayList<String>> hashmap = new HashMap<>();
    // Stores potential starting words for generated sentences.
    private final List<String> startingWords = new ArrayList<>();
    // Random object for making probabilistic choices.
    private final Random r = new Random();

    // Trains the Markov chain with text from a given file.
    public void train(String fileName) throws IOException {
        // Reads all lines from the specified file into a List of Strings.
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        String previousWord = null; // Holds the last word processed, for linking in the hashmap.

        for (String line : lines) {
            // Splits each line into words, separating by whitespace.
            String[] words = line.split("\\s+");
            for (String word : words) {
                if (word.isEmpty()) { // Skips any "empty" words resulting from multiple spaces.
                    continue;
                }

                // If there's a previous word, add the current word as a possible follow-up in the hashmap.
                if (previousWord != null) {
                    hashmap.computeIfAbsent(previousWord, k -> new ArrayList<>()).add(word);
                } else { // If there's no previous word, consider the current word as a starting word.
                    startingWords.add(word);
                }

                // If the word ends with a sentence-ending punctuation, reset previousWord to null.
                if (word.endsWith(".") || word.endsWith("!") || word.endsWith("?")) {
                    previousWord = null;
                } else {
                    previousWord = word; // Otherwise, set previousWord to the current word for the next iteration.
                }
            }
        }
    }

    // Generates text of approximately 'wordCount' words using the trained model.
    public String generateText(int wordCount) {
        if (startingWords.isEmpty() || wordCount <= 0) {
            return ""; // Returns an empty string if no starting words are available or wordCount is invalid.
        }

        StringBuilder sb = new StringBuilder(); // For building the generated text.
        String currentWord = startingWords.get(r.nextInt(startingWords.size())); // Chooses a random starting word.
        sb.append(currentWord);

        int wordsGenerated = 1;
        while (wordsGenerated < wordCount) {
            List<String> nextWords = hashmap.get(currentWord); // Gets the list of possible follow-up words.
            if (nextWords == null || nextWords.isEmpty()) {
                break; // Breaks the loop if there are no follow-up words.
            }
            currentWord = nextWords.get(r.nextInt(nextWords.size())); // Chooses a random follow-up word.
            sb.append(" ").append(currentWord);
            wordsGenerated++;

            // Adds a newline after sentence-ending punctuation and potentially chooses a new starting word.
            if (currentWord.endsWith(".") || currentWord.endsWith("!") || currentWord.endsWith("?")) {
                sb.append("\n");
                if (wordsGenerated < wordCount) {
                    currentWord = startingWords.get(r.nextInt(startingWords.size()));
                    sb.append(currentWord).append(" ");
                    wordsGenerated++;
                }
            }
        }

        return sb.toString();
    }
}
