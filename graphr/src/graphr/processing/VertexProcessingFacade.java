package graphr.processing;

import graphr.data.PrimData;
import graphr.graph.Vertex;

public interface VertexProcessingFacade {
	
	//-- Getting and setting local values
	
	public PrimData getValue(String key);
	public void setValue(String key, PrimData value);
	public long getId();

	//-- Agent replication
	
	public void broadcast();
	

}
