import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;

/*
Hk
2/29/24
Cz
 */
public class Main {
    public static void main(String[] args) throws IOException {
        // Creates a Scanner object for reading user input from the console.
        Scanner s = new Scanner(System.in);
        // Creating a second Scanner object for System.in is unnecessary; you can use 's' for all input.
        Scanner d = new Scanner(System.in);
        System.out.println("Input File Name: ");
        // Reads the name of the input file from the user.
        String fileName = s.nextLine();

        // Creates an instance of the MarkovChain class.
        MarkovChain mc = new MarkovChain();
        // Trains the MarkovChain instance using the provided file name.
        mc.train(fileName);
        System.out.println("How many words would you like to generate?");
        // Reads the number of words to generate from the user.
        int numWords = d.nextInt();

        System.out.println("Output File Name:");
        // Here, there's a potential issue. After reading an int, you need to consume the newline.
        String outFile = s.nextLine(); // This will not work as expected because it reads the leftover newline.

        // Generates text using the trained Markov Chain model.
        String generatedText = mc.generateText(numWords);

        // Tries to write the generated text to the specified output file.
        try (PrintWriter pw= new PrintWriter(outFile)) {
            pw.println(generatedText);
        }
        // Note: The Scanner objects 's' and 'd' are not explicitly closed. Consider closing them or using only one Scanner.
    }
}
