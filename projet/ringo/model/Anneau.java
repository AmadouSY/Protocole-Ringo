package ringo.model;

import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.channels.Selector;
import java.nio.channels.SelectionKey;
import java.nio.channels.DatagramChannel;
import java.nio.ByteBuffer;
import java.io.IOException;

import ringo.model.exception.PortException;

import java.util.Iterator;
import java.net.InetSocketAddress;
import java.net.SocketAddress ;
import java.net.NetworkInterface;
import java.net.StandardSocketOptions ;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.io.*;
import java.net.*;
import java.util.*;

  /*
  *La classe Anneau définit  l'anneau auquel appartient une  entitié
  * (adresse de diffusion et  port de diffusion ) et port et adresse de
  * de l'entité suivante
  */
public class Anneau
{

  public Anneau(Entite entite,int port)
  {
    this.entite = entite ;
    portClient = port;
    compteurMess = 0 ;
    messages = new ArrayList <String>();
    ByteBuffer.allocate(RingoConstants.BUFFER_SIZE);
    //ip = myIP();

  }

  /*Pour configurer le port UDP pour les messages  e*/
  private boolean configUDPChannel(int port,int operation)

  {
    try
    {
      msgUDPChannel= DatagramChannel.open();
      SocketAddress  adress = new InetSocketAddress(port);
      msgUDPChannel.bind(adress);
      msgUDPChannel.configureBlocking(false);
      msgUDPChannel.register(sel,operation);
    }
    catch (Exception e)
    {
      return false ;
    }
    return true ;
  }

  /*Pour configurer le port UDP pour les messages  e*/
  private boolean configUDPChannel2(int port,int operation)
  {
    try
    {
        clientChannel= DatagramChannel.open();
        SocketAddress  adress = new InetSocketAddress(port);
        clientChannel.bind(adress);
        clientChannel.configureBlocking(false);
        clientChannel.register(sel,operation);
    }
    catch (Exception e)
    {
      return false ;
    }
      return true ;
  }
  /*Configuration d'un port de multidiffusion */
  private boolean configMultiDiff(int index, int port, String adresse ,int operation)
  {
    try
    {
      msgDiffChannel[index] = DatagramChannel.open();
      NetworkInterface netif = NetworkInterface.getByName("eth0");
      msgDiffChannel[index].setOption(StandardSocketOptions.SO_REUSEADDR, true);
      msgDiffChannel[index].configureBlocking(false);
      InetSocketAddress group = new InetSocketAddress(adresse,port);
      msgDiffChannel[index].bind(new InetSocketAddress(group.getPort()));
      msgDiffChannel[index].setOption(StandardSocketOptions.IP_MULTICAST_IF, netif);
      msgDiffChannel[index].join(group.getAddress(), netif);
      msgDiffChannel[index].register(sel,operation);

    }
    catch(Exception e)
    {e.printStackTrace();return false ;}
    return true ;

  }

  /*Configuration d'un port tcp */
  private  boolean  configTCP( int port)
  {
    try
    {

      tcpChannel = ServerSocketChannel.open();
      tcpChannel.configureBlocking(false);
      tcpChannel.socket().bind(new InetSocketAddress(port));
      tcpChannel.register(sel,SelectionKey.OP_ACCEPT);
    }
    catch(Exception e)
    {
      System.out.println("Erreur Configuration TCP ");
      return false ;
    }
    return true ;
  }

  /*Gérerer Id d'un entité */
  public static byte[] generateId()
  {

    String hostname="";
    try
    {
       InetAddress addr=InetAddress.getLocalHost();
        hostname=addr.getHostName();
     }
     catch (Exception e ){
       System.out.println("Erreur Generation indentifiant \n Message de InetAddress:"+e.getMessage());
       return null ;
     }

     if (hostname.length()>5){
       hostname=hostname.substring(0,5);
     }

    if (hostname.length()<8){
       String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
       int restant=8-hostname.length();

       for(int r=1;r<=restant;r++){
               int e=(int)(Math.random()*chars.length());
               hostname+=""+chars.charAt(e);
        }

    }

     return  hostname.getBytes();

   }

