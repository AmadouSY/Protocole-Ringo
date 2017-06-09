package ringo.model.exception;


/*insertMessException est  levée si le  un le message recu lors de l'insertion est mal formée*/

public class InsertMessException extends Exception
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* message = message reçu du client
    */
    public InsertMessException(String message)
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
