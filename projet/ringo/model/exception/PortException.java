package ringo.model.exception;


/*PortException est levée si le port est > 9999*/

public class PortException extends Exception
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/* String port = UDP ou TCP ou diffusion
    * valuePort : le port demandé par l'utilisateur
    */
    public PortException(String port, int valuePort)
    {
      this.port = port ;
      this.valuePort = valuePort ;
    }

    public String getMessage()
    {
      return port+" valeur souhaitée: "+valuePort+" est supérieure à 9999\n La valeur d'un port doit être < 9999\n";

    }

    String port ;
    int valuePort ;

}
