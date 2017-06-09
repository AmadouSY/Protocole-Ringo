package ringo.model.exception;

public class IPFormatException extends Exception
{
  /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

/* message = message reçu du client
  */
  public IPFormatException (String message)
  {
    this.message = message ;
  }

  public String getMessage()
  {
    return "Message reçu: "+message+". Le message est mal formé\n "+
    "les adresses ip doivent être codés sur 15 octects ";

  }

  String message ;
}
