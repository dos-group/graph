package graphr.graph;

//import graphr.data.JsonArrayState;
//import graphr.data.JsonKeyValueState;

import graphr.data.GHT;
import graphr.data.JsonArrayState;
import graphr.data.JsonKeyValueState;
import graphr.io.FileSystemHandler;

import java.util.Collection;
import java.util.Hashtable;

public class Graph<DV extends GraphData, DE extends GraphData> {
	
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
	
	public Hashtable<Integer, Vertex<DV,DE>> getVerticesAsHashtable() {
		return vertices;
	}
	
	public Collection<Vertex<DV,DE>> getVertices() {
		return vertices.values();
	}
	
	
	/**
	 * Part of the visitor design pattern -accept method.
	 * <br>
	 * Main entry method for the entire graph. 
	 */
	public void accept(GraphElementVisitor visitor) {
		visitor.before();
		visitor.visit(this);

		for (Vertex<DV,DE> v : vertices.values()) {
			v.accept(visitor);
		}
	
		visitor.after();
	}
	
	
	public void runQuickAndDirtyVisitor() {
		
		JsonArrayState jsonEdges = new JsonArrayState();
		
		for (Vertex<DV, DE> v : vertices.values()) {
			for (Edge<DV, DE> e: v.edges.values()) {
				JsonKeyValueState jsonEdge = new JsonKeyValueState();
				Vertex<DV, DE> vTarget = e.getTarget();
				
				GHT sGHT = (GHT) v.getData();
				jsonEdge.add("source", sGHT.getTable().get("vislabel").s());
				
				GHT tGHT = (GHT) vTarget.getData();
				jsonEdge.add("target", tGHT.getTable().get("vislabel").s());
				
				jsonEdge.add("type", "suit");
				jsonEdges.add(jsonEdge.getAsJson());
			}
		}
		
		FileSystemHandler.getInstance().write(jsonEdges.getAsJson(), 
			"basicvis_live.json");
	}

}
