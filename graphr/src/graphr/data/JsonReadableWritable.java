package graphr.data;

import java.io.Serializable;

public interface JsonReadableWritable extends GraphData {
	
	public String getAsJson();
	public void setFromJson();

}
