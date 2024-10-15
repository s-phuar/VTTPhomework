package homework4;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import java.nio.file.Files;
import java.nio.file.Path;

import java.util.Random;

public class Cookie {

    
    public String cookie (Path p) throws FileNotFoundException, IOException{

        File file = p.toFile();

        BufferedReader reader = new BufferedReader(new FileReader(file));
        int lines = 0;
        while(reader.readLine() != null){
            lines++;
        }
        reader.close();
        //randonly choose line
        Random random = new Random();
        int randomLineNumber = random.nextInt(lines);
        String theMessage = Files.readAllLines(p).get(randomLineNumber);
        return theMessage;

}
}
