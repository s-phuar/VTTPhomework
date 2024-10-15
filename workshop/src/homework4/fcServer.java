package homework4;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.*;
import java.nio.file.Path;
import java.nio.file.Paths;

//javac -d fortunecookie.jar --source-path src src/homework4/*.java
//java -cp fortunecookie.jar homework4.fcServer 12345 cookie_file.txt
//java -cp fortunecookie.jar homework4.fcClient localhost:12345


public class fcServer {

    public static void main(String[] args)throws IOException{

        int port = Integer.parseInt(args[0]);
        ServerSocket server = new ServerSocket(port);
        Socket sock = server.accept();
        System.out.println("connected to cookies list!"); //only prints if we are connected

        //opening stream to transmit cookie data over to client
        OutputStream os = sock.getOutputStream();
        Writer writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);

        //store the cookie txt directory
        Path p = Paths.get("src/homework4/" + args[1]);
        Cookie c = new Cookie();
        String theMessage = c.cookie(p);

        //write the random FC line to the client
        bw.write(theMessage);
        bw.flush();
        os.flush();

        bw.close();
        os.close();
        sock.close();
        server.close();
    

    }
    

}
