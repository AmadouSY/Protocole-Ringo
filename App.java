import java.util.*;
import java.io.*;
import java.net.*;
public class App{

	public static void main(String args[]){
		System.out.print("[addresse port message] : ");
	      	Scanner sc = new Scanner(System.in);
	      	String message = sc.nextLine();
		sc.close();
	      	String[] split = message.split(" ", 3);
	      	int idm = 1;
		String ip = split[0];
		int port = Integer.parseInt(split[1]);
		String mess = split[2];
	      	
	        message = "APPl "+idm+" TRANS### REQ "+mess.length()+" "+mess;
		//message = "APPl "+idm+" DIFF### "+mess.length()+" "+mess;
	      	System.out.println(message);
		//diffusion(ip, port, message);
		transfert(ip, port, message);
	}

	/**
	 * Application de diffusion
	 * @param ip adresse 
	 * @param port le port 
	 * @param message la requete
	 */
	public static void diffusion(String ip, int port, String message){
	 	/*try { 
		      //connexion au serveur TCP de l'entité
		      Inet4Address entity_address = (Inet4Address) InetAddress.getByName(ip);;
		      int entity_TCP =port;
		      Socket sock = new Socket(entity_address, entity_TCP);
		      BufferedReader br = new BufferedReader(new InputStreamReader(sock.getInputStream()));
		      PrintWriter pw = new PrintWriter(new OutputStreamWriter(sock.getOutputStream()));

		      pw.print(message);
		      pw.flush();*/
		try{
			//Connexion 
  			DatagramSocket dso = new DatagramSocket();
 			byte[]data = message.getBytes();
			DatagramPacket paquet = new DatagramPacket(data,data.length,InetAddress.getByName(ip),port);
			dso.send(paquet);

	    	}catch(Exception e){
		      e.printStackTrace();
		}
	}

	/**
	 * Application de transfer de fichier
	 * @param ip adresse 
	 * @param port le port 
	 * @param message la requete
	 */
	public static void transfert(String ip, int port, String message){
		try{
			
			//Connexion UDP
  			DatagramSocket dso = new DatagramSocket();
 			byte[] data = message.getBytes();
			DatagramPacket paquet = new DatagramPacket(data,data.length,InetAddress.getByName(ip),port);
			dso.send(paquet);

			dso = new DatagramSocket(5555);
			
			paquet = new DatagramPacket(data,data.length);
			dso.receive(paquet);

			String response = new String(paquet.getData(),0,paquet.getLength());
      			String [] split = response.split(" ");

			if(split.length==7 && split[3].equals("ROK")){
			
				int id_trans = Integer.parseInt(split[4]);
				String filename = split[6];
				int nummess = Integer.parseInt(split[7]);
				for(int i=0; i<nummess; i++){
					System.out.println("ok send");
				}
			}


		} catch (Exception e){
			e.printStackTrace();	
		}
	}

}
