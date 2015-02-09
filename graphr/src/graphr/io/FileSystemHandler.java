package graphr.io;

import java.io.DataOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;


public class FileSystemHandler {

	private static FileSystemHandler instance;

	// -- Singleton

	public static FileSystemHandler getInstance() {
		if (instance == null) {
			instance = new FileSystemHandler();
		}

		return instance;
	}

	// -- Write

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
	
	//-- Read

	public String read(String fullFilePath) {

		StringBuffer sb = new StringBuffer();
		BufferedReader br = null;

		try {

			String sCurrentLine;
			br = new BufferedReader(new FileReader(fullFilePath));

			while ((sCurrentLine = br.readLine()) != null) {
				sb.append(sCurrentLine);
			}

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (br != null)
					br.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		
		return sb.toString();
	}
	
//	public String readJsonObject(String fullFilePath) {
//		
//		return null;
//
//	}

}
