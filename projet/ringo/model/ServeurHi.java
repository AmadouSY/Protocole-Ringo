package ringo.model;

public class ServeurHi{
	public static void main(String[] args){
	  int compteurMess=0;
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
	  System.out.println(mess);
	
	}
}
