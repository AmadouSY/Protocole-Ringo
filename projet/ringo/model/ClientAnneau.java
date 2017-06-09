package ringo.model;

import java.net.*;
import java.util.*;
public class ClientAnneau {
  public  static void main(String[] args){
    try{
        Scanner sc = new Scanner(System.in);
        DatagramSocket dso=new DatagramSocket();
        byte[]data;
        for(;;){

          /**************************************************************************/
          System.out.println("Choissiez une action ");
          System.out.println("1==> WHOS \n 2 ==> MEMB \n 3 ==> GBYE \n 4 ==> TEST ");
          String choix = sc.nextLine() ;
          data=choix.getBytes();
          DatagramPacket paquet=new
          DatagramPacket(data,data.length,
          InetAddress.getByName("localhost"),5555);
          dso.send(paquet);
      }
    }
    catch(Exception e){
      e.printStackTrace();
    }
  }
}
