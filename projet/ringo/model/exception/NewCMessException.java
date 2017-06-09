package ringo.model.exception;


public class NewCMessException extends Exception{

      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* message = message reçu du client
      */
      public NewCMessException(String message)
      {
        this.message = message ;
      }

      public String getMessage()
      {
        return "Message reçu: "+message+". Le message est mal formé\n "+
        "Format attendu: [WELC␣ip␣port␣ip-diff␣port-diff\\n]";

      }

      String message ;

}
