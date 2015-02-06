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
		
		String s = "{";
		
		int remainingKeys = t.size();
	
		for (String k : t.keySet()) {
			
			String valueString = t.get(k).toString();
			s += "\"" + k + "\":" + (useQuotationMarks(valueString) ? 
					"\"" + valueString + "\"" : valueString)
					+ (remainingKeys > 1 ? "," : "}");
			
			remainingKeys --;
		}
		
		
		return s;
	}
	
	//-- Analytical Methods
	
	private boolean useQuotationMarks(String s) {
		
		if (s.equals("true") || s.equals("false") || s.equals("null")) {
			return false;
		}
		
		if (s.matches("[0-9]*\\.[0-9]+")) {
			// corresponds to usual reg exp
			// [0-9]*\.[0-9]+
			
			return false;
		}
		
		if (s.matches("[0-9]+")) {
			return false;
		}
		
		if (s.matches("\\{.*\\}")) {
			return false;
		}
	
		return true;
	}

}
