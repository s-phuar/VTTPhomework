package homework6;

import java.io.Console;
import java.io.IOException;

import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//javac -d fortunecookie.jar --source-path src src/homework6/*.java
//java -cp fortunecookie.jar homework6.fcServer 12345 cookie_file.txt
//java -cp fortunecookie.jar homework6.fcClient localhost:12345


public class fcServer {

    static volatile boolean running = true;

    public static void main(String[] args)throws IOException{

        // ExecutorService thrPool = Executors.newSingleThreadExecutor();
        ExecutorService thrPool = Executors.newFixedThreadPool(3); //only allows 2 client connections
            //the thread pool can only handl 2 concurrents tasks at a time
            //third client has to wait unitl 1 other client releases the thread


        String threadName = Thread.currentThread().getName();
        int port = Integer.parseInt(args[0]);
        Path p = Paths.get("src/homework4/" + args[1]);
        ServerSocket server = new ServerSocket(port);
        System.out.printf("waiting for connection on: %s at port: %d\n", threadName, port);

        //shutdown logic, new thread that continoulsy looks out for shutdown command, seperate from client handling loop
        //unable to shutdown until all clients close their connections manually
        new Thread(() -> {
            try (Scanner scanner = new Scanner(System.in)) {
                while (running) { //while running is true, we are continously scanning
                    if (scanner.nextLine().equalsIgnoreCase("shutdown")) {
                    System.out.println("shutting down the server...");
                    running = false;
                    thrPool.shutdown(); //close all the thread resources
                    server.close();     //closes the serverside
                    scanner.close();
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start(); //start the thread to scan


        //handles connections
        while(running){
            try{
                Socket sock = server.accept();                      //continously accept client connection
                System.out.println("connected to cookies list!"); //only prints if we are connected with a client
                Cookie c = new Cookie(sock, p);                     //run implementation

                // thrPool.execute(c); //for single thread
                thrPool.submit(c);                                  //for multiple, submits cookie handler to the thread pool
            }catch (IOException e) {
                if (running) {
                    System.err.println("Connection error: " + e.getMessage());
                }

            }
    }
}
}

