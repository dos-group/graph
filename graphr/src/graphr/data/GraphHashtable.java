package graphr.data;

import java.util.Hashtable;

public class GraphHashtable {

	Hashtable<String, PrimData> table;
	
	//-- Constructors
	
	public GraphHashtable() {
		table = new Hashtable<String, PrimData>();
	}
	
	public GraphHashtable(String source) {
		
	}
	
	//-- Setters
	
	public void put(String key, PrimData value) {
		table.put(key, value);
	}
	
	//-- Conversions
	
	public void setFromString(String s) {
		String a = s.trim();
		String asub = a.substring(2, a.length() - 1 - 2);
		String[] strings = asub.split("),(");
		
		for (String e : strings) {
			String[] es = e.split(",");
			
			table.put(es[0], new PrimData(es[1], es[2]));
		}
		
	}
	
	public String toString() {
		
		String s = "(";
		int remainingKeys = table.size();
		
		for (String k : table.keySet()) {
			
			s += GraphHashtable.primDataToString(k, table.get(k));
			
			remainingKeys --;
			
			if (remainingKeys > 0) {
				s += ",";
			}
			
		}
		
		return s + ")";
		
	}
	
	//-- Generic Converters
	
	public static String primDataToString(String key, PrimData d) {
		
		return "(" + key + "," + d.getDataAbbrev() + "," + d.toString() + ")"; 
		
	}
	
}
