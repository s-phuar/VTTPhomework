package homework4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
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
        System.out.println("waiting for connection");
        Socket sock = server.accept();
        System.out.println("connected to cookies list!"); //only prints if we are connected

        //opening stream to transmit cookie data over to client
        OutputStream os = sock.getOutputStream();
        Writer writer = new OutputStreamWriter(os);
        BufferedWriter bw = new BufferedWriter(writer);

        //get the input stream to receive commands from client
        InputStream is = sock.getInputStream();
        Reader reader = new InputStreamReader(is);
        BufferedReader br = new BufferedReader(reader);

        //store the cookie txt directory
        Path p = Paths.get("src/homework4/" + args[1]);


        boolean clientCmd = true;

        while(clientCmd){
        String cmd = br.readLine();
        //receive something for client to either give another cookie or close
        switch(cmd){
            case "get-cookie":
                Cookie c = new Cookie();
                String theMessage = c.cookie(p);
                //write the random FC line to the client
                bw.write("cookie-text: " + theMessage);
                bw.newLine();
                bw.flush();
                os.flush();
            break;

            case "close":
                System.out.println("closing conenction");
                bw.close();
                os.close();
                sock.close();
                server.close();
                clientCmd = false;
            break;

            default:
            System.out.println("try again");

        }
        }
    }
}
