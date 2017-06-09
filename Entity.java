import java.io.*;
import java.net.*;

public class Entity{
  String id;
  String udp; //port d'écoute UDP//
  String tcp; //port d'écoute TCP//
  String ip; //adresse IP de la machine suivante sur l'anneau//
  String nextUDP; //port d'écoute de la machine suivante sur l'anneau//
  String ipv4; // adresse de multidiffusion
  String mutiDiff; //port de multi diffusion

  public Entity(){


  }

  // méthode donnant l'identifiant de la machine grace au nom de la machine
public static String identifiant(){
  String hostname="";
  try {
        InetAddress addr=InetAddress.getLocalHost();
        hostname=addr.getHostName();
        if (hostname.length()>8){
            hostname=hostname.substring(0,8);
        }
         if (hostname.length()<8){
            String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
            int restant=8-hostname.length();
            for(int r=1;r<=restant;r++){
                    int e=(int)(Math.random()*chars.length());
                    hostname+=""+chars.charAt(e);
                    }
            }
            return hostname;}

  catch (Exception e ){
      e.printStackTrace();
	   }
    }






}
