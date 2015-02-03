package graphr;

import graphr.data.GraphHashtable;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;

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
		
		Vertex<GraphHashtable, GraphHashtable> v = new Vertex<GraphHashtable, GraphHashtable>();
		v.setData(gh);
		Edge<GraphHashtable, GraphHashtable> e = new Edge<GraphHashtable, GraphHashtable>();
		e.setData(gh2);
		
		Graph<GraphHashtable, GraphHashtable> g = 
				new Graph<GraphHashtable, GraphHashtable>();
		g.addVertex(v);
		v.addEdge(e);
		e.setTarget(v);
		
		System.out.println(v);
	
	}

}
