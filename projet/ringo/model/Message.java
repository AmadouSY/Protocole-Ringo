package ringo.model;

import java.util.* ;
import java.util.regex.*;

import ringo.model.exception.InsertMessException;
import ringo.model.exception.NewCMessException;
import ringo.model.exception.UnkownMessTypeException;
import ringo.model.exception.*;

public class Message{

  public Message(String message)
  {
    this.message = message ;
    dic = new Hashtable <String, String> ();
  }

  public Hashtable <String, String> insertMess()  throws InsertMessException
  {

    String mess[] = message.split(" ");

    if(mess.length!=5)
      throw new InsertMessException(message);
    if(!mess[0].equals("WELC"))
      throw new InsertMessException(message);
    Pattern p = Pattern.compile("\\d\\d\\d\\d");
    Matcher m = p.matcher(mess[2].trim());
    if(!m.matches())
      throw new InsertMessException(message);
    p = Pattern.compile("\\d\\d\\d\\d");
    m = p.matcher(mess[4].trim());
    if(!m.matches())
        throw new InsertMessException(message);
    p = Pattern.compile("\\d\\d\\d.\\d\\d\\d.\\d\\d\\d.\\d\\d\\d");
    m = p.matcher(mess[1]);
    if(!m.matches())
      throw new InsertMessException(message);
    p = Pattern.compile("\\d\\d\\d.\\d\\d\\d.\\d\\d\\d.\\d\\d\\d");
    m = p.matcher(mess[3]);
    if(!m.matches())
      throw new InsertMessException(message);
    dic.put("type",mess[0].trim());
    dic.put("ip",mess[1].trim());
    dic.put("port",mess[2].trim());
    dic.put("ip-diff",mess[3].trim());
    dic.put("port-diff",mess[4].trim());
    return dic ;

  }
  public Hashtable <String, String> newCMess()  throws NewCMessException
  {
    String mess[] = message.split(" ");
    if(mess.length!=3)
      throw new NewCMessException(message);
    if(!mess[0].equals("NEWC") && !mess[0].equals("DUPL"))
      throw new NewCMessException(message);
    Pattern p = Pattern.compile("\\d\\d\\d\\d");
    Matcher m = p.matcher(mess[2].trim());
    if(!m.matches())
      throw new NewCMessException(message);
    p = Pattern.compile("\\d\\d\\d.\\d\\d\\d.\\d\\d\\d.\\d\\d\\d");
    m = p.matcher(mess[1]);
    if(!m.matches())
      throw new NewCMessException(message);
    dic.put("type",mess[0].trim());
    dic.put("ip",mess[1].trim());
    dic.put("port",mess[2].trim());
    return dic ;

  }

  public Hashtable <String, String> handleUDPMess() throws UnkownMessTypeException
  {
    try
    {
      String mess[] = message.split(" ");

      if(mess[0].trim().equals("WHOS"))
        return whosMess(mess);

      if(mess[0].trim().equals("MEMB"))
        return membMess(mess);

      if(mess[0].trim().equals("GBYE"))
        return gbyeMess(mess);

      if(mess[0].trim().equals("EYBG"))
          return eybgMess(mess);

      if(mess[0].trim().equals("TEST"))
          return testMess(mess);

      if(mess[0].trim().equals("DOWN"))
          return downMess(mess);

      throw new UnkownMessTypeException(mess[0]);
    }
    catch(Exception e)
    {
      System.out.println(e.getMessage());
      e.printStackTrace();
      return null ;
    }
  }

