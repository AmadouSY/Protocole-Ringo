package ringo.model.app;

import java.io.FileOutputStream;
import java.io.IOException;

public class Transfert {
	
	String filename;
	int nummess;
	String idTrans;
	//String [] messages
	Byte [] messages;
	
	Transfert(String file){
		this.filename = file;
		
	}
	
	void start() throws IOException{
		FileOutputStream f = new FileOutputStream(this.filename, true);
		
		for(int i = 0; i < this.nummess; i++){
			f.write(this.messages[i]);
			f.flush();
		}
		f.close();

	}
}
