package graphr.processing;

import graphr.graph.Graph;
import graphr.graph.Vertex;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Manages simulations of agents.
 * <BR>
 * <b>NOT FINISHED</b> 
 * Should be Vertex driven, i.e. run only such vertices that have some agents waiting there to run. 
 * In other words, make it "vertex driven".
 */
public class AgentManager implements ProcessingFacade {

	Hashtable<Integer, ArrayList<Agent>> schedule;
	Graph<?,?> graph;

	int localVertexId;
	Hashtable<Integer, ArrayList<Agent>> newSchedule;
	
		
	public AgentManager(Graph<?,?> graph) {
		schedule = new Hashtable<Integer, ArrayList<Agent>>();
		this.graph = graph;
	}
	
	/**
	 * Perform bulk step, i.e. for every vertex that has some waiting agents -run them as they "arrived".
	 * @return True if at least one agent executed otherwise false.
	 */
	public void runBulkStep() {
		
		newSchedule = new Hashtable<Integer, ArrayList<Agent>>();
		
		for(Integer vertexId : schedule.keySet()) {
			ArrayList<Agent> agents = schedule.get(vertexId);
			for(Agent agent : agents) {	
				agent.runStep();	
			}	
		}
			
		schedule = newSchedule;
	}

	/**
	 * Run given number of bulksteps or until all agents "halt".
	 * @param numberOfBulkSteps
	 * @return Remaining number of bulk steps -if greater than 0 than agents halted. 
	 */
	public long runProcessing(long numberOfBulkSteps) {
		long currentStep = 0;
		
		while ((currentStep < numberOfBulkSteps) && 
				( !((currentStep > 0) && (schedule.size() == 0)) )) {
			runBulkStep();
			currentStep ++;
		}
		
		return numberOfBulkSteps - currentStep;
	}

	
	//-- implements ProcessingFacade

	@Override
	public Vertex<?, ?> getLocalVertex() {
		return graph.getVertex(localVertexId);
	}

}
