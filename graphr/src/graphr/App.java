package graphr;

//import java.util.HashMap;
//
//import com.json.parsers.JsonParserFactory;

import graphr.algorithms.ConnectionDistanceAgentPopulator;
import graphr.algorithms.DirectedSpreadingAgentPopulator;
import graphr.algorithms.RatingWithDirectedUserDepandantSpreadingAgentPopulator;
import graphr.algorithms.RestrictedUserSpreadingAgentPopulator;
import graphr.algorithms.UserDepandantDirectedSpreadingAgentPopulator;
import graphr.data.GHT;
import graphr.data.PrimData;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;
import graphr.graph.Edge.Direction;
import graphr.io.FileSystemHandler;
import graphr.io.JsonVisitor;
import graphr.processing.AgentManager;
import graphr.processing.AgentPopulator;

import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {

	private static Logger log = LogManager.getLogger();

	// private static final String FULL_FILE_PATH
	// = "ouputFile.txt";

	public static Graph<GHT, GHT> getExampleGraph() {
		Graph<GHT, GHT> g = new Graph<GHT, GHT>();

		GHT gh = new GHT();
		gh.put("label", "Anna");
		gh.put("name", "Anna");
		gh.put("age", 24);

		GHT gh2 = new GHT();
		gh2.put("label", "Frank");
		gh2.put("name", "Martin");
		gh2.put("age", 28);
		gh2.put("income", 24.7);

		Vertex<GHT, GHT> v1 = new Vertex<GHT, GHT>(new GHT());
		v1.setData(gh);

		Vertex<GHT, GHT> v2 = new Vertex<GHT, GHT>(new GHT());
		v2.setData(gh2);

		Edge<GHT, GHT> e11 = new Edge<GHT, GHT>(new GHT());
		e11.setSource(v1);
		e11.setTarget(v1);

		Edge<GHT, GHT> e12 = new Edge<GHT, GHT>(new GHT());
		e12.setSource(v1);
		e12.setTarget(v2);

		v1.addEdge(e11);

		v1.addEdge(e12);
		v2.addEdge(e12);

		g.addVertex(v1);
		g.addVertex(v2);

		// Now the graph is getting more complex

		GHT gh3 = new GHT();
		gh3.put("label", "Josh");

		GHT gh4 = new GHT();
		gh4.put("label", "Nilay");

		GHT gh5 = new GHT();
		gh5.put("label", "Lisa");

		Vertex<GHT, GHT> v3 = new Vertex<GHT, GHT>(gh3);

		Vertex<GHT, GHT> v4 = new Vertex<GHT, GHT>(gh4);

		Vertex<GHT, GHT> v5 = new Vertex<GHT, GHT>(gh5);

		Edge<GHT, GHT> e24 = new Edge<GHT, GHT>(new GHT());
		e24.setSource(v2);
		e24.setTarget(v4);

		Edge<GHT, GHT> e23 = new Edge<GHT, GHT>(new GHT());
		e23.setSource(v2);
		e23.setTarget(v3);

		Edge<GHT, GHT> e34 = new Edge<GHT, GHT>(new GHT());
		e34.setSource(v3);
		e34.setTarget(v4);

		Edge<GHT, GHT> e35 = new Edge<GHT, GHT>(new GHT());
		e35.setSource(v3);
		e35.setTarget(v5);

		Edge<GHT, GHT> e45 = new Edge<GHT, GHT>(new GHT());
		e45.setSource(v4);
		e45.setTarget(v5);

		v2.addEdge(e24);
		v4.addEdge(e24);

		v2.addEdge(e23);
		v3.addEdge(e23);

		v3.addEdge(e34);
		v4.addEdge(e34);

		v3.addEdge(e35);
		v5.addEdge(e35);

		v4.addEdge(e45);
		v5.addEdge(e45);

		g.addVertex(v3);
		g.addVertex(v4);
		g.addVertex(v5);

		return g;
	}

	public static void doSomeReadWriteTests() {

		// System.out.println("Hello world.");
		//
		// Graph<GHT, GHT> g = App.getExampleGraph();

		// FileSystemHandler.getInstance().write(JsonFormatter.getInstance().getJsonString(g),
		// App.FULL_FILE_PATH);
		//
		// String readString = FileSystemHandler.getInstance().read(
		// App.FULL_FILE_PATH);
		//
		// System.out.println(readString);
		//
		// Graph<GHT, GHT> parsed =
		// JsonFormatter.getInstance().parseJsonString(readString);
		//
		// System.out.println("+++++++++++++");
		//
		// FileSystemHandler.getInstance().write(JsonFormatter.getInstance().getJsonString(parsed),
		// App.FULL_FILE_PATH);
		//
		// System.out.println(JsonFormatter.getInstance().getJsonString(parsed));

	}

	public static String graphToJsonString(Graph<GHT, GHT> g) {
		JsonVisitor<GHT, GHT> jsonVisitor = new JsonVisitor<GHT, GHT>();
		g.accept(jsonVisitor);
		String graphSerialized = jsonVisitor.getJsonString();
		return graphSerialized;
	}

	public static String getGPlusGraphFromFS() {
		System.out.println("Hello world.");

		String readString = FileSystemHandler.getInstance().read(
//				"target/jsonMitEinerNextEdge.json");
				"target/neuestes.json");
//				"target/angereichertMitRatingInfos.json");
		return readString;
	}

	public static void main(String[] args) {

		// Input

		String gplusGraphString = App.getGPlusGraphFromFS();

		JsonVisitor<GHT, GHT> jsonVisitor = new JsonVisitor<GHT, GHT>();
		Graph<GHT, GHT> g = jsonVisitor.parseJsonString(gplusGraphString);

		// Do processing

		log.debug("run algorithms");
		AgentPopulator p;
		AgentManager m;
		
		/*
		Hashtable<Long, String> erlaubteUser = new Hashtable<>();
		
		erlaubteUser.put((long)1, "1");		
		erlaubteUser.put((long)90, "90");
		erlaubteUser.put((long)10, "10");		
		erlaubteUser.put((long)13, "13");
		erlaubteUser.put((long)16, "16");
		erlaubteUser.put((long)59, "59");
		erlaubteUser.put((long)62, "62");
		erlaubteUser.put((long)87, "87");
		
		p = new RestrictedUserSpreadingAgentPopulator(33, 3, erlaubteUser, true);
		m = new AgentManager(g, p, Direction.BOTH);
		m = new AgentManager(g, p, Direction.INCOMING);
		m.runProcessing(3);
		*/
		p = new DirectedSpreadingAgentPopulator(33,2,true);
		m = new AgentManager(g, p, Direction.BOTH);
		m = new AgentManager(g, p, Direction.INCOMING);
		m.runProcessing(3);
		
		
		//Nur bei gerateten sinnvoll
		//g.handleRating(2.5);
		// Output

		// log.debug("Startin stuff " + App.graphToJsonString(g));

		log.debug("Startin stuff");
		g.deleteVerticesWithoutRightProterty("visible" , new PrimData(true));
		g.deleteEdgesWithoutRightProterty("visible" , new PrimData(true));
		g.createJsonString("target/ausgabe.json");

		log.debug("Sould be done right now.");
	}

}
