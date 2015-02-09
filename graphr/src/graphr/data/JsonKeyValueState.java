package graphr.data;

import java.util.Hashtable;

public class JsonKeyValueState {
	
	private Hashtable<String, String> t;
	
	public JsonKeyValueState() {
		t = new Hashtable<String, String>();
	}
	
	public void add(String key, String value) {
		t.put(key, value);
	}
	
	public String getAsJson() {
		
		String s = "{";
		
		int remainingKeys = t.size();
	
		for (String k : t.keySet()) {
			
			String valueString = t.get(k);
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
		
		if (s.matches("\\[.*\\]")) {
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
