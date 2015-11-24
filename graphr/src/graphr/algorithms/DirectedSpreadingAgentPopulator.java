package graphr.algorithms;

import java.util.ArrayList;

import graphr.graph.Edge.Direction;
import graphr.processing.Agent;
import graphr.processing.AgentPopulator;

public class DirectedSpreadingAgentPopulator extends AgentPopulator {

	protected long queriedVertexId;
	protected long maxDistance;
	protected boolean startEverywhere = false;
	boolean settingVisibility = false;

	public DirectedSpreadingAgentPopulator(Long maxDistance){
		if(maxDistance!=null){
			this.maxDistance = maxDistance;
		}
		this.startEverywhere = true;
	}
	public DirectedSpreadingAgentPopulator(Long queriedVertexId, Long maxDistance) {
		if(queriedVertexId!=null){
			this.queriedVertexId = queriedVertexId;
		}
		if(maxDistance!=null){
			this.maxDistance = maxDistance;
		}
	}

	public DirectedSpreadingAgentPopulator(Long queriedVertexId, Long maxDistance, Boolean settingVisibility) {
		if(queriedVertexId!=null){
			this.queriedVertexId = queriedVertexId;
		}
		if(maxDistance!=null){
			this.maxDistance = maxDistance;
		}
		if(settingVisibility!=null){
			this.settingVisibility = settingVisibility;
		}
	}

	public ArrayList<Agent> getPopulation(long vertexId) {
		
		if (startEverywhere || vertexId == queriedVertexId ) {
			Agent a = createAgent(vertexId, maxDistance);
			ArrayList<Agent> list = new ArrayList<Agent>();
			list.add(a);
			return list;
		} else {
			return null;
		}
	}
	

	/**
	 * @param vertexId
	 *            The searched vertex id
	 * @param distance
	 *            The maximum distance
	 * @return a agent used to get the population
	 */
	protected Agent createAgent(final long vertexId, final long maxDistance) {
		return new DirectedSpreadingAgent(vertexId, maxDistance, settingVisibility);
	}

}
