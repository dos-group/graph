package graphr;

import graphr.data.GraphHashtable;
import graphr.data.PrimData;

public class App {

	public static void main(String[] args) {
	
		System.out.println("Hello world.");
		
		GraphHashtable gh = new GraphHashtable();
		gh.put("name", "Anna");
		gh.put("age", 24);
		gh.put("income", 1283.32);
		gh.put("vegan", true);
		
		System.out.println(gh.toString());
		

	}

}
