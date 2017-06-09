import java.util.ArrayList;
import java.util.*;
import java.io.*;
import java.net.*;


class Entite{

//Identifiant
static String id;

//indice des messages envoyes
static int idm;

//Port d'ecoute pour recevoir les messages UDP de l'entite precedente
int port_ecoute;

//Port d'ecoute pour recevoir des messages UDP de l'entite precedente (<9999)
int port_udp;

// port tcp
int port_tcp;

//adresse ip de la machine suivante sur l'anneau
static String adresse_IP_suiv;

//port d'ecoute de la machine suivante UDP
int port_ecoute_suiv;

/*adresse de multi_diffusion qui servira a signaler une panne sur le reseau
  choisit 224.0.0.0 et 239.255.255.255
*/
String adresse_IP_mult_diff;

//port de diffusion
int port_mutl_diff;

//entite est dupliquee ou non
boolean dup;

//Liste des applications
ArrayList<Integer>application = new ArrayList<Integer>();



public Entite(){
  this.id=identifiant();
  this.idm=100;
  this.dup=false;
  this.port_tcp=validePort("localhost",49152);
  this.adresse_IP_suiv=myIP();
  try{

    this.port_udp=portUdp(InetAddress.getLocalHost(),1024);
    this.adresse_IP_mult_diff="224.0.0.0";
    this.port_mutl_diff=portUdp(InetAddress.getByName(adresse_IP_mult_diff),1024);

  }catch (UnknownHostException e){

  }

}


//teste si le port est disponible
public boolean scanUDP(InetAddress IP, int port)
{
    try{
        byte [] bytes = new byte[128];
        DatagramSocket ds = new DatagramSocket();
        DatagramPacket dp = new DatagramPacket(bytes, bytes.length, IP, port);
        ds.setSoTimeout(1000);
        ds.send(dp);
        dp = new DatagramPacket(bytes, bytes.length);
        ds.receive(dp);
        ds.close();
        return true;
    }
    catch(IOException e){
        return false;
    }
}

//  retourne un numero de port valide
public int portUdp(InetAddress ip, int port)
  {
    while ((!(scanUDP(ip,port)))&& (port<9999)) {
        port++;

    }
    return port;
  }


// methode donnant l'identifiant de la machine grace au nom de la machine
public static String identifiant(){
  String hostname="";
  try {
        InetAddress addr=InetAddress.getLocalHost();
        hostname=addr.getHostName();
      }
  catch (Exception e ){

	}

finally{

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
        return hostname;}}


//adresse IP de la machine codé sur 15 octects
public static String myIP(){
        try{
         adresse_IP_suiv=(InetAddress.getLocalHost()).getHostAddress();
         String res="";
         String [] tab=adresse_IP_suiv.split("\\.");
         for(int i=0;i<tab.length;i++){
            if(tab[i].length()<3){
                if(tab[i].length()==1){
                  tab[i]="0"+"0"+tab[i];}
                else{
                  tab[i]="0"+tab[i];}
                }
            }
           for(int i=0;i<tab.length;i++){
                  res+=tab[i]+".";
             }

            return res.substring(0,res.length()-1);
        }
        catch(Exception e){
          return "Erreur";
        }
}

//adresse IP de diffusion
public static String ip_Diff(){
       String ip_Diff=myIP();
       String res="";
       String [] tab=ip_Diff.split("\\.");
       System.out.println(tab.length);
       tab[tab.length-1]="255";
       for(int i=0;i<tab.length;i++){
        res+=tab[i]+".";
        }
        return res.substring(0,res.length()-1);
}



// teste si un port est libre
private static boolean port_Use (String adresseIP,int port){
	boolean result=true;
	try{
	  (new Socket(adresseIP,port)).close();
	   result=true;
	}
	catch(Exception e){
	   return false;
	}
	return result;
	}

// retourne un port valide
  public static int validePort(String adresseIP,int port){
    while(port_Use(adresseIP,port) && port<65535){
      port++;
    }
    return port;
  }


  public static String idMessage(){
    return id+""+idm;

  }


 }
