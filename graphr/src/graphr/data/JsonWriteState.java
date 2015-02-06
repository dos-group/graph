package graphr.data;

import java.util.Hashtable;

public class JsonWriteState {
	
	private Hashtable<String, String> table;
	
	public JsonWriteState() {
		table = new Hashtable<String, String>();
	}
	
	public void add(String key, Object value) {
		table.put(key, value.toString());
	}
	
	public String toJson() {
		
		return JSONFacility.getInstance().generateJson(table);
	}

}
