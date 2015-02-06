package graphr.data;

import java.util.Hashtable;

// Assumes jar from here:
// https://code.google.com/p/quick-json/
import com.json.generators.JSONGenerator;
import com.json.generators.JsonGeneratorFactory;

public class JSONFacility {
	
	private static JSONFacility facility;
	private JsonGeneratorFactory factory;
    private JSONGenerator generator;
    private Hashtable<String, String> reusableHashtable;
	
    //-- Constructors
    
	public JSONFacility() {
		factory=JsonGeneratorFactory.getInstance();
		generator=factory.newJsonGenerator();
		reusableHashtable = new Hashtable<String, String>();
	}
	
	//-- Singleton
	
	public static JSONFacility getInstance() {
		if (facility == null) {
			facility = new JSONFacility();
		} 
		
		return facility;
	}
	
	//-- Writing
	
	public void clearWriteState() {
		reusableHashtable.clear();
	}
	
	public void addToWriteState(String key, String value) {
		reusableHashtable.put(key, value);
	}
	
	public String getWriteStateAsJson() {
		return generator.generateJson(reusableHashtable);
	}

	
	//-- Converters
	
	private <K,V> String stringFromData(Hashtable<K,V> h) {
		
        return generator.generateJson(h);
		
	}
	
	private <K,V> Hashtable<K,V> dataFromString(String s) {
		
		return null;
		
	}

}
