package ringo.model;

import ringo.model.exception.PortException;

public class ApplicationTest
{

  public static void main(String argv[])
  {
    int  portDiff=0 ;
    int  portTCP ;
    int  portUDP ;
    int portUDPClient ;
    String adresseDiff ="";
    int i = 0;
    boolean nouveau = false ;
    boolean param =false ;

    if(argv[0].equals("-new"))
    {
      if(argv.length!=6)
      {
        System.out.println("Erreur arguments programme ; java Ringo -new portUDP portTcp portDiff AdresseDiff portUDPClient");
        return ;
      }
      i++ ;
      nouveau = true ;
      param = true ;

    }
    if(argv[0].equals("-join"))
    {
      if(argv.length!=6)
      {
        System.out.println("Erreur arguments programme ; java Ringo -join portUDP portTcp portA AdresseA portUDPClient ");
        return ;
      }
      i++ ;
      param = true ;

    }


    try
    {
      portUDP = Integer.parseInt(argv[i]);
      if(!Anneau.portavaible(portUDP,"Port UDP"))
      {
        System.out.println("Port UDP non disponible");
        return ;

      }

      portUDPClient = Integer.parseInt(argv[argv.length-1]);System.out.println(portUDPClient);
      if(!Anneau.portavaible(portUDPClient,"Port UDP Client"))
      {
        System.out.println("Port UDP Client non disponible");
        return ;

      }
      portTCP = Integer.parseInt(argv[i+1]);
      if(!Anneau.portavaible(portTCP,"Port TCP"))
      {
        System.out.println("Port TCP non disponible");
        return ;

      }
      if(nouveau)
        {
          portDiff = Integer.parseInt(argv[i+2]);
          if(!Anneau.portavaible(portDiff,"Port Diff"))
          {
            System.out.println("Port diffusion non disponible");
            return ;

          }

          adresseDiff = argv[i+3];
        }


    }
    catch(PortException e)
    {
      System.out.println(e.getMessage());
      return ;
    }

    Entite entite=null;
    if(nouveau)
      entite = new Entite(portUDP,portTCP, portDiff, adresseDiff);
    else
      entite = new Entite(portUDP,portTCP);

    Anneau anneau = new Anneau(entite,portUDPClient);

    if(nouveau) anneau.lancerAnneau();
    else {
      if(!anneau.join(Integer.parseInt(argv[3]),argv[4])) return ;
      anneau.lancerAnneau();
    }

  }


}
