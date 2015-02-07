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
		gh.put("age", 24);
		
		GHT gh2 = new GHT();
		gh2.put("name", "Martin");
		gh2.put("age", 28);
		gh2.put("income", 24.7);
		
		Vertex<GHT, GHT> v = new Vertex<GHT, GHT>();
		v.setData(gh);
		
		Edge<GHT, GHT> e = new Edge<GHT, GHT>();
		Edge<GHT, GHT> e2 = new Edge<GHT, GHT>();
		v.addEdge(e);
		v.addEdge(e2);
		
		Graph<GHT, GHT> g = new Graph<GHT, GHT>();
		Vertex<GHT, GHT> v2 = new Vertex<GHT, GHT>();
		g.addVertex(v);
		g.addVertex(v2);
		e.setTarget(v2);
		e2.setTarget(v);
		v2.setData(gh2);
		
		System.out.println(g.getAsJson());
	}

}
