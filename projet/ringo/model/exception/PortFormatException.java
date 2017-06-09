package ringo.model.exception;

public class PortFormatException extends Exception
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/* message = message reçu du client
  */
  public PortFormatException (String message)
  {
    this.message = message ;
  }

  public String getMessage()
  {
    return "Message reçu: "+message+". Le message est mal formé\n "+
    "les  ports doivent être codés sur 4 octects ";

  }

  String message ;
}
