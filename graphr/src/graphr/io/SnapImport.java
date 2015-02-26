package graphr.io;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
		String edgeFileGplus = "../data/gplus/100129275726588145876.edges"; 
		
		Graph<GHT, GHT> parsedGraph = si.parseEdgeFile(edgeFileFacebook);
	}

	
	/**
	 * Parses given edge file but does not fill features. Data are left empty.
	 * @param edgeFile File from which to import graph
	 * @param directed Are edges directed or not?
	 * @return Graph that represent 
	 */
	public Graph<GHT, GHT> parseEdgeFile(String edgeFile) {
		FileReader fr;
		
		log.entry();
		
		Graph<GHT, GHT> parsedGraph = new Graph<GHT, GHT>();
	
		// open file
		try {
			fr = new FileReader(edgeFile);
		} catch (FileNotFoundException e) {
			//e.printStackTrace();
			log.error("Error while opening edge file for parsing", e);
			return null;
		}
		
		// wrap file into buffered reader
		BufferedReader in = new BufferedReader(fr);
		
		// read line by line and parse them
		String line;
		Integer lineNumber = 1;
		try {
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
			log.error("Error while reading edge file at line " + lineNumber, e);
			parsedGraph = null;
		}
		
		// close file
		try {
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Error while closing edge file used for parsing", e);
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
	
}
