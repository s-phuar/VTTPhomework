package CSpro;


import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.BufferedWriter;


//consider making an arraylist for each column e.g. app name, category, rating
//put each array into a hashmap?


public class CSVpro {
    
    public static void main(String[] args)
    throws IOException, FileNotFoundException
    {
            BufferedReader read = new BufferedReader(new FileReader("src/CSpro/googleplaystore.csv"));

            ArrayList<String> name = new ArrayList<>();
            ArrayList<String> category = new ArrayList<>();
            ArrayList<String> nameCategory = new ArrayList<>();
            ArrayList<String> rating = new ArrayList<>();
            LinkedHashMap <String, String> map =  new LinkedHashMap<>();

            //transform the csv data, pulling the 3 relevant columns
            String line;
            while((line = read.readLine()) != null ){
                String [] transformedLine = line.split(",");
                String ratingValue = transformedLine[2].trim();
                if(transformedLine.length >= 3 && !ratingValue.isEmpty() && ratingValue.matches("-?\\d+(\\.\\d+)?")){
                //add appName to name arraylist
                name.add(transformedLine[0]);
                //add app category to category arraylist
                category.add(transformedLine[1]);
                //add rating string to the arraylist
                rating.add(transformedLine[2]);
                }

            }
            //removing index 0, column names
            name.remove(0);
            category.remove(0);
            rating.remove(0);

            //combine name and category into 1 arraylist, duplicate app names are made unqiue
            for(int i = 0; i < name.size(); i++){
                nameCategory.add(name.get(i) + "|" + category.get(i));
            }
            //combine nameCategory and rating string into hashmap
            for(int i = 0; i < nameCategory.size(); i++){
                map.put(nameCategory.get(i), rating.get(i));
            }


            //create set of uniquewords holding each category
            ArrayList<String> uniqueWords = new ArrayList<>();
            for(String word: category){
                uniqueWords.add(word);
            }

            //for each category, match uniquewords 'nameCategory' with category with .contains
            ArrayList<categoryObject> temp = new ArrayList<>();
            
            //iterate every unique category
            for(int i = 0 ; i < uniqueWords.size(); i++){
                String uniqueCat = uniqueWords.get(i);
                //iterate every csv line over and over
                for(String key: map.keySet()){
                    //matches nameCategory with unique category, we want to store in Catarray (cat, name, rating)
                    if(key.contains(uniqueWords.get(i))){
                        String modifiedKey = key.replace(uniqueWords.get(i), "");
                        String modifiedKey2 = modifiedKey.replace("|", "");
                        String assocRating = map.get(key);
                            //each card object holds every cat/name/rating for 1 category
                            categoryObject card = new categoryObject(uniqueCat, modifiedKey2, assocRating);
                            //arraylist of card objects
                            temp.add(card);
                        
                    }
                }
            }



            read.close();

            //create new arraylist to hold trimmed and cleaned data
            ArrayList<cleanCat> cleaned = new ArrayList<>();
            for(categoryObject c: temp){
                String assocRating = c.getAssocRating().trim();
                if( assocRating.equals("NaN")|| assocRating.isEmpty() || assocRating.trim().isEmpty() ||Double.parseDouble(assocRating.trim()) > 5 ){
                    continue;
                }else{
                    cleanCat cleaning = new cleanCat(c.getUniqueCat(), c.getModifiedKey(), Double.parseDouble(assocRating));
                    cleaned.add(cleaning);
                }
            }


            //ratings analysis
            HashMap<String, Double> totalRatings = new HashMap<>();
            HashMap<String, String> highestRatedApp = new HashMap<>();
            HashMap<String, String> lowestRatedApp = new HashMap<>();
            HashMap<String, Double> highestRating = new HashMap<>();
            HashMap<String, Double> lowestRating = new HashMap<>();
            HashMap<String, Integer> countRatings = new HashMap<>();


            for (cleanCat cl : cleaned) {
                String Category = cl.getCategory();
                String appName = cl.getAppName();
                Double Rating = cl.getRating();
    
                // Aggregate total ratings
                // Category set as the key
                // the value checks whether the map already holds the key
                    //if holds key, retrieves current total and + Rating
                    //if doesnt hold key, initialise to 0 for that category and starts accumulating rating
                    //no conflicts between category as a new key is created each category
                totalRatings.put(Category, totalRatings.getOrDefault(Category, 0.0) + Rating);
                    //counts number of ratings each category
                countRatings.put(Category, countRatings.getOrDefault(Category, 0) + 1);

                // checks if highestRatedApp map contains the category, if doesnt we put in the KV
                // OR we check if the current cl object's Rating is higher than the previously stored rating
                if (!highestRatedApp.containsKey(Category) || Rating > highestRating.get(Category)) {
                    highestRatedApp.put(Category, appName);
                    highestRating.put(Category, Rating);
                }
    
                // Update lowest rating
                if (!lowestRatedApp.containsKey(Category) || Rating < lowestRating.get(Category)) {
                    lowestRatedApp.put(Category, appName);
                    lowestRating.put(Category, Rating);
                }
            }


            for (String c : totalRatings.keySet()) {
                double total = totalRatings.get(c); //total for each category
                int count = countRatings.get(c);
                double average = total / count;
                String highest = highestRatedApp.get(c);
                double highestRated = highestRating.get(c);
    
                String lowest = lowestRatedApp.get(c);
                double lowestRated = lowestRating.get(c);
    
                System.out.printf("Category: %s, Average Rating: %.2f, Highest Rating: %.2f for %s, Lowest Rating: %.2f for %s%n",
                                  c, average, highestRated, highest, lowestRated, lowest);
            }

    }


}
