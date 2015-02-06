package graphr;

import graphr.data.GHT;
import graphr.data.PrimData;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;

public class App {

	public static void main(String[] args) {
	
		System.out.println("Hello world.");
		
		GHT gh = new GHT();
		gh.put("name", "Anna");
		gh.put("age", 24);
		
//		String encoded = gh.toString();
//		
//		System.out.println(gh.toString());
//		
//		GHT gh2 = new GHT(encoded);
//		
//		System.out.println(gh2.toString());
//		
//		Vertex<GHT, GHT> v = new Vertex<GHT, GHT>();
//		v.setData(gh);
//		Edge<GHT, GHT> e = new Edge<GHT, GHT>();
//		e.setData(gh2);
//		
//		Graph<GHT, GHT> g = 
//				new Graph<GHT, GHT>();
//		g.addVertex(v);
//		v.addEdge(e);
//		e.setTarget(v);
		
//		PrimData p = new PrimData(2.0);
		
		
		System.out.println(gh.toJson());
		
		
		
	
	}

}
