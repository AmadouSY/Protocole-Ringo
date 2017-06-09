package ringo.model.exception;

public class TestMessException extends Exception{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/* message = message reçu du client
 */
 public TestMessException (String message)
 {
   this.message = message ;
 }

 public String getMessage()
 {
   return "Message reçu: "+message+". Le message est mal formé\n "+
   "Format attendu: [TEST␣idm␣ip-diff␣port-diff\\n]";

 }

 String message ;
}
