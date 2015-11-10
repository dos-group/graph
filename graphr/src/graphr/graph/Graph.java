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
	
	
	public void deleteVerticesWithoutVisibility(){

		Hashtable<Long, Vertex<DV,DE>> delitable = new Hashtable<>();
		for (Vertex<DV, DE> v : vertices.values()) {
			GHT data = (GHT) v.getData();
			if(data.getTable().get("visible")==null|| data.getTable().get("visible").b()==false){
				//System.out.println("der Knoten " + v.id + " ist nicht sichtbar!");
				delitable.put(v.id, v);
			}else{
				//System.out.println("der Knoten " + v.id + " ist sichtbar!");
			}
		}
		

		for (Vertex<DV, DE> v : delitable.values()) {
			vertices.remove(v.getId());
		}
	}

	public void deleteEdgesWithoutVisibility(){//DOES NOT WORK

		for (Vertex<DV, DE> v : vertices.values()) {
			Hashtable<Long, Edge<DV,DE>> delitable = new Hashtable<>();

			for (Edge<DV, DE> e : v.getEdges()){
				GHT data = (GHT) e.getData();
				if(data.getTable().get("visible")==null|| data.getTable().get("visible").b()==false){
					delitable.put(e.id,e);
				}else{
				}
			}
			

			for (Edge<DV, DE> e : delitable.values()) {
				v.getEdges().remove(e);
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
            
            int aktvertex = 0;
            int maxverteces = vertices.values().size() ;
			for (Vertex<DV, DE> v : vertices.values()) {
				aktvertex++;
				//ausgabe soweit wie es geht
				writer.write("\t\t{\n"
						+ "\t\t\t\"type\":\"Vertex\",\n"
						+ "\t\t\t\"id\":" + v.getId() + ",\n"
						+ "\t\t\t\"data\":{\n");

				//Daten wieder mit laufnummer
				GHT data = (GHT) v.getData();
				int aktVertexData = 0;
				int maxVertexData = data.getTable().keySet().size();
				for(String dataKey : data.getTable().keySet()){
					aktVertexData++;
					if (aktVertexData<maxVertexData){//es gibt noch mindestens ein weiteres Datum - daher mit komma
						writer.write("\t\t\t\t\"" + dataKey + "\":\"" + data.getTable().get(dataKey) + "\",\n");
					}else{
						writer.write("\t\t\t\t\"" + dataKey + "\":\"" + data.getTable().get(dataKey) + "\"\n");						
					}					
				}
				
				//Zwischenlayout
				writer.write("\t\t\t},\n"
						+"\t\t\t\"edges\":[\n");
				//edges wieder mit laufnummer
				int aktedge = 0;
				int maxedge = v.getEdges().size();
				for(Edge e : v.getEdges()){
					aktedge++;
					writer.write("\t\t\t\t{\n"
							+ "\t\t\t\t\t\"id\":" + e.getId() + ",\n"
							+ "\t\t\t\t\t\"type\":\"Edge\",\n"
							+ "\t\t\t\t\t\"target\":" + e.getTargetId() + ",\n"
							+ "\t\t\t\t\t\"data\":{\n");
					//edgedaten wieder mit laufnummer
					GHT edgeData = (GHT) e.getData();
					int aktEdgeData = 0;
					int maxEdgeData = edgeData.getTable().keySet().size();
					for(String edgeDataKey : edgeData.getTable().keySet()){
						aktEdgeData++;
						if(aktEdgeData < maxEdgeData){ //es gibt noch mindestens ein weiteres Datum - daher mit komma
							writer.write("\t\t\t\t\t\t\"" + edgeDataKey + "\":\"" + edgeData.getTable().get(edgeDataKey) + "\",\n");
						}else{
							writer.write("\t\t\t\t\t\t\"" + edgeDataKey + "\":\"" + edgeData.getTable().get(edgeDataKey) + "\"\n");
						}
					}
					
					writer.write("\t\t\t\t\t}\n");
					if(aktedge<maxedge){ //es gibt noch mindestens eine weitere Edge - daher mit komma
						writer.write("\t\t\t\t},\n");
					}else{
						writer.write("\t\t\t\t}\n");						
					}
				}
				writer.write("\t\t\t]\n");
				if (aktvertex< maxverteces){//Es gibt noch mindestens einen weiteren - daher mit komma
					writer.write("\t\t},\n");
				}else{
					writer.write("\t\t}\n");					
				}
			}
				//Vertexende
				
				
				/*
				GHT data = (GHT) v.getData();
					writer.write(System.getProperty("line.separator")+
							"Vertex " + v.id + "#############################################################"+
							System.getProperty("line.separator"));
					for(String string : data.getTable().keySet()){
						if(string.contains("in_")){
							System.out.println(string + ":"+ data.getTable().get(string));			
							writer.write(string + ":"+ data.getTable().get(string));
						}
					}
					for(String string : data.getTable().keySet()){
						if(string.contains("out_")){
							System.out.println(string + ":"+ data.getTable().get(string));	
							writer.write(string + ":"+ data.getTable().get(string));		
						}
					}
				}
		*/
				 /*
				for (Edge<DV, DE> e: v.edges.values()) {
					System.out.println("Edge " + e.id);
	
					GHT edgeData = (GHT) e.getData();
					System.out.println("edge:::" + edgeData.getTable().get("startzeit"));
				}
				*/
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
