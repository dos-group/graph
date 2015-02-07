package graphr.graph;

import java.util.ArrayList;
import java.util.Hashtable;

import graphr.data.JsonArrayState;
import graphr.data.JsonReadableWritable;
import graphr.data.JsonKeyValueState;


public class Vertex<DV extends JsonReadableWritable,DE extends JsonReadableWritable>
	extends GraphElement<DV> implements JsonReadableWritable {
	
	Hashtable<Integer, Edge<DV,DE>> edges;
	
	public Vertex() {
		edges = new Hashtable<Integer, Edge<DV,DE>>();
	}
 
	public void addEdge(Edge<DV,DE> e) {
		edges.put(new Integer(e.id), e);
	}
	
	public String toString() {

		return "Vertex"; // to do ;)
		
	}

	@Override
	public String getAsJson() {
		
		JsonKeyValueState j = new JsonKeyValueState();
		
		j.add("type", "Vertex");
		j.add("id", new Integer(id).toString());
		j.add("data", data.getAsJson());
	
		JsonArrayState edgesForJson = new JsonArrayState();
		
		for (Edge<DV,DE> e : edges.values()) {
			edgesForJson.add(e.getAsJson());
		}
		
		j.add("edges", edgesForJson.toJson());

		return j.toJson();
	}

	@Override
	public void setFromJson() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
