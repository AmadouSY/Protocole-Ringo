import java.io.IOException;
import java.util.*;
import java.net.*;
import java.io.*;


public class Serveur {

    public static void main(String[] zero){

        ServerSocket socket;


        try {

        socket = new ServerSocket(2009);

        Thread t = new Thread(new Accepter_clients(socket));

        t.start();

        System.out.println("Accepter une entité !");



        } catch (IOException e) {



            e.printStackTrace();

        }

    }

}


class Accepter_clients implements Runnable {


       private ServerSocket socketserver;
       private Socket socket;
       private PrintWriter out = null;
       private BufferedReader in = null;
       private int nbrclient = 1;

        public Accepter_clients(ServerSocket s){
            socketserver = s;

        }



        public void run() {

            String lec="";


            try {

                while(true){

              socket = socketserver.accept(); // Un client se connecte on l'accepte
              in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
              out = new PrintWriter(socket.getOutputStream());
                      out.println("WELC ip port ip-diff port-diff");
                      out.flush();
                      //System.out.println("Le client numéro "+nbrclient+ " est connecté !");
                      lec=in.readLine();
                      System.out.println(lec);
                      Thread.sleep(9000);
                      out.println("ACKC");
                      out.flush();
                      nbrclient++;

                      socket.close();

                }



            } catch (Exception e) {

                e.printStackTrace();

            }

        }


    }
