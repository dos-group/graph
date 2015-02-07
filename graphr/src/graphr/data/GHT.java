package graphr.data;

import java.util.Hashtable;

public class GHT implements GraphData {
	         // Stands for graph hashtable

	Hashtable<String, PrimData> table;
	
	//-- Constructors
	
	public GHT() {
		table = new Hashtable<String, PrimData>();
	}
	
	public GHT(String source) {
		this();
		setFromString(source);
	}
	
	//-- Setters
	
	public void put(String key, PrimData value) {
		table.put(key, value);
	}
	
	public void put(String key, int i) {
		put(key, new PrimData(i));
	}
	
	public void put(String key, long l) {
		put(key, new PrimData(l));
	}
	
	public void put(String key, double d) {
		put(key, new PrimData(d));
	}
	
	public void put(String key, boolean b) {
		put(key, new PrimData(b));
	}
	
	public void put(String key, String s) {
		put(key, new PrimData(s));
	}
	
	//-- Conversions
	
	public void setFromString(String s) {
		String a = s.trim();
		String asub = a.substring(2, a.length() - 2);
		String[] strings = asub.split("[)][,][(]");
		
		for (String e : strings) {
			String[] es = e.split(",");
			
			table.put(es[0], new PrimData(es[1], es[2]));
		}
		
	}

	
	public String toString() {
		
		String s = "{";
		int remainingKeys = table.size();
		
		for (String k : table.keySet()) {
			
			s += GHT.primDataToString(k, table.get(k));
			
			remainingKeys --;
			
			if (remainingKeys > 0) {
				s += ",";
			}
			
		}
		
		return s + "}";
		
	}
	
	//-- Generic Converters
	
	public static String primDataToString(String key, PrimData d) {
		
		return "(" + key + "," + d.getDataAbbrev() + "," + d.toString() + ")"; 
		
	}

	@Override
	public String getAsJson() {
		JsonKeyValueState j = new JsonKeyValueState();
		
        for (String k : table.keySet()) {
        	
//        	System.out.println("Key:" + k);
//        	System.out.println("Value:" + table.get(k).getAsJson());
        	j.add(k, table.get(k).getAsJson());
        }
        
        return j.toJson();
	}

	@Override
	public void setFromJson() {
		
	}
	
}
