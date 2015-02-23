package graphr.processing;

import graphr.data.PrimData;
import graphr.graph.Vertex;

public interface ProcessingFacade {
	
	//-- Getting and setting local values
	
	public PrimData getLocalValue(String key);
	public void setLocalValue(String key, PrimData value);
	
	//-- Agent replication
	
	public void broadcast();
	

}
