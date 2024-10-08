package CSpro;


import java.io.FileReader;
import java.io.IOException;
import java.io.FileNotFoundException;
import java.io.Reader;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.BufferedReader;
import com.opencsv.CSVReader;

//consider making an arraylist for each column e.g. app name, category, rating
//put each array into a hashmap?


public class CSVpro {
    
    public static void main(String[] args)
    throws IOException, FileNotFoundException
    {
            CSVReader read = new CSVReader(new FileReader("src/CSpro/googleplaystore.csv"));

            ArrayList<String> pro = new ArrayList<>();

            String line = "x";
            while(line != null){
                line = read.readLine();

                String[] tokens = line.split(",");
                for (int i = 0; i < tokens.length; i++){
                        pro.add(tokens[i].split(",")[0]);
                        // pro.add(tokens[i].split(",")[1]);
                        // pro.add(tokens[i].split(",")[2]);
                    
            }

                System.out.println(pro);


            }

            read.close();


    }


}
