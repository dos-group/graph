package graphr.data;

import graphr.graph.GraphData;

public interface JsonReadableWritable extends GraphData {
	
	public String getAsJson();
	public void setFromJson();

}
