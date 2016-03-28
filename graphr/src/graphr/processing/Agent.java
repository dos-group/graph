package graphr.processing;

import graphr.graph.Edge;


/**
 * Parent class for all graph's agents. User is expected to extend this class and implemented given methods.
 *
 */
public abstract class Agent {

	protected VertexProcessingFacade v;
	
	public void setVertexProcessingFacade(VertexProcessingFacade v) {
		this.v = v;
	}
	
	public abstract Agent getCopy();
	
	public abstract void setUsedEdge(Edge<?, ?> e);
	
	/**
	 * Called before simulation starts to do some initialization. Here comes user's code.
	 */
	public void runBefore(){}
	
	/**
	 * Called after simulation stops to do some clean up. Here comes user's code.
	 */
	public void runAfter(){}

	/**
	 * Perform computation on given vertex and decide where to move in the next step
	 * @param vertex On which vertex to perform computation
	 * @return null if agent halts otherwise where to move next
	 */
	public abstract void runStep();
	
}
