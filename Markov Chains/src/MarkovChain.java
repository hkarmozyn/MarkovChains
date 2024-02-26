
import java.io.IOException;
import java.nio.file.*;
import java.util.*;
public class MarkovChain {
    private final Map<String, List<String>> hashMap = new HashMap<>();
   private final List<String> startingWords= new ArrayList<>();
    private final Random r = new Random();

    public void train(String fileName) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(fileName));

        String previousWord = null;
        for (String line: lines){
            String[] words = line.split("\\s+");
            for (String word : words){
                if (word.isEmpty()) continue;
                 if(previousWord!=null) {
                     if (!hashMap.containsKey(previousWord)) {
                         hashMap.put(previousWord, new ArrayList<>());
                     }
                     hashMap.get(previousWord).add(word);
                 }else{
                     startingWords.add(word);

                }
                 if (word.endsWith(".")||word.endsWith("?")||word.endsWith("!")){
                     previousWord = null;
                 }else{
                     previousWord = word;
                 }

            }
        }

    }

}
