package graphr.data;

import java.util.Hashtable;

import org.apache.commons.lang3.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class JsonKeyValueState {

	private Hashtable<String, String> t;

	public JsonKeyValueState() {
		t = new Hashtable<String, String>();
	}

	public void add(String key, String value) {
		t.put(key, value);
	}
	
	public String get(String key) {
		return t.get(key);
	}

	public String getAsJson() {

		// String s = "{";
		//
		// int remainingKeys = t.size();
		//
		// if (remainingKeys == 0) {
		// return "null";
		// }
		//
		// for (String k : t.keySet()) {
		// String valueString = t.get(k);
		// s += "\""
		// + k
		// + "\":"
		// + (useQuotationMarks(valueString) ? "\"" + valueString
		// + "\"" : valueString)
		// + (remainingKeys > 1 ? "," : "}");
		//
		// remainingKeys--;
		// }
		//
		// return s;

		if (t.size() == 0)
			return JSONObject.NULL.toString();

		JSONObject obj = new JSONObject();

		for (String k : t.keySet()) {
			String value = t.get(k);

			if (value.startsWith("{") && value.endsWith("}")) {
				obj.put(k, new JSONObject(value));
			} else if (value.startsWith("[") && value.endsWith("]")) {
				obj.put(k, new JSONArray(value));
			} else {
				value = StringEscapeUtils.escapeJson(value);

				obj.put(k, JSONObject.stringToValue(value));
			}
		}

		return obj.toString();
	}

	// -- Analytical Methods

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
