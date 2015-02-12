package graphr.data;

import graphr.graph.GraphData;
import graphr.graph.GraphDataVisitor;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Map;

public class GHT implements GraphData {
	private static final long serialVersionUID = 4824632181734657401L;

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
	
	//-- Getters

	public Hashtable<String, PrimData> getTable() {
		return table;
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

	/**
	 * Compares this object with other GHT whether keys and values are identical. Comparison can be either
	 * strict type (type of value is checked) or not (string representation of values are compared).
	 * @param other To which table to compare data
	 * @param strictTypes True if strict type comparison or not.
	 * @return True if GHT's are equal otherwise false
	 */
	public boolean compareTables(GHT other, boolean strictTypes) {
		
		if(table.entrySet().size() != other.table.entrySet().size())
			return false;
		
		for(Map.Entry<String, PrimData> myEntry: table.entrySet()) {
			PrimData otherValue = other.table.get(myEntry.getKey());
			if(otherValue == null)
				return false;
			
			if(strictTypes) {
				if(!myEntry.getValue().equals(otherValue)) {
					return false;
				}
			} else {
				if(!myEntry.getValue().toString().equals( otherValue.toString() ))
					return false;
			}
			
		}
		
		return true;
	}

	
	//-- Generic Converters
	
	public static String primDataToString(String key, PrimData d) {
		
		return "(" + key + "," + d.getDataAbbrev() + "," + d.toString() + ")"; 
		
	}


	@Deprecated
	public String getAsJson() {
		JsonKeyValueState j = new JsonKeyValueState();
		
        for (String k : table.keySet()) {
        	j.add(k, table.get(k).getAsJson());
        }
        
        return j.getAsJson();
	}

	/**
	 * Part of the visitor design pattern -accept method
	 */
	@Override
	public Serializable accept(GraphDataVisitor visitor) {
		return visitor.visit(this);
	}
	
}
