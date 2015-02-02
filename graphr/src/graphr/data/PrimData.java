package graphr.data;

public class PrimData {
	
	Object o;
	
	//-- Constructors
	
	public PrimData(String abbrev, String someString) {
		setFromSomeString(abbrev, someString);
	}
	
	public PrimData(int i) {
		set(i);
	}
	
	public PrimData(long l) {
		set(l);
	}
	
	public PrimData(double d) {
		set(d);
	}
	
	public PrimData(boolean b) {
		set(b);
	}
	
	public PrimData(String s) {
		set(s);
	}
	
	// -- Setters
	
	public void set(int i) {
		o = new Integer(i);
	}
	
	public void set(long l) {
		o = new Long(l);
	}
	
	public void set(double d) {
		o = new Double(d);
	}
	
	public void set(boolean b) {
		o = new Boolean(b);
	}
	
	public void set(String s) {
		o = s;
	}
	
	public void set(Object o) {
		this.o = o;
	}
	
	public void setFromSomeString(String abbrev, String s) {
		switch (abbrev){
		case "i": set(new Integer(s)); break;
		case "l": set(new Long(s)); break;
		case "d": set(new Double(s)); break;
		case "b": set(new Boolean(s)); break;
		default: set(s);
		}
			
	}
	
	//-- Getters
	
	public int i() {
		return ((Integer) o).intValue();
	}
	
	public long l() {
		return ((Long) o).longValue();
	}
	
	public double d() {
		return ((Double) o).doubleValue();
	}
	
	public boolean b() {
		return ((Boolean) o).booleanValue();
	}
	
	public String s() {
		return (String) o;
	}
	
	public String toString() {
		
		if (o instanceof Integer) {
			return ((Integer) o).toString();  
		}
		
		if (o instanceof Long) {
			return ((Long) o).toString();  
		}
		
		if (o instanceof Double) {
			return ((Double) o).toString(); 
		}
		
		if (o instanceof Boolean) {
			return ((Boolean) o).toString(); 
		}
		
		if (o instanceof String) {
			return (String) o;
		}
		
		throw new IllegalStateException("Wrapped object type is invalid.");
		
	}
	
	public String getDataAbbrev() {
		
		return PrimData.getDataAbbrev(o);
		
	}
	
	//-- Convenience Methods
	
	public static String getDataAbbrev(Object o) {
		
		if (o instanceof Integer) {
			return "i";  
		}
		
		if (o instanceof Long) {
			return "l";  
		}
		
		if (o instanceof Double) {
			return "d"; 
		}
		
		if (o instanceof Boolean) {
			return "b"; 
		}
		
		if (o instanceof String) {
			return "S";
		}
		
		throw new IllegalArgumentException("Examined object type invalid.");
		
	}

}
