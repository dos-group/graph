package graphr.io;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import graphr.data.GHT;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;
import org.junit.Test;

public class JsonFormatterTest {
	
	private static Logger log = LogManager.getLogger(); 

	/**
	 * Tests whether both serialization and deserialization works correctly with the {@link JsonFormatter} class. 
	 * It checks whether we get the same graph after serialization of the source and its immediate parsing (deserialization).
	 */
	@Test
	public void testJsonSerilization() {
		
		Graph<GHT, GHT> graphSource = getExampleGraph();
		String graphSerialized = JsonFormatter.getInstance().getJsonString(graphSource);
		Graph<GHT, GHT> graphProcessed = JsonFormatter.getInstance().parseJsonString(graphSerialized);		
		checkGraphs(graphSource, graphProcessed);		
	}

	/**
	 * Tests whether both serialization and deserialization works correctly with the {@link JsonVisitor} class. 
	 * It checks whether we get the same graph after serialization of the source and its immediate parsing (deserialization).
	 */
	@Test
	public void testJsonVisitorSerilization() {
		
		Graph<GHT, GHT> graphSource = getExampleGraph();
		
		JsonVisitor<GHT, GHT> jsonVisitor = new JsonVisitor<GHT, GHT>();		
		graphSource.accept(jsonVisitor);		
		String graphSerialized = jsonVisitor.getJsonString();
		
		log.debug("---- Serialized graph:   " + graphSerialized);
		

		
		Graph<GHT, GHT> graphDeserialized = jsonVisitor.parseJsonString(graphSerialized);	
		
		// Now let's reserialize the graph
		
		JsonVisitor<GHT, GHT> jsonVisitorRe = new JsonVisitor<GHT, GHT>();
		graphDeserialized.accept(jsonVisitorRe);
		String graphSerializedRe = jsonVisitorRe.getJsonString();
		log.debug("---- Graph reserialized: " + graphSerializedRe);
		log.debug("---- SameString: " + graphSerializedRe.equals(graphSerialized));
		
		
		checkGraphs(graphSource, graphDeserialized);	
		
		// Assuming:
		// https://github.com/douglascrockford/JSON-java
		
//		JSONObject jo = new JSONObject(graphSerialized);
//		log.debug("---- json object: " + jo.toString());
		
//		// Third check
//		
//		JsonVisitor<GHT, GHT> jsonVisitor2 = new JsonVisitor<GHT, GHT>();
//		graphProcessed.accept(jsonVisitor2);
//		String graphSerialized2 = jsonVisitor2.getJsonString();
//		log.debug("---- Serialized graph2: " + graphSerialized2);
		
		
	
	}

	
	protected void checkGraphs(Graph<GHT, GHT> graphSource, Graph<GHT, GHT> graphProcessed) {
		// compare graphs: for each vertex in source graph find and match vertex in processed graph 
		for(Vertex<GHT, GHT> sourceVertex : graphSource.getVertices()) {
			
			// find vertex of given ID
			Vertex<GHT, GHT> processedVertex = graphProcessed.getVertex((int) sourceVertex.getId());
			assertNotNull("Serialized and deserialized graphs do not match. Could not find processed vertex of id " + sourceVertex.getId(), 
					processedVertex);
			
			// compare associated GHTs
			if(sourceVertex.getData() == null) {
				assertTrue("Serialized and deserialized graphs do not match. GHT of processed vertex is not null!", 
						processedVertex.getData() == null); 
			} else {			
				assertTrue("Serialized and deserialized graphs do not match. GHTs of vertices are not equal!", 
						sourceVertex.getData().compareTables(processedVertex.getData(), false)); 			
			}
			
			// compare edges			
			if(sourceVertex.getEdges() == null) {			
				assertTrue("Serialized and deserialized graphs do not match. Set of edges is null!", 
						processedVertex.getEdges() == null );
			}

			assertTrue("Serialized and deserialized graphs do not match. Different number of outgoing edges!", 
					sourceVertex.getEdges().size() == processedVertex.getEdges().size() );
			
			for(Edge<GHT, GHT> sourceEdge : sourceVertex.getEdges()) {
				Edge<GHT, GHT> processedEdge = processedVertex.getEdge((int) sourceEdge.getId());
				assertNotNull("Serialized and deserialized graphs do not match. Processed edge of id " + sourceEdge.getId() + " does not exist!", 
						processedEdge);
				
				// compare data
				if(sourceEdge.getData() == null) {
					assertTrue("Serialized and deserialized graphs do not match. GHT of processed edge is not null!", 
							processedEdge.getData() == null); 
				} else {
				
					assertTrue("Serialized and deserialized graphs do not match. GHTs of edges are not equal!", 
							sourceEdge.getData().compareTables(processedEdge.getData(), false)); 
				}
				
				// compare target id				
				assertTrue("Serialized and deserialized graphs do not match. Target vertex ID of edges are not equal!", 
						sourceEdge.getTarget().getId() == processedEdge.getTarget().getId() );
				
			}
			
		}

	}
	
	/**
	 * Generates a graph for test purposes
	 * @return
	 */
	public Graph<GHT, GHT> getExampleGraph() {
		
		GHT gh = new GHT();
		gh.put("name", "Anna");
		gh.put("age", 24);
		
		GHT gh2 = new GHT();
		gh2.put("name", "Martin");
		gh2.put("age", 28);
		gh2.put("income", 24.7);
		
		Vertex<GHT, GHT> v = new Vertex<GHT, GHT>();
		v.setData(gh);
		
		Edge<GHT, GHT> e = new Edge<GHT, GHT>();
		Edge<GHT, GHT> e2 = new Edge<GHT, GHT>();
		v.addEdge(e);
		v.addEdge(e2);
		
		Graph<GHT, GHT> g = new Graph<GHT, GHT>();
		Vertex<GHT, GHT> v2 = new Vertex<GHT, GHT>();
		g.addVertex(v);
		g.addVertex(v2);
		e.setTarget(v2);
		e2.setTarget(v);
		v2.setData(gh2);
		
		return g;
		
	}

}
