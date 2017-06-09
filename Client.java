import java.io.IOException;
import java.util.*;
import java.net.*;
import java.io.*;


public class Client {


    public static void main(String[] zero){


       PrintWriter out = null;
       BufferedReader in = null;
       Socket socket;

        String lec;
        try {

        socket = new Socket("localhost",2009);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

        out = new PrintWriter(socket.getOutputStream());
        lec=in.readLine();
        System.out.println(lec);
        out.println("NEWC ip port");
        out.flush();
        lec=in.readLine();
        System.out.println(lec);


        socket.close();

        } catch (IOException e) {



            e.printStackTrace();

        }

    }

}
