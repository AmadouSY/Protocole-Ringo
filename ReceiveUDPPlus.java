import java.io.*;
import java.net.*;
public class ReceiveUDPPlus {
	public static void main(String[] args){
		try{
			DatagramSocket dso=new DatagramSocket(5555);
			byte[]data=new byte[100];
			DatagramPacket paquet=new DatagramPacket(data,data.length);
			while(true){
				dso.receive(paquet);
				String st=new
				String(paquet.getData(),0,paquet.getLength());
				System.out.println("J'ai reçu :"+st);
				System.out.println(
				"De la machine "+paquet.getAddress().toString());
				System.out.println("Depuis le port "+paquet.getPort());
				}
		}
		catch(Exception e){
			e.printStackTrace();
			}
	}
}
