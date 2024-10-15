package homework3;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
        //javac -d shoppingcart.jar --source-path src src/homework3/GroceryListUpdated.java
        //java -cp shoppingcart.jar homework3.GroceryListUpdated src/homework3/cartdb
        // "src/homework3/cartdb" as the args[0]

public class GroceryListUpdated {
    static String username;    //tracks current user 

    public static void main(String[] args) throws IOException, FileNotFoundException{
        String cartDirectory;
        if(args.length > 0 ){
            cartDirectory = args[0];
        }else{
            cartDirectory = "db";
        }

        //directory creation 'cartdb'
        File directory = new File(cartDirectory);
        if(!directory.exists()){
            boolean isDirCreated = directory.mkdir(); //creates directory as well as return a boolean
            if(isDirCreated){
                System.out.println("Directory created: " + cartDirectory);
            }else{
                System.out.println("Failed to create directory: " + cartDirectory);
            }
        }else{
            System.out.println("Using directory: " + cartDirectory);
        }

        //create blank userList.txt file, stores all usernames
        Path pUserList = Paths.get(cartDirectory + "/userList.txt");
        File fileUserList = pUserList.toFile();
        if(!fileUserList.exists()){
            fileUserList.createNewFile(); 
        }

        ArrayList<String> groceryList = new ArrayList<>();  //Arraylist to hold all ingredients

        while(true){
            Console cons = System.console();
            String cmd = cons.readLine("> ").trim().toLowerCase();

            String[] cmdSplit;        

            if(cmd.contains(",")){ //when adding ingredients, account for commas
                cmdSplit = cmd.split(" ");
                for(int i = 0; i < cmdSplit.length; i++){
                    cmdSplit[i] = cmdSplit[i].replace(",","");
                    }
            }else{
            cmdSplit = cmd.split(" ");
            }

        switch(cmdSplit[0]){
            //new commands

            case "login":
                String user = cmdSplit[1]; //user's name
                List<String> lines = Files.readAllLines(pUserList); //read userfile.txt into a list
                //update member field
                username = user;

                try{
                if(lines.contains(user)){   //if list contains the user's name
                    //access the user's file and check if null
                    Path userName = Paths.get(cartDirectory + "/" + user + ".txt");
                    File fileUserName = userName.toFile();
                    FileReader reader = new FileReader(fileUserName);
                    BufferedReader bufferedReader = new BufferedReader(reader);
                    //if null, print null
                    String line;
                    if ((line = bufferedReader.readLine()) != null){    
                        System.out.printf("%s, your cart contains the following items\n", user);
                        int counter = 1;
                        for(String grocery : Files.readAllLines(userName)){
                            System.out.printf("%d. %s\n", counter, grocery);
                            counter ++;
                            }
                    }else{                                  
                        System.out.printf("%s, your cart is empty\n", user);
                    }

                }else{
                    //write to userList.txt to append new user
                    System.out.println("user does not exist, creating new user...");
                    Writer writer = new FileWriter(fileUserList, true);
                    BufferedWriter bufferedWriter = new BufferedWriter(writer);
                    bufferedWriter.write(user + "\n");
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    //create blank txt file off new user's name
                    Path userName = Paths.get(cartDirectory + "/" + user + ".txt");
                    File fileUserName = userName.toFile();
                    fileUserName.createNewFile(); 
                }
                }catch (IOException e){
                    System.err.println("Error creating user file: " + e.getMessage());
                }
            break;

            case "save":
                //grab whats in grocerylist, throws it into current user's list
                if (username != null){
                Path saveList = Paths.get(cartDirectory + "/" + username + ".txt");
                File save = saveList.toFile();
                Writer writer = new FileWriter(save);
                BufferedWriter bufferedWriter = new BufferedWriter(writer);
                for(int i = 0; i < groceryList.size(); i++){
                    bufferedWriter.write(groceryList.get(i));
                    bufferedWriter.newLine();
                }
                bufferedWriter.flush();
                bufferedWriter.close();
                System.out.println("your cart has been saved");
                }else{
                    System.out.println("Please login to a user first");
                }
            break;

            case "users":
                FileReader reader = new FileReader(fileUserList);
                BufferedReader bufferedReader = new BufferedReader(reader);
                //if null, print null
                String line;
                if ((line = bufferedReader.readLine()) != null){    
                    System.out.println("The following users are registered");
                    int counter = 1;
                    for(String name : Files.readAllLines(pUserList)){
                        System.out.printf("%d. %s\n", counter, name);
                        counter ++;
                        }
                }
            break;

            case "list":
                if (groceryList.isEmpty()){
                    System.out.println("Your cart is empty");
                } else {
                    for(int i = 0; i < groceryList.size(); i++){
                        System.out.printf("%d. %s\n", i+1, groceryList.get(i));
                    }
                }
            break;

            case "add":
                for(String s : cmdSplit){
                    if(s.equals("add")){
                        continue;
                    }
                    else if(groceryList.contains(s)){
                        System.out.printf("You have %s in your cart\n", s);
                    }else{
                        groceryList.add(s);
                        System.out.printf("%s added to cart\n", s);
                    }
                }
            break;

            case "delete":
                for(String s : cmdSplit){

                    if(s.equals("delete")){
                        continue;
                    }else if(Integer.parseInt(s) > groceryList.size()){
                        System.out.println("Incorrect item index");
                    }else{
                        Integer ss = Integer.parseInt(s);
                        System.out.printf("%s removed from cart\n", groceryList.get(ss - 1));
                        groceryList.remove(ss - 1);
                    }
                }
                break;

            }


        }

    }

    }   

