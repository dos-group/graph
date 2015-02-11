package graphr.simulation;

import graphr.graph.Vertex;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Manages simulations of agents.
 * <BR>
 * <b>NOT FINISHED</b> 
 * Should be Vertex driven, i.e. run only such vertices that have some agents waiting there to run. 
 * In other words, make it "vertex driven".
 */
public class AgentManager {

	Map<Vertex<?,?>, List<Agent>> schedule;
		
	public AgentManager() {
		schedule = new HashMap<Vertex<?,?>, List<Agent>>();
	}
	
	/**
	 * Perform bulk step, i.e. for every vertex that has some waiting agents -run them as they "arrived".
	 * @return True if at least one agent executed otherwise false.
	 */
	public boolean makeBulkStep() {
		Vertex<?,?> nextVertex;
		Map<Vertex<?,?>, List<Agent>> newSchedule = new HashMap<Vertex<?,?>, List<Agent>>();
		boolean executedOnce = false;
		
		for(Map.Entry<Vertex<?,?>, List<Agent>> entry : schedule.entrySet()) {
			
			for(Agent ag : entry.getValue()) {
								
				nextVertex = ag.step( entry.getKey() );
				
				// if agent is halted, do not schedule it anymore
				if(nextVertex != null) {
					List<Agent> agList = newSchedule.get(nextVertex);
					
					// if no agents to yet to be scheduled on given vertex, create a list
					if(agList == null) {
						agList = new ArrayList<Agent>();
						newSchedule.put(nextVertex, agList);
					}
					
					//insert agent into new vertex waiting queue
					agList.add(ag);
					
					// set flag that at least one agent run
					executedOnce = true;
				}
			}
			
			
		}
		
		schedule = newSchedule;
		return executedOnce;
	}

	/**
	 * Run given number of bulksteps or until all agents "halt".
	 * @param numberOfBulkSteps
	 * @return Remaining number of bulk steps -if greater than 0 than agents halted. 
	 */
	public long runSimulation(long numberOfBulkSteps) {
		long remainBulkStep;
		
		for(remainBulkStep = numberOfBulkSteps; remainBulkStep > 0 && makeBulkStep(); --remainBulkStep) {
			// intentionally left empty
		}
		
		return remainBulkStep;
	}
	
	
	public Map<Vertex<?, ?>, List<Agent>> getSchedule() {
		return schedule;
	}

	public void setSchedule(Map<Vertex<?, ?>, List<Agent>> schedule) {
		this.schedule = schedule;
	}

}
