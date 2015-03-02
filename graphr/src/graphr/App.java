package graphr;

//import java.util.HashMap;
//
//import com.json.parsers.JsonParserFactory;


import graphr.algorithms.ConnectionDistanceAgentPopulator;
import graphr.data.GHT;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;
import graphr.io.FileSystemHandler;
import graphr.io.JsonVisitor;
import graphr.processing.AgentManager;
import graphr.processing.AgentPopulator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
	
	private static Logger log = LogManager.getLogger(); 
	
//	private static final String FULL_FILE_PATH 
//		= "ouputFile.txt";
	
	public static Graph<GHT, GHT> getExampleGraph() {
		
		GHT gh = new GHT();
		gh.put("label", "Anna");
		gh.put("name", "Anna");
		gh.put("age", 24);
		
		GHT gh2 = new GHT();
		gh2.put("label", "Frank");
		gh2.put("name", "Martin");
		gh2.put("age", 28);
		gh2.put("income", 24.7);
		
		Vertex<GHT, GHT> v = new Vertex<GHT, GHT>(new GHT());
		v.setData(gh);
		
		Edge<GHT, GHT> e = new Edge<GHT, GHT>(new GHT());
		Edge<GHT, GHT> e2 = new Edge<GHT, GHT>(new GHT());
		v.addEdge(e);
		v.addEdge(e2);
		
		Graph<GHT, GHT> g = new Graph<GHT, GHT>();
		Vertex<GHT, GHT> v2 = new Vertex<GHT, GHT>(new GHT());
		g.addVertex(v);
		g.addVertex(v2);
		e.setTarget(v2);
		e2.setTarget(v);
		v2.setData(gh2);
		
		// Now the graph is getting more complex
		
		GHT gh3 = new GHT();
		gh3.put("label", "Josh");
		Vertex<GHT, GHT> v3 = new Vertex<GHT, GHT>(gh3);
		
		GHT gh4 = new GHT();
		gh4.put("label", "Nilay");
		Vertex<GHT, GHT> v4 = new Vertex<GHT, GHT>(gh4);
		
		GHT gh5 = new GHT();
		gh5.put("label", "Lisa");
		Vertex<GHT, GHT> v5 = new Vertex<GHT, GHT>(gh5);
		
		g.addVertex(v3);
		g.addVertex(v4);
		g.addVertex(v5);
		
		Edge<GHT, GHT> e24 = new Edge<GHT, GHT>(new GHT());
		e24.setTarget(v4);
		Edge<GHT, GHT> e23 = new Edge<GHT, GHT>(new GHT());
		e23.setTarget(v3);
		Edge<GHT, GHT> e34 = new Edge<GHT, GHT>(new GHT());
		e34.setTarget(v4);
		Edge<GHT, GHT> e35 = new Edge<GHT, GHT>(new GHT());
		e35.setTarget(v5);
		Edge<GHT, GHT> e45 = new Edge<GHT, GHT>(new GHT());
		e45.setTarget(v5);
		
		v2.addEdge(e24);
		v2.addEdge(e23);
		v3.addEdge(e34);
		v3.addEdge(e35);
		v4.addEdge(e45);
		
		return g;
		
	}
	
	public static void doSomeReadWriteTests() {
		
//		System.out.println("Hello world.");
//		
//		Graph<GHT, GHT> g = App.getExampleGraph();
		
//		FileSystemHandler.getInstance().write(JsonFormatter.getInstance().getJsonString(g), 
//				App.FULL_FILE_PATH);
//		
//		String readString = FileSystemHandler.getInstance().read(
//				App.FULL_FILE_PATH);
//		
//		System.out.println(readString);
//		
//		Graph<GHT, GHT> parsed = JsonFormatter.getInstance().parseJsonString(readString);
//		
//		System.out.println("+++++++++++++");
//
//		FileSystemHandler.getInstance().write(JsonFormatter.getInstance().getJsonString(parsed), 
//				App.FULL_FILE_PATH);
//				
//		System.out.println(JsonFormatter.getInstance().getJsonString(parsed));
		
	}

	public static String graphToJsonString(Graph<GHT, GHT> g) {
		JsonVisitor<GHT, GHT> jsonVisitor = new JsonVisitor<GHT, GHT>();		
		g.accept(jsonVisitor);		
		String graphSerialized = jsonVisitor.getJsonString();
		return graphSerialized;
	}

	public static void main(String[] args) {
		
		// Input
		
		Graph<GHT,GHT> g = App.getExampleGraph();
		log.debug("Before" + App.graphToJsonString(g));
		
		JsonVisitor<GHT, GHT> jsonVisitor = new JsonVisitor<GHT, GHT>();		
		g.accept(jsonVisitor);		
		String graphSerialized = jsonVisitor.getJsonString();
		Graph<GHT, GHT> graphDeserialized = jsonVisitor.parseJsonString(graphSerialized);
		
		// Do processing
		
		AgentPopulator p = new ConnectionDistanceAgentPopulator(0, 5);
		AgentManager m = new AgentManager(g, p);
		m.runProcessing(10);
		
		// Output
		
		log.debug("After" + App.graphToJsonString(g));
		
		g.runQuickAndDirtyVisitor();
	}

}
