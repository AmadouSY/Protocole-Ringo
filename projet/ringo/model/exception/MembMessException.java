package ringo.model.exception;

public class MembMessException extends Exception{
   /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/* message = message reçu du client
  */
  public MembMessException (String message)
  {
    this.message = message ;
  }

  public String getMessage()
  {
    return "Message reçu: "+message+". Le message est mal formé\n "+
    "Format attendu: [MEMB␣idm␣id␣ip␣port\\n]";

  }

  String message ;
}
