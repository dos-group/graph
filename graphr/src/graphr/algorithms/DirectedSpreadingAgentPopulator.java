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

	public DirectedSpreadingAgentPopulator(long maxDistance){
		this.maxDistance = maxDistance;
		this.startEverywhere = true;
	}
	public DirectedSpreadingAgentPopulator(long queriedVertexId, long maxDistance) {
		this.queriedVertexId = queriedVertexId;
		this.maxDistance = maxDistance;
	}

	public DirectedSpreadingAgentPopulator(long queriedVertexId, long maxDistance, boolean settingVisibility) {
		this.settingVisibility = settingVisibility;
		this.queriedVertexId = queriedVertexId;
		this.maxDistance = maxDistance;
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
