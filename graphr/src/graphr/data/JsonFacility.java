package graphr.data;

import java.util.HashSet;
import java.util.Hashtable;



// Assumes jar from here:
// https://code.google.com/p/quick-json/

// Can check valid Json with & format:
// http://jsonformatter.curiousconcept.com

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
	
	protected String generateJsonFromStringTable(Hashtable<String, Object> h) {
	
		return generator.generateJson(h);
	}
	
	protected String generateJsonFromSetTable(Hashtable<String, HashSet<String>> h) {
		
		return generator.generateJson(h);
	}
	
}
