import java.io.IOException;
import java.nio.file.*;
import java.util.*;

public class MarkovChain {

    private final HashMap<String, ArrayList<String>> hashmap = new HashMap<>();
    private final List<String> startingWords = new ArrayList<>();
    private final Random r = new Random();

    public void train(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        String previousWord = null;

        for (String line : lines) {
            String[] words = line.split("\\s+");
            for (String word : words) {
                if (word.isEmpty()) {
                    continue;
                }

                if (previousWord != null) {
                    if (!hashmap.containsKey(previousWord)) {
                        hashmap.put(previousWord, new ArrayList<>());
                    }
                    hashmap.get(previousWord).add(word);
                } else {
                    startingWords.add(word);
                }

                if (word.endsWith(".") || word.endsWith("!") || word.endsWith("?")) {
                    previousWord = null;
                } else {
                    previousWord = word;
                }
            }
        }
    }

    public String generateText(int wordCount) {
        if (startingWords.isEmpty() || wordCount <= 0) {
            return "";
        }

        StringBuilder sb = new StringBuilder();
        String currentWord = startingWords.get(r.nextInt(startingWords.size()));
        sb.append(currentWord);

        int wordsGenerated = 1;
        while (wordsGenerated < wordCount) {
            List<String> nextWords = hashmap.get(currentWord);
            if (nextWords == null || nextWords.isEmpty()) {
                break;
            }
            currentWord = nextWords.get(r.nextInt(nextWords.size()));
            sb.append(" ").append(currentWord);
            wordsGenerated++;

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