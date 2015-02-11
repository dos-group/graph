package graphr.simulation;

import graphr.graph.Vertex;

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
	 * Perform bulk step, i.e. let every agent to run on a given vertex
	 */
	public void makeBulkStep() {
		Vertex<?,?> nextVertex;
		Map<Vertex<?,?>, List<Agent>> newSchedule = new HashMap<Vertex<?,?>, List<Agent>>();
		
		for(Map.Entry<Vertex<?,?>, List<Agent>> entry : schedule.entrySet()) {
			
			for(Agent ag : entry.getValue()) {
				nextVertex = ag.step( entry.getKey() );
				if(nextVertex == null)
					nextVertex = entry.getKey();
				
				//TODO: insert agent into new vertex waiting queue
			}
			
			
		}
		
		schedule = newSchedule;
	}
	
}
