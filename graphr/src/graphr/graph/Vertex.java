package graphr.graph;

import java.util.ArrayList;

import graphr.data.JsonArrayState;
import graphr.data.JsonReadableWritable;
import graphr.data.JsonKeyValueState;


public class Vertex<DV extends JsonReadableWritable,DE extends JsonReadableWritable>
	extends GraphElement<DV> implements JsonReadableWritable {
	
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

	@Override
	public String getAsJson() {
		
		JsonKeyValueState j = new JsonKeyValueState();
		
		j.add("type", "Vertex");
		j.add("id", new Integer(id).toString());
		j.add("data", data.getAsJson());
	
		JsonArrayState jsonEdges = new JsonArrayState();
		
		for (Edge<DV,DE> e : edges) {
			jsonEdges.add(e.getAsJson());
		}
		
		j.add("edges", jsonEdges.toJson());

		return j.toJson();
	}

	@Override
	public void setFromJson() {
		// TODO Auto-generated method stub
		
	}
	
	
	
}