  private Hashtable <String,String> gbyeMess(String [] mess)
    throws GbyeMessException, IDFormatException, PortFormatException, IPFormatException
  {
    if(mess.length!=6)
      throw new GbyeMessException(message);
    if(mess[1].trim().length()!=8)
      throw new IDFormatException(mess[1]);
    if(mess[2].trim().length()!=15 || !checkIP(mess[2]))
      throw new IPFormatException(mess[2]);
    if(mess[3].length()!=4 || !checkPort(mess[3]))
      throw new PortFormatException(mess[3]);
    if(mess[4].trim().length()!=15 || !checkIP(mess[4]))
      throw new IPFormatException(mess[4]);
    if(mess[5].trim().length()!=4 || !checkPort(mess[5]))
      throw new PortFormatException(mess[5]);

    dic.put("type","protocole");
    dic.put("query","gbye");
    dic.put("idm",mess[1].trim());
    dic.put("ip",mess[2].trim());
    dic.put("port",mess[3].trim());
    dic.put("ip-succ",mess[4].trim());
    dic.put("port-succ",mess[5].trim());
    return dic ;
  }

  private Hashtable <String,String> membMess(String [] mess)
    throws MembMessException, IDFormatException, PortFormatException, IPFormatException
  {
    if(mess.length!=5)
      throw new MembMessException(message);
    if(mess[1].trim().length()!=8)
      throw new IDFormatException(mess[1]);
    if(mess[2].trim().length()!=8 )
      throw new IDFormatException(mess[2]);
    if(mess[3].trim().length()!=15 || !checkIP(mess[3]))
      throw new IPFormatException(mess[3]);
    if(mess[4].trim().length()!=4 || !checkPort(mess[4]))
      throw new PortFormatException(mess[4]);
    dic.put("type","protocole");
    dic.put("query","memb");
    dic.put("idm",mess[1].trim());
    dic.put("id",mess[2].trim());
    dic.put("ip",mess[3].trim());
    dic.put("port",mess[4].trim());
    return dic ;
  }

  private Hashtable <String,String> testMess(String [] mess)
    throws TestMessException, IDFormatException, PortFormatException, IPFormatException
    {
      if(mess.length!=4)
        throw new TestMessException(message);
      if(mess[1].trim().length()!=8)
        throw new IDFormatException(mess[1]);
      if(mess[2].trim().length()!=15 || !checkIP(mess[2]))
        throw new IPFormatException(mess[2]);
      if(mess[3].trim().length()!=4 || !checkPort(mess[3]))
        throw new PortFormatException(mess[3]);
      dic.put("type","protocole");
      dic.put("query","test");
      dic.put("idm",mess[1].trim());
      dic.put("ip-diff",mess[2].trim());
      dic.put("port-diff",mess[3].trim());
      return dic ;
    }
  private Hashtable <String,String> eybgMess(String []mess)
    throws EybgMessException, IDFormatException, PortFormatException, IPFormatException
  {
    if(mess.length!=2)
       throw new EybgMessException(message);
    if(mess[1].trim().length()!=8)
      throw new IDFormatException(mess[1]);
    dic.put("type","protocole");
    dic.put("query","eybg");
    dic.put("idm",mess[1].trim());
    return dic ;
  }

  private Hashtable <String,String> downMess(String []mess)
    throws DownMessException, IDFormatException, PortFormatException, IPFormatException
  {
    if(mess.length!=1)
       throw new DownMessException(message);
    dic.put("type","protocole");
    dic.put("query","down");
    return dic ;
  }

  private Hashtable <String,String> whosMess(String [ ]mess)
   throws WhosMessException, IDFormatException, PortFormatException, IPFormatException
  {
    if(mess.length!=2)
       throw new WhosMessException(message);
    if(mess[1].trim().length()!=8)
      throw new IDFormatException(mess[1]);
    dic.put("type","protocole");
    dic.put("query","whos");
    dic.put("idm",mess[1].trim());
    return dic ;
  }

  private boolean checkPort(String mess){

    Pattern p = Pattern.compile("\\d\\d\\d\\d");
    Matcher m = p.matcher(mess.trim());
    return m.matches();
  }

  private boolean checkIP(String mess)
  {
    Pattern p = Pattern.compile("\\d\\d\\d.\\d\\d\\d.\\d\\d\\d.\\d\\d\\d");
    Matcher m = p.matcher(mess.trim());
    return m.matches();
  }

  private String message ;
  private  Hashtable <String, String> dic ;
}
