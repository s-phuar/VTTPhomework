package homework6;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.Reader;
import java.io.Writer;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;

import java.util.Random;

public class Cookie implements Runnable{

    private final Socket sock;
    private final Path p;

    
    public Cookie(Socket s, Path p){ //pass freshly created socket from serer
        sock = s;
        this.p = p;
    }

    @Override
    public void run(){
        try (//opening stream to transmit cookie data over to client
            OutputStream os = sock.getOutputStream()) {
            Writer writer = new OutputStreamWriter(os);
            BufferedWriter bw = new BufferedWriter(writer);

            //get the input stream to receive commands from client
            InputStream is = sock.getInputStream();
            Reader reader = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(reader);

            //store the cookie txt directory
            boolean clientCmd = true;

            while(clientCmd){
            String cmd = br.readLine();
            //receive something for client to either give another cookie or close
            switch(cmd){
                case "get-cookie":
                    String theMessage = cookie(p); //cookie is non-static method of the Cookie class
                    //currently, cookie(p) is called on an instance of the Cookie class, cookie method does not need to be static here
                    //write the random FC line to the client
                    bw.write("cookie-text: " + theMessage);
                    bw.newLine();
                    bw.flush();
                    os.flush();
                break;

                case "close":
                    bw.flush();
                    br.close();
                    bw.close();
                    os.close();
                    sock.close();
                    System.out.println("closing connection");
                    clientCmd = false; // for some reason, we reached the end of run() but does not close naturally
                break;

                default:
                System.out.println("try again");

            }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }



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
