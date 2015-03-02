package graphr.io;

import graphr.App;
import graphr.algorithms.ConnectionDistanceAgentPopulator;
import graphr.data.GHT;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;
import graphr.processing.AgentManager;
import graphr.processing.AgentPopulator;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

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
		String featFileGplus = "../data/gplus/100129275726588145876.feat";
		String featNamesFileGplus = "../data/gplus/100129275726588145876.featnames";
		
		String edgeFileTwitter = "../data/twitter/100318079.edges";
		String featFileTwitter = "../data/twitter/100318079.feat";
		String featNamesFileTwitter = "../data/twitter/100318079.featnames";
		 
		String exportFile = "out.json";
		
		String edgeFile = edgeFileGplus;
		String featFile = featFileGplus;
		String featNamesFile = featNamesFileGplus;
		
		SnapImportIdMapper idMapper =  si.new ImportIdGplus();		
		Graph<GHT, GHT> parsedGraph = si.parseEdgeFile(edgeFile, idMapper);
		if(parsedGraph == null) {
			log.error("Could not parse edge file");
			return;
		}

		SnapImportFeature sif =  si.new ImportGplusFeature();		
//		SnapImportFeature sif =  si.new ImportTwitterFeature();
		if(!si.parseFeatures(parsedGraph, sif, idMapper, featFile, featNamesFile)) {
			log.error("Could not parse features");
			return;
		}
		
		AgentPopulator p = new ConnectionDistanceAgentPopulator(0, 5);
		AgentManager m = new AgentManager(parsedGraph, p);
		m.runProcessing(10);
		
		// Output
		
		FileSystemHandler.getInstance().write(App.graphToJsonString(parsedGraph), exportFile);
		
		log.debug("Saved");
		
		JsonVisitor<GHT, GHT> jsonVisitor = new JsonVisitor<GHT, GHT>();
		Graph<GHT, GHT> graphDeserialized = jsonVisitor.parseJsonString( FileSystemHandler.getInstance().read(exportFile) );
		log.debug("Read");
		
		
	}

	/**
	 * All-in-one method to completely parse graphs including features
	 * @param edgeFile Path to file containing edges
	 * @param featName Path to file containing map of vertex to feature ID
	 * @param featNamesFile Path to file containing map of feature ID to its value
	 * @param featureParser Reference to object providing correct parsing of features into keyname-value pair
	 * @param SnapImportIdMapper Reference to service providing mapping of raw form IDs (string) to our internal id data type (long) 
	 * @return Fully initialized graph
	 */
	public Graph<GHT, GHT> parseAll(String edgeFile, String featName, 
			String featNamesFile, SnapImportFeature featureParser, SnapImportIdMapper idMapper) {
		
		Graph<GHT, GHT> parsedGraph = parseEdgeFile(edgeFile, idMapper);
		
		if(parsedGraph != null && featName != null && featNamesFile != null) {			
			if(!parseFeatures(parsedGraph, featureParser, idMapper, featName, featNamesFile)) {
				return null;
			}			
		}
				
		return parsedGraph;
	}
	
	/**
	 * Parses given edge file but does not fill features. Data are left empty.
	 * @param edgeFile File from which to import graph
	 * @param directed Are edges directed or not?
	 * @return Graph that represent 
	 */
	public Graph<GHT, GHT> parseEdgeFile(String edgeFile, SnapImportIdMapper idMapper) {
		
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
				Long srcVertexId = idMapper.getId(pa[0]);
				Vertex<GHT, GHT> srcVertex = getVertex(parsedGraph, srcVertexId); 
										
				// get destination vertex object (if does not exist, create it)
				Long dstVertexId = idMapper.getId(pa[1]);				
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
	Vertex<GHT,GHT> getVertex(Graph<GHT, GHT> graph, Long vertexId) {
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
	public boolean parseFeatures(Graph<GHT, GHT> graph, SnapImportFeature featureParser, SnapImportIdMapper idMapper, 
			String featureFile, String featureNamesFile) {		
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
				
				// get the vertex -if there is no vertex it means it is not connected, we have to create a new one
				Vertex<GHT, GHT> vertex = getVertex(graph, idMapper.getId(parsedLine[0]) );						
								
				// check each feature flag whether set to 1 and then insert it
				for(int featIndexFlag = 1; featIndexFlag < parsedLine.length; featIndexFlag++) {
					Integer flag = new Integer(parsedLine[featIndexFlag]); 
					if(flag == 1) {
						String featDef = pfn.get(featIndexFlag - 1);						
						// parse feature name and get value
						
						String featName = featureParser.getFeatureName(featDef);
						String featValue = featureParser.getFeatureValue(featDef);
						log.debug("Vertex " + parsedLine[0] + ": " + featDef + ": \"" + featName + "\" -> \"" + featValue + "\"");
												
						vertex.getData().put(featName, featValue);
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
	
	/**
	 * Parses Gplus files. We have to do it separately because Googles's IDs are 21 char long array. We have to
	 * convert it to the regular ID's. 
	 * @param edgeFile
	 * @param featFile
	 * @param featNamesFile
	 * @return
	 */
	public Graph<GHT, GHT> parseGplusFiles(String edgeFile, String featFile, String featNamesFile) {
		log.entry();		
		Graph<GHT, GHT> parsedGraph = new Graph<GHT, GHT>();
		

		return parsedGraph;
	}
	
	
	/**
	 * Implements parsing of feature names for Facebook, example of the line of feature name format:
	 * <pre>
	 * 206 work;start_date;anonymized feature 160 
	 * </pre>
	 * That stands for feature name <i>work;start_date</i> and value <i>anonymized feature 160</i>  
	 */
	public class ImportFacebookFeature implements SnapImportFeature {

		@Override
		public String getFeatureName(String feature) {
			return feature.substring(0, feature.lastIndexOf(";"));
		}

		@Override
		public String getFeatureValue(String feature) {
			return feature.substring( feature.lastIndexOf(";") + 1);
		}
		
	}

	/**
	 * Implements parsing of feature names for Twitter. <b>There are only feature names but not values!</b> For instance:
	 * <pre>
	 * 81 #TVZAlbuns
	 * </pre>
	 * That stands for feature name <i>#TVZAlbuns</i> and no value.  
	 */
	public class ImportTwitterFeature implements SnapImportFeature {

		/**
		 * Twitters feature has only name but not value
		 */
		@Override
		public String getFeatureName(String feature) {
			return feature;
		}

		/**
		 * There is no value in twitter's feature 
		 */
		@Override
		public String getFeatureValue(String feature) {
			return "";
		}
		
	}

	
	/**
	 * Implements parsing of feature names for Google Plus, example of the line of feature name format:
	 * <pre>
	 * 1303 university:University of Toledo
	 * </pre>
	 * That stands for feature name <i>work;start_date</i> and value <i>anonymized feature 160</i>  
	 */
	public class ImportGplusFeature implements SnapImportFeature {

		@Override
		public String getFeatureName(String feature) {
			return feature.substring(0, feature.lastIndexOf(":"));
		}

		@Override
		public String getFeatureValue(String feature) {
			return feature.substring( feature.lastIndexOf(":") + 1);
		}
		
	}
	
	
	/**
	 * Makes direct mapping of given ID as string into Long class
	 */
	public class ImportIdNoConversion implements SnapImportIdMapper {

		@Override
		public Long getId(String rawId) {
			return new Long(rawId);
		}
		
	}
	
	/**
	 * Provides mapping from Google Plus (21 CHAR) to our long data type 
	 */
	public class ImportIdGplus implements SnapImportIdMapper {

		Map<String, Long> idMap = null;
		long idCounter = 0L; 
		
		@Override
		public Long getId(String rawId) {
			
			if(idMap == null) {
				idMap = new HashMap<String, Long>();
			}
			
			Long id = idMap.get(rawId.trim());
			if(id == null) {
				id = ++idCounter;
				idMap.put(rawId.trim(), id);
			}
			
			return id;
		}
		
	}
	
}
