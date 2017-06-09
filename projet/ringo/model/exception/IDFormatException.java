package ringo.model.exception;

public class IDFormatException extends Exception
{

      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* message = message reçu du client
      */
      public IDFormatException (String message)
      {
        this.message = message ;
      }

      public String getMessage()
      {
        return "Message reçu: "+message+". Le message est mal formé\n "+
        "l'indentifiant doit être codé sur 8 octects ";

      }

      String message ;

}
