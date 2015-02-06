package graphr.data;

import java.util.HashSet;
import java.util.Hashtable;

public class JsonWriteState {
	
	private Hashtable<String, String> table;
	private HashSet<String> set;
	
	
	public void add(String key, Object value) {
		if (table == null) {
			table = new Hashtable<String, String>();
		}
		table.put(key, value.toString());
	}
	
	public void add(String value) {
		if (set == null) {
			set = new HashSet<String> ();
		}
		
	}
	
	public String toJson() {
		
		if (table == null) {
			Hashtable<String, HashSet<String>> t = new Hashtable<String, HashSet<String>>();
			t.put("Stringarray", set);
			return JsonFacility.getInstance().generateJsonFromSetTable(t);
		} else {
			return JsonFacility.getInstance().generateJsonFromStringTable(table);
		}
	}

}