   /*Pour vérifier si le port est utilisable */

  public static boolean portavaible(int port,String from) throws PortException
  {
     if ( port > RingoConstants.MAX_PORT_NUMBER) {
       throw new PortException(from,port);

     }

     ServerSocket ss = null;
     DatagramSocket ds = null;
     try {
         ss = new ServerSocket(port);
         ss.setReuseAddress(true);
         ds = new DatagramSocket(port);
         ds.setReuseAddress(true);
         return true;
     } catch (IOException e) {
     } finally {
         if (ds != null) {
             ds.close();
         }

         if (ss != null) {
             try {
                 ss.close();
             } catch (IOException e) {
                 /* should not be thrown */
             }
         }
     }

     return false;

  }
  /*Insertion */
  public boolean addEntite(boolean doubleur,SocketChannel sc)
  {
    try{
      if(doubleur)
      {
        return true ;
      }
      String message = "";
      if(entite.getNext())
      {
          message = "WELC "+entite.getAdressNext()+" "+entite.getPortNext()+" "+entite.getAdressDiff()
          +" "+entite.getPortDiff()+"\n";
      }
      else
      {

        message = "WELC "+myIP()+" "+entite.getPortUDP()+" "+entite.getAdressDiff()+" "+entite.getPortDiff()+"\n";
      }
        ByteBuffer buffer = ByteBuffer.wrap(message.getBytes());
        sc.write(buffer);
        ByteBuffer buff = ByteBuffer.allocate(RingoConstants.BUFFER_SIZE);
        String h ;
        while (sc.read(buff)==0);
        h = new String(buff.array());
        Message _message = new Message(h.replace("\n",""));
        System.out.println("message recu :"+h.replace("\n",""));
        Hashtable<String, String>  result = _message.newCMess();
        System.out.println("result "+result);
        if(result.get("type").equals("DUPL"))
        {
          return true ;
        }
        entite.setNext(true);
        String port = new String(result.get("port"));

        entite.setPortNext(Integer.parseInt(port));
        entite.setAdressNext(result.get("ip"));
        message = "ACKC\n";
        buffer = ByteBuffer.wrap(message.getBytes());
        sc.write(buffer);
        System.out.println("Après insertion d'une entite");
        System.out.println(entite);
        return true ;
    }
    catch(Exception e)
    {
      System.out.println("Erreur dans addentite");
      System.out.println("Message exception: "+e.getMessage());
      e.printStackTrace();
      return false ;
    }
  }

