package graphr.graph;

import java.util.ArrayList;

public class Graph<DV,DE> {
	
	ArrayList<Vertex<DV,DE>> vertices;
	
	public Graph() {
		vertices = new ArrayList<Vertex<DV,DE>>();
	}
	
	public void addVertex(Vertex<DV,DE> v) {
		vertices.add(v.id, v);
	}

}
