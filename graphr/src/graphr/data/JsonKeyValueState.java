package graphr.data;

import java.util.Hashtable;

public class JsonKeyValueState {
	
	private Hashtable<String, Object> t;
	
	public void add(String key, Object value) {
		if (t == null) {
			t = new Hashtable<String, Object>();
		}
		t.put(key, value.toString());
	}
	
	public String toJson() {
		
		return JsonFacility.getInstance().generateJsonFromStringTable(t);
	}

}
