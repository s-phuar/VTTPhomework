package homework1;

import java.util.ArrayList;
import java.io.Console;


public class GroceryList {



    //Welcome to your shopping cart 
        // > list 
            // Your cart is empty 
        // > add apple 
            // apple added to cart 
        // > add orange, pear 
            // orange added to cart 
            // pear added to cart 
        // > list 
            // 1. apple 
            // 2. orange 
            // 3. pear 
        // delete 2 
            // orange removed from cart 
        // > list 
            // 1. apple 
            // 2. pear 
        // > add apple, grapes 
            // You have apple in your cart 
            // grapes added to cart 
        // > list 
            // 1. apple 
            // 2. pear 
            // 3. grapes 
        // > delete 4 
            // Incorrect item index 


    public static void main(String[] args){

        //arraylist to hold all ingredients
        ArrayList<String> groceryList = new ArrayList<>();



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

