package graphr.data;

import graphr.graph.GraphData;

import java.io.Serializable;

public interface JsonReadableWritable extends GraphData {
	
	public String getAsJson();
	public void setFromJson();

}
