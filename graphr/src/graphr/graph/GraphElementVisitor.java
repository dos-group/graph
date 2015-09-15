package graphr.graph;


/**
 * Attempt to create a visitor pattern for graph serialization. 
 * <br>
 * Interface for a visitor. 
 *
 */
public interface GraphElementVisitor {
	
	/**
	 * Called before graph, at the beginning
	 */
	void before();
	
	
	void visit(Graph<?,?> graph);
	void visit(Edge<?,?> edge);
	void visit(Vertex<?,?> vertex);
	
	/**
	 * Called after all elements were processed
	 */
	void after();
}
