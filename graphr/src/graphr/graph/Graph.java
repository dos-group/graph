package graphr.graph;

import graphr.data.GraphData;
import graphr.data.JsonArrayState;
import graphr.data.JsonKeyValueState;
import graphr.data.JsonReadableWritable;

import java.util.Collection;
import java.util.Hashtable;

public class Graph<DV extends GraphData,
	DE extends GraphData> 
	implements JsonReadableWritable {
	
	Hashtable<Integer, Vertex<DV,DE>> vertices;
	
	public Graph() {
		vertices = new Hashtable<Integer,Vertex<DV,DE>>();
	}
	
	public void addVertex(Vertex<DV,DE> v) {
		vertices.put(new Integer(v.id), v);
	}
	
	/**
	 * Finds vertex of given ID
	 * @param id Identifier of vertex
	 * @return Vertex with given ID, if does not exist then null
	 */
	public Vertex<DV,DE> getVertex(int id) {		
		return vertices.get(new Integer(id));
	}
	
	public Collection<Vertex<DV,DE>> getVertices() {
		return vertices.values();
	}
	
	@Override
	public String getAsJson() {
		
		JsonKeyValueState j = new JsonKeyValueState();
		j.add("type", "Graph");
		
		JsonArrayState verticesForJson = new JsonArrayState();
		
		for (Vertex<DV,DE> v : vertices.values()) {
			verticesForJson.add(v.getAsJson());
		}
		
		j.add("vertices",verticesForJson.getAsJson());

		return j.getAsJson();
		
	}

	@Override
	public void setFromJson() {
		// TODO Auto-generated method stub
		
	}

}
