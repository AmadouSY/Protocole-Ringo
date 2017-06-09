package ringo.model;

import java.util.Scanner;
import java.nio.*;

/*La classe Entite definit les attributs et le comportement d'une entité
*dans le réseau
*/
class Entite
{

  public Entite(int portUDP, int portTCP)
  {
    id = Anneau.generateId();
    this.portUDP = portUDP ;
    this.portTCP = portTCP ;
    doubleur = false ;
    next = false;
  }
  public Entite(int portUDP, int portTCP,int portDiff)
  {
    id = Anneau.generateId();
    this.portUDP = portUDP ;
    this.portTCP = portTCP ;
    this.portDiff = portDiff ;
    doubleur = false ;
    next = false;
  }



  public Entite(int portUDP, int portTCP, int portDiff,String adressDiff)
  {
    this(portUDP,portTCP,portDiff);
    this.adressDiff = adressDiff;
  }


  public void setAdressDiff(String adressDiff)
  {
        this.adressDiff = adressDiff ;
  }

  public void setAdressNext(String adressNext)
  {
    this.adressNext = adressNext ;
  }


  public void setPorTCP(int portTCP)
  {
    this.portTCP = portTCP ;
  }

  public void setPortUDP(int portUDP)
  {
    this.portUDP = portUDP ;
  }

  public void setPortDiff(int portDiff)
  {
    this.portDiff = portDiff ;
  }

  public void setPortNext(int portNext)
  {
    this.portNext = portNext ;
  }

  public String getAdressDiff()
  {
        return this.adressDiff ;
  }

  public String getAdressNext()
  {
    return this.adressNext ;
  }

  public int getPortDiff()
  {
    return this.portDiff ;
  }

  public int  getPortNext()
  {
    return this.portNext;
  }

  public int  getPortTCP()
  {
    return this.portTCP;
  }

  public int  getPortUDP()
  {
    return this.portUDP;
  }


  public boolean getNext()
  {
    return next ;
  }

  public void setNext(boolean next)
  {
    this.next = next ;
  }

  public boolean doubleur()
  {
    return doubleur ;
  }

  public String getId()
  {
    return new String(id);
  }

  public void setDoubleur(boolean doubleur)
  {
    this.doubleur = doubleur ;
  }

  public String toString()
  {
    String h = new String(id) ;
    return " Entité ID: "+h +"\n Port UDP: "+portUDP+" \n Port TCP: "+portTCP+
    "\n Doubleur:"+doubleur+"\n Has Next: "+next+"\n adressDiff1: "+adressDiff+"\n Port diffusion:  "+portDiff+
    "\n Adresse suivant: "+adressNext+"\n port suivant: "+portNext+"\n Adresse ip"+Anneau.myIP();

  }

  private byte[] id =  new byte[8] ;
  private int portUDP ;
  private int portTCP ;
  private boolean doubleur ; /*True si l'entité est un doubleur */
  private boolean next ; /*False si l'entité est seule dans l'anneau afin d'éviter d'envoyer des messages inutilement */
  private String adressDiff ; /*Adresse de diffusion de l'anneau */
  private int portDiff ; /*Port de diffusion de l'anneau */
  private int portNext ;/*Port de l'entité suivante */
  private String adressNext;/*Adresse de l'entité de suivante */
  private String adressDiff2 ; /*Adresse de diffusion de l'anneau */
  private int portDiff2 ; /*Port de diffusion de l'anneau */
  private int portNext2;/*Port de l'entité suivante */
  private String adressNext2;/*Adresse de l'entité de suivante */



}
