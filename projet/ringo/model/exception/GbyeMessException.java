package ringo.model.exception;

public class GbyeMessException extends Exception
{

      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* message = message reçu du client
      */
      public GbyeMessException(String message)
      {
        this.message = message ;
      }

      public String getMessage()
      {
        return "Message reçu: "+message+". Le message est mal formé\n "+
        "Format attendu: [GBYE␣idm␣ip␣port␣ip-succ␣port-succ\\n]";

      }

      String message ;

}
