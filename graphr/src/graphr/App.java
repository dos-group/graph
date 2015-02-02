package graphr;

import graphr.data.GraphHashtable;

public class App {

	public static void main(String[] args) {
	
		System.out.println("Hello world.");
		
		GraphHashtable gh = new GraphHashtable();
		gh.put("name", "Anna");
		gh.put("age", 24);
		gh.put("income", 1283.32);
		gh.put("vegan", true);
		
		String encoded = gh.toString();
		
		System.out.println(gh.toString());
		
		GraphHashtable gh2 = new GraphHashtable(encoded);
		
		System.out.println(gh2.toString());

	}

}
