package ringo.model.exception;


public class UnkownMessTypeException extends Exception
{

      /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* message = message reçu du client
      */
      public UnkownMessTypeException(String message)
      {
        this.message = message ;
      }

      public String getMessage()
      {
        return "La requête de type: "+message+" est inconnue";

      }

      String message ;

}
