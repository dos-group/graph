package graphr.graph;

import java.io.Serializable;

public interface GraphData extends Serializable {

	/**
	 * Part of the visitor design pattern -all graph data must implement accept method
	 * @param visitor Reference to a particular visitor
	 * @return Object that is serializable
	 */
	Serializable accept(GraphDataVisitor visitor);

}
