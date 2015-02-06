package graphr.data;

import java.util.Hashtable;

public class JsonKeyValueState {
	
	private Hashtable<String, String> t;
	
	public void add(String key, Object value) {
		if (t == null) {
			t = new Hashtable<String, String>();
		}
		t.put(key, value.toString());
	}
	
	public String toJson() {
		
		return JsonFacility.getInstance().generateJsonFromStringTable(t);
	}

}
