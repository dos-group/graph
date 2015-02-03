package graphr.graph;

import java.util.ArrayList;

public class Vertex<DV,DE> extends GraphElement<DV> {
	
	ArrayList<Edge<DV,DE>> edges;
	
	public Vertex() {
		edges = new ArrayList<Edge<DV,DE>>();
	}
 
	public void addEdge(Edge<DV,DE> e) {
		edges.add(e);
	}
	
	public String toString() {
		String s = "(" + getId() + "," + getData().toString() + ",{";
		int remainingEdges = edges.size();
		for (Edge<DV,DE> e : edges) {
			s += e.toString();
			remainingEdges --;
			if (remainingEdges > 0) {
				s += ",";
			}
		}
		
		return s + "})";
	}
	
}
