package graphr.processing;

import java.util.ArrayList;


/**
 * Parent class for all graph's agents. User is expected to extend this class and implemented given methods.
 *
 */
public abstract class Agent {

	protected ProcessingFacade facade;
	
	public Agent(ProcessingFacade facade) {
		this.facade = facade;
	}
	
	public abstract Agent getCopy();
	
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
