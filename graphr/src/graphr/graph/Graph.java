package graphr.graph;

//import graphr.data.JsonArrayState;
//import graphr.data.JsonKeyValueState;

import graphr.data.GHT;
import graphr.data.JsonArrayState;
import graphr.data.JsonKeyValueState;
import graphr.data.PrimData;
import graphr.io.FileSystemHandler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;
import java.util.Hashtable;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Graph<DV extends GraphData, DE extends GraphData> {
	
	private static Logger log = LogManager.getLogger(); 
	
	Hashtable<Long, Vertex<DV,DE>> vertices;
	
	public Graph() {
		vertices = new Hashtable<Long,Vertex<DV,DE>>();
	}
	
	public void addVertex(Vertex<DV,DE> v) {
		vertices.put(new Long(v.id), v);
	}
	
	/**
	 * Finds vertex of given ID
	 * @param id Identifier of vertex
	 * @return Vertex with given ID, if does not exist then null
	 */
	public Vertex<DV,DE> getVertex(long id) {		
		return vertices.get(new Long(id));
	}
	
	public Hashtable<Long, Vertex<DV,DE>> getVerticesAsHashtable() {
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
		
		log.debug("At beginning of json visitor ");
		
		JsonArrayState jsonEdges = new JsonArrayState();
		
		long iteration = 0;
		log.debug("Starting at: " + iteration);
		
		for (Vertex<DV, DE> v : vertices.values()) {
			log.debug("loaded vertices");
			for (Edge<DV, DE> e: v.edges.values()) {
				iteration ++;
				log.debug("it " + iteration);
				
				JsonKeyValueState jsonEdge = new JsonKeyValueState();
				Vertex<DV, DE> vTarget = e.getTarget();
				
				GHT sGHT = (GHT) v.getData();
				PrimData os = sGHT.getTable().get("vislabel");
				
				GHT tGHT = (GHT) vTarget.getData();
				PrimData ot = tGHT.getTable().get("vislabel");
				
				if (!((ot == null) && (os == null))) {
					jsonEdge.add("source", os != null ? os.s() : "null");
					jsonEdge.add("target", ot != null ? ot.s() : "null");
				
					jsonEdge.add("type", "suit");
					jsonEdges.add(jsonEdge.getAsJson());
				}
			}
		}
		
		FileSystemHandler.getInstance().write(jsonEdges.getAsJson(), 
			"/Users/pjanacik/Github/graph/graphlens/basicvis_live.json");
	}
	
	
	public void deleteVerticesWithoutRightProterty(String attribute, PrimData value){

		Hashtable<Long, Vertex<DV,DE>> delitable = new Hashtable<>();
		for (Vertex<DV, DE> v : vertices.values()) {
			GHT data = (GHT) v.getData();
			if(data.getTable().get(attribute)==null|| !data.getTable().get(attribute).getAsJson().equals(value.getAsJson())){
				delitable.put(v.id, v);
			}else{
			}
		}

		for (Vertex<DV, DE> v : delitable.values()) {
			vertices.remove(v.getId());
		}
	}
	

	public void deleteEdgesWithoutRightProterty(String attribute, PrimData value){
		
		for (Vertex<DV, DE> v : vertices.values()) {
			Hashtable<Long, Edge<DV,DE>> delitable = new Hashtable<>();

			for (Edge<DV, DE> e : v.getEdges()){
				GHT data = (GHT) e.getData();
				if(data.getTable().get(attribute)==null|| !data.getTable().get(attribute).getAsJson().equals(value.getAsJson())){
					delitable.put(e.id,e);
				}else{
				}
			}
			
			for (Edge<DV, DE> e : delitable.values()) {
				v.removeEdgeOnBothSides(e);
			}			
			
		}
	}
	
	
	public void createJsonString(String filename){
		log.debug("At beginning of createJsonString() ");

		try {
	        File file;
	        FileWriter writer;
	        file = new File(filename);
            writer = new FileWriter(file);
	
            writer.write("{\n"
            		+ "\t\"vertices\":[\n");
            
            boolean firstvertex=true;
			for (Vertex<DV, DE> v : vertices.values()) {
				if(firstvertex){
					firstvertex = false;
				}else{
					writer.write("\t\t,\n");
				}
				//ausgabe soweit wie es geht
				writer.write("\t\t{\n"
						+ "\t\t\t\"type\":\"Vertex\",\n"
						+ "\t\t\t\"id\":" + v.getId() + ",\n"
						+ "\t\t\t\"data\":{\n");

				//Daten wieder mit laufnummer
				GHT vertexData = (GHT) v.getData();
				
				boolean firstVertexData = true;
				for(String dataKey : vertexData.getTable().keySet()){
					if(firstVertexData){
						firstVertexData = false;
					}else{
						writer.write(",\n");
					}
					writer.write("\t\t\t\t\"" + dataKey + "\":\"" + vertexData.getTable().get(dataKey) + "\"");
				}
				writer.write("\n");
				
				//Zwischenlayout
				writer.write("\t\t\t},\n"
						+"\t\t\t\"edges\":[\n");
				//edges wieder mit laufnummer
				boolean firstEdge = true;
				for(Edge e : v.getEdges()){
					if(firstEdge){
						firstEdge=false;
					}else{
						writer.write("\t\t\t\t,\n");						
					}
					writer.write("\t\t\t\t{\n"
							+ "\t\t\t\t\t\"id\":" + e.getId() + ",\n"
							+ "\t\t\t\t\t\"type\":\"Edge\",\n"
							+ "\t\t\t\t\t\"target\":" + e.getTargetId() + ",\n"
							+ "\t\t\t\t\t\"data\":{\n");
					//edgedaten wieder mit laufnummer
					GHT edgeData = (GHT) e.getData();
					boolean firstEdgeData = true;
					for(String edgeDataKey : edgeData.getTable().keySet()){
						if(firstEdgeData){
							firstEdgeData = false;
						}else{
							writer.write(",\n");
						}
						writer.write("\t\t\t\t\t\t\"" + edgeDataKey + "\":\"" + edgeData.getTable().get(edgeDataKey) + "\"");
					}
					writer.write("\n");
					
					writer.write("\t\t\t\t\t}\n");
					writer.write("\t\t\t\t}\n");						
					
				}
				//Vertexende
				writer.write("\t\t\t]\n");
				writer.write("\t\t}\n");								
			}
				
				
			writer.write("\t],\n"
					+ "\t\"type\":\"Graph\"\n"
					+ "}");
			
	        writer.flush();
		    writer.close();
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		log.debug("Finished createJsonString() ");		
	}
}
