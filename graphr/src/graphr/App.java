package graphr;

import graphr.data.GraphHashtable;
import graphr.data.PrimData;

public class App {

	public static void main(String[] args) {
	
		System.out.println("Hello world.");
		
		GraphHashtable gh = new GraphHashtable();
		gh.put("name", new PrimData("Anna"));
		gh.put("age", new PrimData(24));
		
	
		

	}

}
