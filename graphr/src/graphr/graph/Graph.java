package graphr.graph;

import graphr.data.JsonReadableWritable;

import java.util.ArrayList;

public class Graph<DV extends JsonReadableWritable,DE extends JsonReadableWritable> {
	
	ArrayList<Vertex<DV,DE>> vertices;
	
	public Graph() {
		vertices = new ArrayList<Vertex<DV,DE>>();
	}
	
	public void addVertex(Vertex<DV,DE> v) {
		vertices.add(v.id, v);
	}
	

}
