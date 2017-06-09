package ringo.model.exception;

public class EybgMessException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/* message = message reçu du client
 */
 public EybgMessException (String message)
 {
   this.message = message ;
 }

 public String getMessage()
 {
   return "Message reçu: "+message+". Le message est mal formé\n "+
   "Format attendu: [EYBG␣idm\\n]";

 }

 String message ;
}
