package graphr.graph;

import java.util.Collection;
import java.util.Hashtable;

import graphr.data.GraphData;
import graphr.data.JsonArrayState;
import graphr.data.JsonKeyValueState;
import graphr.data.JsonReadableWritable;


public class Vertex<DV extends GraphData,DE extends GraphData>
	extends GraphElement<DV> implements JsonReadableWritable {
	
	Hashtable<Integer, Edge<DV,DE>> edges;
	
	public Vertex() {
		edges = new Hashtable<Integer, Edge<DV,DE>>();
	}
 
	public void addEdge(Edge<DV,DE> e) {
		edges.put(new Integer(e.id), e);
	}
	
	public Collection<Edge<DV, DE>> getEdges() {
		return edges.values();
	}
	
	public String toString() {
		return "Vertex"; // to do ;)
	}

	@Override
	public String getAsJson() {
		
		JsonKeyValueState j = new JsonKeyValueState();
		
		j.add("type", "Vertex");
		j.add("id", new Integer(id).toString());
		j.add("data", data != null ? data.getAsJson() : "null");
	
		JsonArrayState edgesForJson = new JsonArrayState();
		
		for (Edge<DV,DE> e : edges.values()) {
			edgesForJson.add(e.getAsJson());
		}
		
		j.add("edges", edgesForJson.getAsJson());

		return j.getAsJson();
	}

	@Override
	public void setFromJson() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
