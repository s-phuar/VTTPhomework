package LLM;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class LLM {
    private Reader reader;
    private Map<String, Map<String, Integer>> nextWords;
    private Random rand = new SecureRandom();


    public void LLM(Reader reader){
        this.reader = reader;
        this.nextWords = new HashMap<>(); //create new hashmap to store nextwords
    }


    public void addToWordDistribution(String curr, String next){
        //declares Map holding string integer KV pairs
        //1st half accesses wordDistrib map from nextWords map
        Map<String, Integer> wordDistrib; 
        if(nextWords.containsKey(curr)){
            wordDistrib = nextWords.get(curr); //value is expected to be KV String Integer pair
        }else{
            wordDistrib = new HashMap<>();
        }
        
        //2nd half updates wordDistrib map based off next
        int count = 0;
        if(wordDistrib.containsKey(next)){
            count = wordDistrib.get(next); //access most recent count
        }
        count++;
        wordDistrib.put(next, count);
        
        //last bit updates nextwords for next use
        nextWords.put(curr, wordDistrib);
    }


    //returns the value from word key, in the nextWord map
    public String nextWord(String word){
        int wordCount = -1;
        String theWord = "";
        Map<String, Integer> dist = nextWords.get(word); //temp dist map to access nextWords
        if(null == dist){
            return "";
        }
        for(String w: dist.keySet()){
            if(dist.get(w) > wordCount){
                wordCount = dist.get(w);
                theWord = w; //return the key
            }
        }
        return theWord;
    }

    public String randomNext(String word){
        Map<String, Integer> dist = nextWords.get(word); //temp dist map to access nextWords
        if(null == dist){
            return "";
        }
        int numWords = dist.size();
        int theWord = rand.nextInt(numWords);
        int i = 0;
        String nw = "";
        for(String w: nextWords.keySet()){
            nw = w; //nw equals key
            if(i > theWord){
                return w;
            }
            i++;
        }
        return nw;
    }

    public String generate(String root, int numWords, float hallucination) {
        String theSentence = root;
        String curr = root;
        String next = "";
        float totalRand = 1 - hallucination;
        for (int i = 0; i < numWords; i++) {
           if (rand.nextFloat() > totalRand)
              next = nextWord(curr); // Explotation
           else
              next = randomNext(curr); // Exploration
           if (next.length() <= 0)
              return theSentence;
           theSentence += " " + next;
           curr = next;
        }
  
        return theSentence;
     }
  
     public String generate(String root, int numWords) {
        String theSentence = root;
        String curr = root;
        String next = "";
        for (int i = 0; i < numWords; i++) {
           //next = nextWord(curr);
           next = randomNext(curr);
           if (next.length() <= 0)
              return theSentence;
           theSentence += " " + next;
           curr = next;
        }
  
        return theSentence;
     }
   public void buildModel() throws IOException {

      BufferedReader br = new BufferedReader(reader);
      String line;

      while ((line = br.readLine()) != null) {
         // Clean the line
         line = line.trim().replaceAll("\\p{Punct}", "");
         if (line.length() <= 0)
            continue;
         String[] words = line.split("\\s+");
         for (int i = 0; i < words.length - 1; i++) {
            String curr = words[i];
            String next = words[i + 1];
            addToWordDistribution(curr, next);
         }
      }
   }

   public void dumpModel() {
      for (String curr: nextWords.keySet()) {
         System.out.printf(">> %s\n", curr);
         Map<String, Integer> dist = nextWords.get(curr);
         for (String next: dist.keySet())
            System.out.printf("\t\t%s = %d\n", next, dist.get(next));
      }
   }


    
}