  /*Initialiser un nouvel anneau */
  public void  lancerAnneau()
  {
    //String ip = myIP();
    try
    {
      sel = Selector.open();
      System.out.println("Initialisation de l'anneau ");
      System.out.println(entite);

      if(!configTCP(entite.getPortTCP()))
      {
        System.out.println("Erreur dans configTCP");
        return ;
      }
      if(!configUDPChannel(entite.getPortUDP(),SelectionKey.OP_READ))
      {
        System.out.println("Erreur dans configUDP");
        return ;
      }
      if(!configUDPChannel2(portClient,SelectionKey.OP_READ))
      {
        System.out.println("Erreur dans configUDP Client");
        return ;
      }
    if(!configMultiDiff(0,entite.getPortDiff(),entite.getAdressDiff(),SelectionKey.OP_READ|SelectionKey.OP_WRITE))
      {
        System.out.println("Erreur dans configUDP Multidiff");
        return ;
      }

      suivant1 = new DatagramSocket();
      while(true)
      {
        sel.select();
        Iterator<SelectionKey> it=sel.selectedKeys().iterator();
        while(it.hasNext())
        {

          SelectionKey sk=it.next();
          it.remove();
          if(sk.isAcceptable() && sk.channel()==tcpChannel && !entite.doubleur())
          {
            System.out.println("=============================Connection TCP ================================") ;
            SocketChannel client = tcpChannel.accept();
            client.configureBlocking(true);
					  System.out.print("Connection Accepted: " + client.getLocalAddress() + "\n");
            if(!addEntite(false,client)) System.out.println("L\'insertion à échoué ");
            client.close();
          }

          if(sk.isReadable() && sk.channel()==msgDiffChannel[0])
          {
            ByteBuffer buff = null ;
            buff = ByteBuffer.allocate(RingoConstants.BUFFER_SIZE);
            System.out.println("=========================Message de multidiffusion============================") ;
            msgDiffChannel[0].receive(buff);
            String mess =new String(buff.array(),0,buff.array().length);
            buff.clear();
            System.out.println("Message reçu: "+mess);
          }

          if(sk.isReadable() && sk.channel()==msgUDPChannel)
          {

            System.out.println("*********************Message UDP**********************");
            ByteBuffer buff = null ;
            buff = ByteBuffer.allocate(RingoConstants.BUFFER_SIZE);
            msgUDPChannel.receive(buff);
            String mess =new String(buff.array(),0,buff.array().length);
            buff.clear();
            System.out.println("Message reçu: "+mess);
            Message message = new Message(mess);
            Hashtable <String,String> table = message.handleUDPMess();
            if(table==null){buff.clear(); continue ;}
            if(!entite.getNext()) continue ;
            System.out.println("Traitement de la requête");
            if(table.get("type").equals("protocole"))
            {
              if(table.get("query").equals("whos"))
              {
                if(messages.contains(table.get("idm"))) {
                  System.out.println("Le message a fait le tour de l'anneau ");
                  continue ;
                }
                System.out.println("Envoi du message:"+mess+" à l'entité suivante");
                byte [] b = mess.getBytes();
                DatagramPacket paquet=new
                DatagramPacket(b,b.length,InetAddress.getByName(entite.getAdressNext()),entite.getPortNext());
                suivant1.send(paquet);
                String mess2 = "MEMB "+genererMessage()+" "+entite.getId()+" "+myIP()+" "+entite.getPortUDP()+"\n";
                byte[] buffer ;
                buffer = mess2.getBytes();
                System.out.println("Envoi du message MEMB après reception d'un WHOS:"+mess2+"à l'entité suivante");
                DatagramPacket p=new
                DatagramPacket(buffer,buffer.length,InetAddress.getByName(entite.getAdressNext()),entite.getPortNext());
                suivant1.send(p);
              }
              if(table.get("query").equals("memb"))
              {
                if(messages.contains(table.get("idm"))) {
                  System.out.println("Le message a fait le tour de l'anneau ");
                  continue ;
                }
                System.out.println("Envoi du message:"+mess+" à l'entité suivante");
                byte [] b = mess.getBytes();
                DatagramPacket paquet=new
                DatagramPacket(b,b.length,InetAddress.getByName(entite.getAdressNext()),entite.getPortNext());
                suivant1.send(paquet);
              }
              if(table.get("query").equals("eybg"))
              {
                if(messages.contains(table.get("idm"))) {
                  System.out.println("Le message a fait le tour de l'anneau ");
                  break ;
                }
                System.out.println("Envoi du message:"+mess+" à l'entité suivante");
                byte [] b = mess.getBytes();
                DatagramPacket paquet=new
                DatagramPacket(b,b.length,InetAddress.getByName(entite.getAdressNext()),entite.getPortNext());
                suivant1.send(paquet);
              }
              if(table.get("query").equals("gbye"))
              {
                if(messages.contains(table.get("idm"))) {
                  System.out.println("Le message a fait le tour de l'anneau\n. ;( ????");
                  continue ;
                }
                if(table.get("ip").equals(entite.getAdressNext()) && Integer.parseInt(table.get("port"))==entite.getPortNext() )
                {
                    String m = "EYBG "+table.get("idm")+"\n";
                    byte[] b = m.getBytes();
                    DatagramPacket paquet=new
                    DatagramPacket(b,b.length,InetAddress.getByName(entite.getAdressNext()),entite.getPortNext());
                    suivant1.send(paquet);
                    System.out.println("Mon successeur vient de quitter l'anneau");
                    entite.setAdressNext(table.get("ip-succ"));
                    entite.setPortNext(Integer.parseInt(table.get("port-succ")));
                    System.out.println("Après update ==> \n"+entite);
                    continue ;

                }
                System.out.println("Envoi du message:"+mess+" à l'entité suivante");
                byte [] b = mess.getBytes();
                DatagramPacket paquet=new
                DatagramPacket(b,b.length,InetAddress.getByName(entite.getAdressNext()),entite.getPortNext());
                suivant1.send(paquet);
              }
            }
          }
          if(sk.isReadable() && sk.channel()==clientChannel)
          {
                ByteBuffer buff = null ;
                buff = ByteBuffer.allocate(RingoConstants.BUFFER_SIZE);
                System.out.println("*********************Message Client**********************");
                clientChannel.receive(buff);

                String m =new String(buff.array(),0,buff.array().length);
                System.out.println("Message client "+m);
                boolean c = false;
                String query ="";
                buff.clear();
                if(m.trim().equals("1"))
                {

                  query = "WHOS ";
                  query +=genererMessage()+"\n";
                  c = true ;
                }
                if(m.trim().equals("2"))
                {
                  query = "GBYE ";
                  query +=genererMessage()+" "+myIP()+" "+entite.getPortUDP()+" "+entite.getAdressNext()+" "+entite.getPortNext()+"\n";
                  c = true ;
                }
                if(m.trim().equals("3"))
                {
                  query = "TEST ";
                  query +=genererMessage()+" "+entite.getAdressDiff()+" "+entite.getPortDiff()+"\n";
                  System.out.println("Diffusion de message");
                  System.out.println("Contenu: "+query);
                  DatagramSocket dso=new DatagramSocket();
                  byte[] data=query.getBytes();
                  InetSocketAddress ia=new InetSocketAddress(entite.getAdressDiff(),entite.getPortDiff());
                  DatagramPacket paquet=new
                  DatagramPacket(data,data.length,ia);
                  dso.send(paquet);
                  dso.close();

                }
                if(entite.getNext()&& c)
                {
                  byte[] buffs ;
                  buffs = query.getBytes();
                  System.out.println("Envoi du message à l'entité suivante");
                  System.out.println("Message prêt a être envoyé "+query);
                  DatagramPacket paquet=new
                  DatagramPacket(buffs,buffs.length,InetAddress.getByName(entite.getAdressNext()),entite.getPortNext());
                  suivant1.send(paquet);
                }

                System.out.println("*********************************************************************");
            }

          }

          /**************************************************************************
          System.out.println("Choissiez une action ou appuyez sur Entrer pour passer");
          System.out.println("1==> WHOS \n 2 ==> MEMB \n 3 ==> GBYE \n 4 ==> TEST ");
          String choix = sc.nextLine() ;
          if(choix.equals("")) continue ;
          boolean c = false;
          String query ="";
          if(choix.equals("1"))
          {
            query = "WHOS ";
            query +=genererMessage()+"\n";
            c = true ;
          }
          if(entite.getNext()&& c)
          {
            byte[] buff ;
            buff = query.getBytes();
            System.out.println("Envoi du message à l'entité suivante");
            DatagramPacket paquet=new
            DatagramPacket(buff,buff.length,InetAddress.getByName(entite.getAdressNext()),entite.getPortNext());
            suivant1.send(paquet);
          }
          */

        }
      }



    catch(Exception e)
    {
      System.out.println("Erreur dans lancerAnneau: "+e.getMessage());
      e.printStackTrace();
    }

  }

