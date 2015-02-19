package graphr.io;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

import com.codesnippets4all.json.parsers.JSONParser;
import com.codesnippets4all.json.parsers.JsonParserFactory;

import graphr.data.GHT;
import graphr.data.JsonArrayState;
import graphr.data.JsonKeyValueState;
import graphr.data.PrimData;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.GraphData;
import graphr.graph.GraphDataVisitor;
import graphr.graph.GraphElementVisitor;
import graphr.graph.Vertex;

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
 */
public class JsonVisitor<DV extends GraphData, DE extends GraphData> implements
		GraphElementVisitor {
	private static Logger log = LogManager.getLogger();
	private static final String EDGE_TARGET_KEY = "JSonVisitor_Edge_target";

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
		vertexProp.add("id", new Integer((int) vertex.getId()).toString());
		vertexProp.add("data", vertex.getData() != null ? vertex.getData()
				.accept(dataVisitor).toString() : "null");

		// get JSON array for edges
		JsonArrayState edgesForJson = new JsonArrayState();
		for (Edge<?, ?> e : vertex.getEdges()) {
			JsonKeyValueState edgeProp = new JsonKeyValueState();
			edgeProp.add("type", "Edge");
			edgeProp.add("id", new Integer((int) e.getId()).toString());
			edgeProp.add("data", (e.getData() == null ? "null" : e.getData()
					.accept(dataVisitor).toString()));
			edgeProp.add("target", (e.getTarget() == null ? "null"
					: new Integer((int) e.getTarget().getId()).toString()));
			edgesForJson.add(edgeProp.getAsJson());
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

		Hashtable<Integer, Vertex<GHT, GHT>> vTable = parsedGraph
				.getVerticesAsHashtable();
		for (Vertex<GHT, GHT> v : vTable.values()) {
			for (Edge<GHT, GHT> e : v.getEdges()) {
				if (e.getData().getTable()
						.containsKey(JsonVisitor.EDGE_TARGET_KEY)) {
					PrimData target = (PrimData) e.getData().getTable()
							.get(JsonVisitor.EDGE_TARGET_KEY);
					Vertex<GHT, GHT> targetVertex = vTable.get(target.o());
					e.setTarget(targetVertex);
					e.getData().getTable().remove(JsonVisitor.EDGE_TARGET_KEY);
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

		Vertex<GHT, GHT> v = new Vertex<GHT, GHT>();
		v.setId(jo.getInt("id"));
		v.setData(jSonObjectToGHT(jo.getJSONObject("data")));

		// Edges

		Object edges = jo.get("edges");
		if (edges instanceof JSONArray) {

			JSONArray edgesArray = (JSONArray) edges;

			for (int i = 0; i < edgesArray.length(); i++) {
				JSONObject ejo = edgesArray.getJSONObject(i);
				v.addEdge(jSonObjectToEdge(ejo));
			}
		}

		return v;
	}

	public Edge<GHT, GHT> jSonObjectToEdge(JSONObject jo) {

		Edge<GHT, GHT> e = new Edge<GHT, GHT>();
		e.setId(jo.getInt("id"));

		Object o = jo.get("data");
		if (o instanceof JSONObject) {
			JSONObject dataO = (JSONObject) o;
			e.setData(jSonObjectToGHT(dataO));
		} else {
			e.setData(new GHT());
		}

		Object ot = jo.get("target");
		if (ot instanceof Integer) {
			Integer targetInt = (Integer) ot;
			e.getData().put(JsonVisitor.EDGE_TARGET_KEY, new PrimData(targetInt));
		}

		return e;
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

	// public Graph<GHT, GHT> parseJsonString(String readString) {
	// log.entry(readString);
	//
	// JsonParserFactory factory=JsonParserFactory.getInstance();
	// JSONParser parser=factory.newJsonParser();
	//
	// Map<?,?> jsonData = (Map<?,?>) parser.parseJson(readString);
	//
	// // get JSON list of vertices
	// List<?> value = (List<?>) jsonData.get("vertices");
	//
	// log.debug(value);
	//
	// Graph<GHT, GHT> parsedGraph = new Graph<GHT, GHT>();
	//
	// // map of edges with uninitialized target vertex field to the id targeted
	// vertex
	// HashMap<Edge<GHT, GHT>, Integer> untargetedEdges = new
	// HashMap<Edge<GHT,GHT>, Integer>();
	//
	// // parse vertices
	// for(Object obj : value) {
	// log.debug("-----");
	//
	// log.debug("read: " + obj);
	// Map<?,?> propMap = (Map<?,?>) obj;
	//
	// Vertex<GHT, GHT> v = new Vertex<GHT, GHT>();
	//
	// // vertex id
	// v.setId( (new PrimData("i", (String) propMap.get("id")).i()) );
	//
	// // now we can insert it into the graph because ID is the correct right
	// now
	// parsedGraph.addVertex(v);
	//
	// log.debug("data: " + propMap.get("data").toString());
	//
	// // vertex data
	// if(propMap.get("data") instanceof String) {
	// // do nothing
	// } else if(propMap.get("data") instanceof Map) {
	// Map<?,?> dataMap = (Map<?,?>) propMap.get("data");
	// v.setData( parseGHT(dataMap) );
	// } else {
	// log.error("Error: Data of the vertex is neither \"null\" string nor as map. Instead it is "
	// + propMap.get("data").getClass().getName());
	// }
	//
	// // parse edges
	// Object unkwEdges = propMap.get("edges");
	// if(unkwEdges instanceof String) {
	// if(((String)unkwEdges).compareTo("null") == 0) {
	// // do nothing, there are no edges
	// } else {
	// log.error("Error: Expecting string equal to \"null\" or list, instead got: "
	// + unkwEdges);
	// }
	// } else if (unkwEdges instanceof List) {
	// List<?> edges = (List<?>) propMap.get("edges");
	//
	// // iterate over edges
	// for(Object jsonEdge : edges) {
	// log.debug("edge: " + jsonEdge);
	// Edge<GHT, GHT> edge = new Edge<GHT, GHT>();
	//
	// // set id
	// Map<?,?> edgeProp = (Map<?,?>)jsonEdge;
	// edge.setId( (new PrimData("i", (String) edgeProp.get("id")).i()) );
	//
	// v.addEdge(edge);
	//
	// // edge's data
	// if(edgeProp.get("data") instanceof String) {
	// // do nothing
	// } else if(edgeProp.get("data") instanceof Map) {
	// Map<?,?> edgeDataMap = (Map<?,?>) edgeProp.get("data");
	// edge.setData( parseGHT(edgeDataMap) );
	// } else {
	// log.debug("Error: Data of the edge is neither \"null\" string nor as map. Instead it is "
	// + propMap.get("data").getClass().getName());
	// }
	//
	// // save edge's id for later initialization
	// edge.setTarget(null);
	// untargetedEdges.put(edge, (new PrimData("i", (String)
	// edgeProp.get("target")).i()));
	//
	// log.debug("parsed edge: " + edge);
	// }
	// }
	//
	//
	// }
	//
	// log.trace("-->> Updating edges' target");
	// for(Map.Entry<Edge<GHT, GHT>,Integer> entry : untargetedEdges.entrySet())
	// {
	// Vertex<GHT, GHT> vertex = parsedGraph.getVertex(entry.getValue());
	// if(vertex != null) {
	// entry.getKey().setTarget(vertex);
	// } else {
	// log.error("Error: Given target vertex with id " + entry.getValue() +
	// " does not exist! Edge: " + entry.getKey());
	// }
	// }
	//
	// return parsedGraph;
	//
	// }

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
