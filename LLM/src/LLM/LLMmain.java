package LLM;

import java.io.Console;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Path;
import java.nio.file.Paths;

import LLMans.LanguageModel;

// NUS\coding\homework\LLM> javac -d classes --source-path src src/LLM/*.java
// NUS\coding\homework\LLM> java -cp classes LLM.LLMmain catinthehat

public class LLMmain {
    
    public static void main(String[] args) throws IOException{
        if (args.length <= 0){
            System.out.println("Please initiate LLm with text file");
            System.exit(-1);
        }
        //args [1] being the txt file name
        Path p = Paths.get("src/LLM/" + args[0] +".txt");
        System.out.println(p);
        File file = p.toFile(); 
        Reader reader = new FileReader(file);
        
        LanguageModel lm = new LanguageModel(reader);
        lm.buildModel();
        //lm.dumpModel();

        Console cons = System.console();
        while (true) {
        String startWord = cons.readLine("> start word: ");
        int numWords = Integer.parseInt(cons.readLine("> num words: "));
        String generated = lm.generate(startWord, numWords, .75f);

        System.out.printf("\n==============\n%s\n", generated);
        }




    }


}
