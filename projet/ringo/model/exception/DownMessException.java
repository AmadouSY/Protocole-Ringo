package ringo.model.exception;

public class DownMessException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/* message = message reçu du client
	 */
	public DownMessException (String message)
	{
	   this.message = message ;
	 }
	
		public String getMessage()
	 {
	   return "Message reçu: "+message+". Le message est mal formé\n "+
	   "Format attendu: [DOWN\\n]";
	   
	 }
	
	 String message ;
	}
