package graphr.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import graphr.data.GHT;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;

/**
 * Class with methods that import graphs from SNAP ({@link http://snap.stanford.edu/data/}) into
 * our graph model.
 *
 */
public class SnapImport {
	private static Logger log = LogManager.getLogger();
	
	public static void main(String[] args) {
		SnapImport si = new SnapImport();
		String edgeFileFacebook = "../data/facebook/0.edges";
		String featFileFacebook = "../data/facebook/0.feat";
		String featNamesFileFacebook = "../data/facebook/0.featnames";
		
		String edgeFileGplus = "../data/gplus/100129275726588145876.edges"; 
		
		
		Graph<GHT, GHT> parsedGraph = si.parseEdgeFile(edgeFileFacebook);

		JsonVisitor<GHT, GHT> jsonVisitor = new JsonVisitor<GHT, GHT>();		
		parsedGraph.accept(jsonVisitor);		
		String graphSerialized = jsonVisitor.getJsonString();
		
		log.debug("---- Imported and serialized graph:   " + graphSerialized);

		Graph<GHT, GHT> graphDeserialized = jsonVisitor.parseJsonString(graphSerialized);

		
		if(!si.parseFeatures(parsedGraph, featFileFacebook, featNamesFileFacebook)) {
			log.error("Could not parse features");
			return;
		}
		
		
	}

	
	/**
	 * Parses given edge file but does not fill features. Data are left empty.
	 * @param edgeFile File from which to import graph
	 * @param directed Are edges directed or not?
	 * @return Graph that represent 
	 */
	public Graph<GHT, GHT> parseEdgeFile(String edgeFile) {
		
		log.entry();
		
		Graph<GHT, GHT> parsedGraph = new Graph<GHT, GHT>();
	
		// open file
		try (BufferedReader in = new BufferedReader(new FileReader(edgeFile))) {

			// read line by line and parse them
			String line;
			Integer lineNumber = 1;

			while((line = in.readLine()) != null) {
				log.debug("Line: " + lineNumber + ": " + line);
				
				// parse line into two string
				String[] pa = line.split(" ");
				
				if(pa.length != 2) {
					log.warn("Wrong format of line " + lineNumber + ", expecting two integers separated by space: \"" + line + "\"");
					++lineNumber;
					continue;
				}
				
				// get source vertex object (if does not exist, create it)
				Integer srcVertexId = new Integer(pa[0]);
				Vertex<GHT, GHT> srcVertex = getVertex(parsedGraph, srcVertexId); 
										
				// get destination vertex object (if does not exist, create it)
				Integer dstVertexId = new Integer(pa[1]);				
				Vertex<GHT, GHT> dstVertex = getVertex(parsedGraph, dstVertexId);
				
				// check if there is already such edge, if not then create a new one
				if( isDuplicitEdge(srcVertex, dstVertex) ) {
					log.warn("Duplicit edge! Line: " + lineNumber);
				} else {
					// create new edge and attach it to the graph
					Edge<GHT, GHT> newEdge = new Edge<GHT, GHT>(dstVertex, new GHT());
					srcVertex.addEdge(newEdge);
				}
								
				++lineNumber;
			}

			
		} catch (IOException e) {
			log.error("IO error while processing edge file", e);
			return null;
		}
				
		return parsedGraph;
	}
	
	/**
	 * Checks if there is already an edge from the source to the destination vertex
	 * @param srcVertex Source vertex
	 * @param dstVertex Destination vertex
	 * @return True if there is an edge otherwise false
	 */
	boolean isDuplicitEdge(Vertex<GHT,GHT> srcVertex, Vertex<GHT,GHT> dstVertex) {
		for(Edge<GHT, GHT> edge : srcVertex.getEdges()) {
			if(dstVertex.getId() == edge.getTargetId()) {
				return true;
			}
		}		
		return false;
	}
	
	/**
	 * Searches for vertex of given ID. If does not exist, creates a new one with given
	 * ID and inserts it into the graph.
	 * @param graph Graph in which we search for vertex
	 * @param vertexId ID of the vertex to be searched
	 * @return Found or new vertex object
	 */
	Vertex<GHT,GHT> getVertex(Graph<GHT, GHT> graph, Integer vertexId) {
		Vertex<GHT, GHT> vertex = graph.getVertex(vertexId);
		if(vertex == null) {
			vertex = new Vertex<GHT, GHT>(new GHT());
			vertex.setId(vertexId);
			graph.addVertex(vertex);
		}				
		
		return vertex;
	}
	
	/**
	 * Parses features and updates the graph
	 * @param graph Graph that will be updated with parsed features
	 * @param featureFile File containing map of vertices to features
	 * @param featureNamesFile File with definition of features
	 * @return True if no error, otherwise false
	 */
	public boolean parseFeatures(Graph<GHT, GHT> graph, String featureFile, String featureNamesFile) {		
		log.entry();
		
		try (BufferedReader in = new BufferedReader(new FileReader(featureFile))) {
 		
			// parse and load all features as map of feature ID to string
			Map<Integer, String> pfn = parseFeatureNames(featureNamesFile);
			
			Integer lineNumber = 1;
			String line;
			while( (line = in.readLine()) != null ) {
				String parsedLine[] = line.split(" ");				
				if((parsedLine.length - 1) != pfn.size()) {
					log.error("Feature list and feature names does not match in the size at line: " + lineNumber + ", for vertex ID: " + parsedLine[0]);
					return false;
				}
				
				//log.debug("Feature names size: " + pfn.size() + ", parsed feature list: " + parsedLine.length);
				
				// get the vertex
				Vertex<GHT, GHT> vertex = graph.getVertex( new Integer(parsedLine[0]) );
				
				// check each feature flag whether set to 1 and then insert it
				for(int featIndexFlag = 1; featIndexFlag < parsedLine.length; featIndexFlag++) {
					Integer flag = new Integer(parsedLine[featIndexFlag]); 
					if(flag == 1) {
						String featValue = pfn.get(featIndexFlag - 1);
						log.debug("Vertex " + parsedLine[0] + ": " + featValue);
						// parse feature name and get value
						
						//vertex.getData().put(key, s);
					}
				}
				
				
				++lineNumber;
			}
			
			
		} catch (IOException e) {
			log.error("IO error while accessing feature files", e);
			return false;
		} 
		
		return true;
	}

	/**
	 * Parses feature names file that contains list of feature ID and its value
	 * @param featureNamesFile Path to file to be processed
	 * @return Map of feature ID to its string value
	 */
	public Map<Integer, String> parseFeatureNames(String featureNamesFile) {
		Map<Integer, String> m = new HashMap<Integer, String>();
		
		try(BufferedReader in = new BufferedReader(new FileReader(featureNamesFile))) {
			Integer lineNumber = 1;
			String line;
			while( (line = in.readLine()) != null ) {
				String parsedLine[] = line.split(" ", 2);
				
				if(parsedLine.length != 2) {
					log.error("Error parsing feature names file at line " + lineNumber + ": expecting format: ID-space-String");
					return null;
				}
				
				Integer featId = new Integer(parsedLine[0]);				
				m.put(featId, parsedLine[1]);
				++lineNumber;
			}
			
			
			
		} catch (IOException e) {
			log.error("Error while parsing feature names file", e);
			m = null;
		}
		
		return m;
	}
	
}
