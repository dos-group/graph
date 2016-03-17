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
	
	public int size() {
		return a.size();
	}

	public String getAsJson() {
		
		if(a.size() == 0)
			return "null";
		
		StringBuilder sbuilder = new StringBuilder();
		sbuilder.append("[");
		
		for (int i = 0; i < a.size(); i ++) {
			sbuilder.append(a.get(i));
			sbuilder.append(i < a.size() - 1 ? "," : "]");
		}
	
		return sbuilder.toString();
		
	}
	
}
