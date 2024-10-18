package homework6;

import java.io.IOException;

import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

//javac -d fortunecookie.jar --source-path src src/homework6/*.java
//java -cp fortunecookie.jar homework6.fcServer 12345 cookie_file.txt
//java -cp fortunecookie.jar homework6.fcClient localhost:12345


public class fcServer {

    public static void main(String[] args)throws IOException{

        //executor to manage threads and execute tasks in java
        ExecutorService thrPool = Executors.newSingleThreadExecutor();
            // ExecutorService thrPool = Executors.newFixedThreadPool(1);


        String threadName = Thread.currentThread().getName();

        int port = Integer.parseInt(args[0]);
        Path p = Paths.get("src/homework4/" + args[1]);
        ServerSocket server = new ServerSocket(port);
        System.out.printf("waiting for connection on: %s at port: %d\n", threadName, port);
        Socket sock = server.accept();

        System.out.println("connected to cookies list!"); //only prints if we are connected

        
        Cookie c = new Cookie(sock, p);
        thrPool.execute(c);
            //thrPool.submit(c);
        System.out.println(Thread.currentThread().isAlive());
        thrPool.shutdown(); //force close the thread. probably only good for single thread
        server.close();


        
    }
}
