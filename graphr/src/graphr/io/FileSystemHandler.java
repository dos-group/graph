package graphr.io;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class FileSystemHandler {
	
	private static FileSystemHandler instance;
	
	//-- Singleton
	
	public static FileSystemHandler getInstance() {
		if (instance == null) {
			instance = new FileSystemHandler();
		}
		
		return instance;
	}
	
	//-- Write
	
	public void write(String s, String fullFilePath) {
		
		try {
			FileOutputStream fs = new FileOutputStream(fullFilePath);
			DataOutputStream ds = new DataOutputStream(fs); 
			ds.writeBytes(s);
			
			ds.close();
		} catch (IOException e) {
			System.out.println("Exception occured: " + e);
		}
		
	}

}
