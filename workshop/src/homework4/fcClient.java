package homework4;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Socket;

//java -cp fortunecookie.jar homework4.fcClient localhost:12345

public class fcClient {
    
    public static void main(String[] args) throws IOException, FileNotFoundException{
    String[] host = args[0].split(":");
    int port = Integer.parseInt(host[1]);

    System.out.println("Connecting to the server");
    Socket sock = new Socket(host[0], port);
    System.out.println("Connected!");

    //get the input stream to receive from server
    InputStream is = sock.getInputStream();
    Reader reader = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(reader);

    String fromServer = br.readLine();
    System.out.printf(">>> COOKIE SERVER: %s\n", fromServer);

    br.close();
    is.close();
    sock.close();


    }
}
