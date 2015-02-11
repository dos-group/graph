package graphr.io;

import graphr.data.GHT;
import graphr.data.GraphData;
import graphr.data.GraphDataVisitor;
import graphr.data.JsonKeyValueState;

import java.io.Serializable;

/**
 * Part of the visitor design pattern for graph data -implementing customized JSON format
 *
 * @param <DV>
 */
public class GHTJsonVisitor implements GraphDataVisitor {

	@Override
	public Serializable visit(GraphData data) {
		
		if(data == null)
			return "null";
		
		GHT ght = (GHT)data;
		
		JsonKeyValueState j = new JsonKeyValueState();
		
        for (String k : ght.getTable().keySet()) {        	
        	j.add(k, ght.getTable().get(k).getAsJson());
        }
        
        return j.getAsJson();

	}


}
