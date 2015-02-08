package graphr.data;

import java.util.ArrayList;

public class JsonArrayState {

	private ArrayList<String> a;
	
	public JsonArrayState() {
		a = new ArrayList<String>();
	}
	
	public void add(String s) {
		a.add(s);
	}

	public String getAsJson() {
		
		String s = "[";
		
		for (int i = 0; i < a.size(); i ++) {
			s += a.get(i) + (i < a.size() - 1 ? "," : "");
		}
		
		return s + "]";
		
	}
	
}
