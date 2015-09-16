package graphr.io;

import graphr.data.GHT;
import graphr.data.JsonArrayState;
import graphr.data.JsonKeyValueState;
import graphr.data.PrimData;
import graphr.graph.Edge;
import graphr.graph.Edge.Direction;
import graphr.graph.Graph;
import graphr.graph.GraphData;
import graphr.graph.GraphDataVisitor;
import graphr.graph.GraphElementVisitor;
import graphr.graph.Vertex;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Attempt to create a visitor pattern for graph serialization. <br>
 * Visitor that serializes/desirializes a graph into customized JSON output of
 * the following format:
 * 
 * <pre>
 * {
 *  "vertices": [
 * 	 {
 * 	  "type" : "Vertex"
 * 	  "id" : id
 *   "data" : { "key1" : "value1", "key2" : "value2", ...}
 *   "edges" : [{ "type": "Edge", "id" : id, "target" : targetId, "data": {same as vertex} }, {}, ...]
 * 	 },
 *  {...} 
 *  ], 
 *  "type" : graph
 * }
 * </pre>
 *
 * Assuming: https://github.com/douglascrockford/JSON-java
 *
 *
 *
 */
public class JsonVisitor<DV extends GraphData, DE extends GraphData> implements GraphElementVisitor {
	private static Logger log = LogManager.getLogger();
	private static final String EDGE_TARGET_KEY = "JSonVisitor_Edge_target";
	private static final String EDGE_SOURCE_KEY = "JSonVisitor_Edge_source";

	JsonKeyValueState root;
	JsonArrayState verticesForJson;
	GraphDataVisitor dataVisitor;

	@Override
	public void before() {
		dataVisitor = new GHTJsonVisitor();
	}

	@Override
	public void visit(Graph<?, ?> graph) {
		root = new JsonKeyValueState();
		root.add("type", "Graph");
		verticesForJson = new JsonArrayState();
	}

	@Override
	public void visit(Vertex<?, ?> vertex) {

		JsonKeyValueState vertexProp = new JsonKeyValueState();

		vertexProp.add("type", "Vertex");
		vertexProp.add("id", new Long(vertex.getId()).toString());
		vertexProp.add("data", vertex.getData() != null ? vertex.getData().accept(dataVisitor).toString() : "null");

		// get JSON array for edges
		JsonArrayState edgesForJson = new JsonArrayState();
		for (Edge<?, ?> e : vertex.getEdges()) {
                    //create only outgoing edges
                    if(e.getSource().getId() == vertex.getId()){
			JsonKeyValueState edgeProp = new JsonKeyValueState();
			edgeProp.add("type", "Edge");
			edgeProp.add("id", new Long(e.getId()).toString());
			edgeProp.add("data", (e.getData() == null ? "null" : e.getData().accept(dataVisitor).toString()));
			edgeProp.add("target", (e.getTarget() == null ? "null" : new Long(e.getTarget().getId()).toString()));
			edgesForJson.add(edgeProp.getAsJson());
                    }
		}
		vertexProp.add("edges", edgesForJson.getAsJson());

		// add serialized vertex into the list of vertices
		verticesForJson.add(vertexProp.getAsJson());
	}

	@Override
	public void visit(Edge<?, ?> edge) {
		// do nothing
	}

	@Override
	public void after() {
		root.add("vertices", verticesForJson.getAsJson());
	}

	public String getJsonString() {
		return root != null ? root.getAsJson() : "null";
	}

