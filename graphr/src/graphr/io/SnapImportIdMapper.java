package graphr.io;

/**
 * Interface for all context classes providing mapping of IDs into our own IDs
 *
 */
public interface SnapImportIdMapper {
	
	/**
	 * For given raw ID in the String format find and return our valid ID
	 * @param rawId
	 * @return
	 */
	public Long getId(String rawId); 
	
}
