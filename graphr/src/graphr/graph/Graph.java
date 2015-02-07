package graphr.graph;

import graphr.data.JsonArrayState;
import graphr.data.JsonKeyValueState;
import graphr.data.JsonReadableWritable;

import java.util.Hashtable;

public class Graph<DV extends JsonReadableWritable,
	DE extends JsonReadableWritable> 
	implements JsonReadableWritable {
	
	Hashtable<Integer, Vertex<DV,DE>> vertices;
	
	public Graph() {
		vertices = new Hashtable<Integer,Vertex<DV,DE>>();
	}
	
	public void addVertex(Vertex<DV,DE> v) {
		vertices.put(new Integer(v.id), v);
	}
	
	@Override
	public String getAsJson() {
		
		JsonKeyValueState j = new JsonKeyValueState();
		j.add("type", "Graph");
		
		JsonArrayState verticesForJson = new JsonArrayState();
		
		for (Vertex<DV,DE> v : vertices.values()) {
			verticesForJson.add(v.getAsJson());
		}
		
		j.add("vertices",verticesForJson.toJson());

		return j.toJson();
		
	}

	@Override
	public void setFromJson() {
		// TODO Auto-generated method stub
		
	}

}
