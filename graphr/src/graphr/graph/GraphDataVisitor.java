package graphr.graph;

import java.io.Serializable;

/**
 * Visitor design pattern -interface for all classes implementing visitor's logic
 *
 * @param <D>
 */
public interface GraphDataVisitor {	
	Serializable visit(GraphData data);	
}
