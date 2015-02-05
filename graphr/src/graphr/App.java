package graphr;

import graphr.data.GHT;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;

public class App {

	public static void main(String[] args) {
	
		System.out.println("Hello world.");
		
		GHT gh = new GHT();
		gh.put("name", "Anna");
		
		String encoded = gh.toString();
		
		System.out.println(gh.toString());
		
		GHT gh2 = new GHT(encoded);
		
		System.out.println(gh2.toString());
		
		Vertex<GHT, GHT> v = new Vertex<GHT, GHT>();
		v.setData(gh);
		Edge<GHT, GHT> e = new Edge<GHT, GHT>();
		e.setData(gh2);
		
		Graph<GHT, GHT> g = 
				new Graph<GHT, GHT>();
		g.addVertex(v);
		v.addEdge(e);
		e.setTarget(v);
		
		System.out.println(v);
		
		
		
	
	}

}
