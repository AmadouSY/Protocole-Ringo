
  /*
  *La classe Anneau définit  l'anneau auquel appartient une  entitié
  * (adresse de diffusion et  port de diffusion ) et port et adresse de
  * de l'entité suivante
  */
public class Anneau
{
  private String ip;
  private int port;

	public Anneau(String ip, int port)
	{
		this.ip = ip;
		this.port = port;
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public int getPort() {
		return port;
	}

	public void setPort(int port) {
		this.port = port;
	}


  public static String generateIdEntite()
  {
    return "" ;
  }

  private static int nbreEntite = 1 /*Nombre d'entités dans le réseau */
}
