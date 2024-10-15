package homework3;

import java.util.ArrayList;
import java.util.HashSet;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.Console;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;


public class GroceryListUpdated {

    public static void main(String[] args) throws IOException, FileNotFoundException{
        String cartDirectory;   
        //******************************use "src/homework3/directoryname" as the args[0]

        if(args.length > 0 ){
            cartDirectory = args[0];
        }else{
            cartDirectory = "db";
        }

        //file to hold name of directory
        File directory = new File(cartDirectory);

        if(!directory.exists()){
            //creates directory as well as return a boolean
            boolean isDirCreated = directory.mkdir();
            if(isDirCreated){
                System.out.println("Directory created: " + cartDirectory);
            }else{
                System.out.println("Failed to create directory: " + cartDirectory);
            }

        }else{
            System.out.println("Using directory: " + cartDirectory);
        }




        //arraylist to hold all ingredients
        ArrayList<String> groceryList = new ArrayList<>();
        //HashSet to hold  unique user list
        HashSet<String> userList = new HashSet<>();

        while(true){
        //cons to hold console command
        Console cons = System.console();
        String cmd = cons.readLine("> ").trim().toLowerCase();

        String[] cmdSplit;        

        if(cmd.contains(",")){
            cmdSplit = cmd.split(" ");
            for(int i = 0; i < cmdSplit.length; i++){
                cmdSplit[i] = cmdSplit[i].replace(",","");
                }
            } else{
            cmdSplit = cmd.split(" ");
            }



        switch(cmdSplit[0]){
            //new commands

            case "login":
                String line;
                String user = cmdSplit[1];
                //store path of new user list
                File file = new File("src/homework3/" + cartDirectory + "/" + user + ".txt");

                if(userList.contains(user)){
                    //user exists, list exists
                    BufferedReader bufferedRead = new BufferedReader(new FileReader(file));
                    if((line = bufferedRead.readLine()) != null){ 
                        System.out.printf("%s, your cart contains the following items", user);
                        for(int i = 0; i < groceryList.size(); i++){
                            System.out.printf("%d. %s\n", i+1, groceryList.get(i));
                        }
                    }else{
                        System.out.printf("%s, your cart is empty", user);
                    }
                }else{
                    //user does not exist, list does not exist
                    //create user, add to user list, and create user's list
                     //will get created in 'user' directory (homework)
                    userList.add(user);
                    file.createNewFile();

                }
            break;

            case "users":
            break;

            case "save":
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

