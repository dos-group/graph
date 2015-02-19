package graphr;

//import java.util.HashMap;
//
//import com.json.parsers.JsonParserFactory;

import graphr.data.GHT;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;
import graphr.io.FileSystemHandler;
import graphr.io.JsonFormatter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class App {
	
	private static Logger log = LogManager.getLogger(); 
	
	private static final String FULL_FILE_PATH 
		= "ouputFile.txt";
	
	public static Graph<GHT, GHT> getExampleGraph() {
		
		GHT gh = new GHT();
		gh.put("name", "Anna");
		gh.put("age", 24);
		
		GHT gh2 = new GHT();
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
		
		return g;
		
	}
	
	@Deprecated
	public static void testJsonFormatter() {
		
//		System.out.println("Hello world.");
//		
//		Graph<GHT, GHT> g = App.getExampleGraph();
//		
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

	public static void main(String[] args) {
			
		testJsonFormatter();
		
	}

}
