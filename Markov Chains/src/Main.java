import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws IOException {
        Scanner s = new Scanner(System.in);
        Scanner d = new Scanner(System.in);
        System.out.println("Input File Name: ");
        String fileName = s.nextLine();


        MarkovChain mc = new MarkovChain();
        mc.train(fileName);
        System.out.println("How many words would you like to generate?");
        int numWords = d.nextInt();

        System.out.println("Output File Name:");
        String outFile = s.nextLine();

        String generatedText = mc.generateText(numWords);

        try (PrintWriter pw= new PrintWriter(outFile)) {
            pw.println(generatedText);
        }





    }
}