  //adresse IP de la machine
  @SuppressWarnings("finally")
public static String myIP(){
          String ip="";
          try{
           ip=(InetAddress.getLocalHost()).getHostAddress();
           /*Enumeration<NetworkInterface> nets = NetworkInterface.getNetworkInterfaces();
            for (NetworkInterface netint : Collections.list(nets))
            {
              if(netint.getName().equals("eth0") && netint.isUp())
              {
                Enumeration<InetAddress> inetAddresses = netint.getInetAddresses();
                for (InetAddress inetAddress : Collections.list(inetAddresses)) {
                    ip = inetAddress.toString().replace("/","");
                }
              }
            }*/
          }
          catch(Exception e){
            ip="Erreur";
          }
          finally{
               String res="";
               String [] tab=ip.split("\\.");
               for(int i=0;i<tab.length;i++){
                  if(tab[i].length()==0){
                       tab[i]="0"+"0"+"0";
                   }
                   if(tab[i].length()==1){
                        tab[i]="00"+tab[i];
                    }
                    if(tab[i].length()==2){
                         tab[i]="0"+tab[i];
                     }
                }
                 for(int i=0;i<tab.length;i++){
                        res+=tab[i]+".";
                   }

                  return res.substring(0,res.length()-1);
          }
  }

  /*Cette fonction permet à l'entite de joindre un anneau */
  public boolean join(int port,String adresse)
  {
    try
    {
      System.out.println("Debut insertion");
      Socket socket=new Socket(adresse,port);
      BufferedReader br=new BufferedReader(new InputStreamReader(socket.getInputStream()));
      PrintWriter pw=new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
      String mess=br.readLine();
      System.out.println("Message recu de l'entité :"+mess);
      Message message= new Message(mess);
      Hashtable <String,String> result= message.insertMess();
      String envoi ="NEWC "+myIP()+" "+entite.getPortUDP()+"\n";
      System.out.println("Resultat de Hashtable "+result);
      pw.print(envoi);
      pw.flush();
      mess=br.readLine();
      if(mess.equals("ACKC\n"))
        return false ;
      pw.close();
      br.close();
      socket.close();
      entite.setAdressNext(result.get("ip"));
      entite.setPortNext(Integer.parseInt(result.get("port")));
      entite.setPortDiff(Integer.parseInt(result.get("port-diff")));
      entite.setAdressDiff(result.get("ip-diff"));
      entite.setNext(true);
      System.out.println("Entite après join");
      System.out.println(entite);
      return true ;
    }
    catch(Exception e){
    System.out.println(e.getMessage());
    e.printStackTrace();
    return false ;
    }
  }

