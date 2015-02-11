package graphr.simulation;

import graphr.graph.Graph;
import graphr.graph.Vertex;

/**
 * Parent class for all graph's agents. User is expected to extend this class and implemented given methods.
 *
 */
public abstract class Agent {

	Graph<?,?> graph;
	AgentManager manager;
	

	public Agent(AgentManager manager, Graph<?,?> graph) {
		this.manager = manager;
		this.graph = graph;
	}
	
	/**
	 * Called before simulation starts to do some initialization. Here comes user's code.
	 */
	public abstract void before();
	
	/**
	 * Called after simulation stops to do some clean up. Here comes user's code.
	 */
	public abstract void after();

	/**
	 * Perform computation on given vertex and decide where to move in the next step
	 * @param vertex On which vertex to perform computation
	 * @return null if agent halts otherwise where to move next
	 */
	public abstract Vertex<?,?> step(Vertex<?,?> vertex);
	

}
