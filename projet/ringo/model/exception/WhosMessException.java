package ringo.model.exception;

public class WhosMessException extends Exception
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/* message = message reçu du client
 */
 public WhosMessException (String message)
 {
   this.message = message ;
 }

 public String getMessage()
 {
   return "Message reçu: "+message+". Le message est mal formé\n "+
   "Format attendu: [WHOS␣idm\\n]";

 }

 String message ;

}