	/**
	 * Experimental method to parse graph in JSON. Initialization of edges is
	 * done in two steps. Firstly they are created during parsing a vertex but
	 * their target vertex is set to null. Once all vertices are parsed, proper
	 * references to target vertex (of the edge) is set. We use help structure
	 * that is map of edge to id of the vertex. Assuming:
	 * https://github.com/douglascrockford/JSON-java
	 * 
	 * @param readString
	 *            String to be parsed
	 * @return Initialized graph
	 */
	public Graph<GHT, GHT> parseJsonString(String readString) {

		JSONObject jo = new JSONObject(readString);

		// Init

		Graph<GHT, GHT> parsedGraph = new Graph<GHT, GHT>();
		if (!jo.getString("type").equals("Graph")) {
			throw new IllegalArgumentException("Graph expected.");
		}

		// Add vertices

		JSONArray vertices = jo.getJSONArray("vertices");
		int vertexCount = vertices.length();

		for (int i = 0; i < vertexCount; i++) {
			JSONObject vjo = vertices.getJSONObject(i);
			parsedGraph.addVertex(jSonObjectToVertex(vjo));
		}

		// Fix edge targets

		Hashtable<Long, Vertex<GHT, GHT>> vTable = parsedGraph.getVerticesAsHashtable();
		for (Vertex<GHT, GHT> v : vTable.values()) {
			Iterator<Edge<GHT, GHT>> edges = v.getEdges(Direction.BOTH).iterator();
			while (edges.hasNext()) {
				Edge<GHT, GHT> edge = edges.next();
				Hashtable<String, PrimData> edgeData = edge.getData().getTable();
				if (edgeData.containsKey(JsonVisitor.EDGE_TARGET_KEY)) {
					PrimData target = (PrimData) edgeData.get(JsonVisitor.EDGE_TARGET_KEY);
					Vertex<GHT, GHT> targetVertex = vTable.get(target.o());
					if (targetVertex != null) {
						edge.setTarget(targetVertex);
						edgeData.remove(JsonVisitor.EDGE_TARGET_KEY);

						// Add missing incoming edge to vertex
						if (targetVertex.getEdges(Direction.BOTH) == null
								|| !targetVertex.getEdges(Direction.BOTH).contains(edge)) {
							targetVertex.addEdge(edge);
						}
					}
				}
				if (edgeData.containsKey(JsonVisitor.EDGE_SOURCE_KEY)) {
					PrimData source = (PrimData) edgeData.get(JsonVisitor.EDGE_SOURCE_KEY);
					Vertex<GHT, GHT> sourceVertex = vTable.get(source.o());
					edge.setSource(sourceVertex);
					edgeData.remove(JsonVisitor.EDGE_SOURCE_KEY);
				}
				if (edge.getSource() == null || edge.getTarget() == null) {
					edges.remove();
				}
			}
		}

		return parsedGraph;
	}

	public Vertex<GHT, GHT> jSonObjectToVertex(JSONObject jo) {

		if (!jo.get("type").equals("Vertex")) {
			throw new IllegalArgumentException("Vertex expected.");
		}

		// Data directly attached to vertex

		Vertex<GHT, GHT> v = new Vertex<GHT, GHT>(new GHT());
		long vertexId = jo.getLong("id");
		v.setId(vertexId);

		Object o = jo.get("data");
		if (o instanceof JSONObject) {
			JSONObject dataO = (JSONObject) o;
			v.setData(jSonObjectToGHT(dataO));
		} else {
			v.setData(new GHT());
		}

		// Edges

		Object edges = jo.get("edges");
		if (edges instanceof JSONArray) {

			JSONArray edgesArray = (JSONArray) edges;

			for (int i = 0; i < edgesArray.length(); i++) {
				JSONObject ejo = edgesArray.getJSONObject(i);
				v.addEdge(jSonObjectToEdge(ejo, vertexId));
			}
		}

		return v;
	}

	public Edge<GHT, GHT> jSonObjectToEdge(JSONObject jo, long sourceLong) {

		Edge<GHT, GHT> edge = new Edge<GHT, GHT>(new GHT());
		edge.setId(jo.getLong("id"));

		Object o = jo.get("data");
		if (o instanceof JSONObject) {
			JSONObject dataO = (JSONObject) o;
			edge.setData(jSonObjectToGHT(dataO));
		} else {
			edge.setData(new GHT());
		}

		Long targetLong = jo.getLong("target");
		edge.getData().put(JsonVisitor.EDGE_TARGET_KEY, new PrimData(targetLong));
		edge.getData().put(JsonVisitor.EDGE_SOURCE_KEY, new PrimData(sourceLong));

		return edge;
	}

	public GHT jSonObjectToGHT(JSONObject jo) {

		Iterator<String> keys = jo.keys();

		GHT table = new GHT();

		while (keys.hasNext()) {

			String key = keys.next();

			Object value = jo.get(key);
			table.put(key, new PrimData(value));

		}

		return table;
	}

	/**
	 * Helper method to parse given JSON map into the Graph hashtable
	 * 
	 * @param dataMap
	 *            JSON map of keys and properties
	 * @return Graph hashtable
	 */
	public GHT parseGHT(Map<?, ?> dataMap) {
		GHT data = new GHT();
		for (Object mapKey : dataMap.keySet()) {
			Object mapValue = dataMap.get(mapKey);
			log.debug("key: " + mapKey + "; value: " + mapValue);
			data.put(mapKey.toString(), mapValue.toString());
		}

		return data;
	}

}