  private String genererMessage()
  {
    String mess;
    String time = ""+System.currentTimeMillis();
    /*On récupere les 5 derniers caracteres*/
    mess = time.substring(time.length()-4,time.length());
    String chars = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
    int e=(int)(Math.random()*chars.length());
    mess+=""+chars.charAt(e);
    e=(int)(Math.random()*chars.length());
    mess+=""+chars.charAt(e);
    compteurMess++;
    String comp = ""+compteurMess;
    if(comp.length()<2)
      comp = "0"+comp ;
    else if(comp.length()>2)
      comp = comp.substring(0,2);
    mess +=comp ;
    messages.add(mess);
    return mess ;

  }

  private Entite entite ;
  private Selector sel ;
  /*Pour gérer les messages qui  vont circuler dans l'anneau
  * Un channel pour le port UDP de l'entité et deux autres pour les entités suivantes
  * (l'entité peut être un doubleur )
  */
  private DatagramChannel msgUDPChannel =null;
  private DatagramChannel clientChannel = null ;
  private int portClient ;
  private DatagramSocket suivant1;
  //private String ip ;
  /**
  * Pour les messages de mutdifusion
  */
  private DatagramChannel[] msgDiffChannel ={null,null} ;
  private ServerSocketChannel tcpChannel ;/* Accepter les connections tcp */
  Scanner sc = new Scanner(System.in);
  /*Buffer utilisé pour les lectures */
 //private ByteBuffer buff;
  /*Compteur pour les messages, pour mieux calculer l'id d'un message */
  private int compteurMess ;
  /*Une liste qui stocke tous les messages déjà envoyés par l'entité */
  private ArrayList <String> messages ;

}
