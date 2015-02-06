package graphr.data;

import java.util.Hashtable;


// Assumes jar from here:
// https://code.google.com/p/quick-json/
import com.json.generators.JSONGenerator;
import com.json.generators.JsonGeneratorFactory;

public class JsonFacility {
	
	private static JsonFacility facility;
	private JsonGeneratorFactory factory;
    private JSONGenerator generator;
    
	
    //-- Constructors
    
	public JsonFacility() {
		factory=JsonGeneratorFactory.getInstance();
		generator=factory.newJsonGenerator();

	}
	
	//-- Singleton
	
	public static JsonFacility getInstance() {
		if (facility == null) {
			facility = new JsonFacility();
		} 
		
		return facility;
	}
	
	//-- Converters
	
	protected String generateJson(Hashtable<String, String> h) {
	
		return generator.generateJson(h);
	}
	
//	private <K,V> String stringFromData(Hashtable<K,V> h) {
//		
//        return generator.generateJson(h);
//		
//	}
//	
//	private <K,V> Hashtable<K,V> dataFromString(String s) {
//		
//		return null;
//		
//	}

}
