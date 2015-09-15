package graphr.io;

/**
 * Interface for class implementing parsing of feature name definition 
 */
public interface SnapImportFeature {

	/**
	 * Returns name of the feature from the feature string
	 */
	public String getFeatureName(String feature);
	
	/**
	 * Returns value of the feature from the feature string
	 */
	public String getFeatureValue(String feature);
	
}
