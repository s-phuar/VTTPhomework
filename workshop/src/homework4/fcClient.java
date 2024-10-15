package homework4;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.Console;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.net.Socket;

//java -cp fortunecookie.jar homework4.fcClient localhost:12345

public class fcClient {

    public static String getCookie(BufferedReader br) throws IOException{
        String fromServer = br.readLine(); //get-cookie
        fromServer = fromServer.replace("cookie-text: ", "");
        return fromServer;
    }
    
    public static void main(String[] args) throws IOException, FileNotFoundException{
    String[] host = args[0].split(":");
    int port = Integer.parseInt(host[1]);

    System.out.println("Connecting to the server");
    Socket sock = new Socket(host[0], port);
    System.out.println("Connected to the cookie server!");

    //get the input stream to receive from server
    InputStream is = sock.getInputStream();
    Reader reader = new InputStreamReader(is);
    BufferedReader br = new BufferedReader(reader);

    //outputsteam to send command to server from new cookie/close
    OutputStream os = sock.getOutputStream();
    Writer writer = new OutputStreamWriter(os);
    BufferedWriter bw = new BufferedWriter(writer);


    boolean open = true;
    while(open){
    //switch case + console input
    Console cons = System.console();
    String cmd = cons.readLine("> ").trim().toLowerCase();

    switch(cmd){
        case "get-cookie":
            bw.write("get-cookie"); // send command to server
            bw.newLine();
            bw.flush();
            String fromServer = getCookie(br);
            System.out.printf(">>> COOKIE SERVER: %s\n", fromServer);
        break;

        case "close":
            System.out.println("closing connection");
            bw.write("close");
            bw.newLine();
            bw.flush();
            br.close();
            is.close();
            sock.close();
            open = false;
        break;

        default:
            System.out.println("Please use a valid command:");
            System.out.println("cookie-text: to receive a fortune");
            System.out.println("close: to close connection");


    }
}




    }
}
