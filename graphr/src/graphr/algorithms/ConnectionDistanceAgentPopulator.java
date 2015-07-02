package graphr.algorithms;

import graphr.processing.Agent;
import graphr.processing.AgentPopulator;

import java.util.ArrayList;

public class ConnectionDistanceAgentPopulator extends AgentPopulator {
	
	private long queriedVertexId;
	private long maxDistance;
	
	public ConnectionDistanceAgentPopulator(long queriedVertexId, long maxDistance) {
		this.queriedVertexId = queriedVertexId;
		this.maxDistance = maxDistance;
	}

	public ArrayList<Agent> getPopulation(long vertexId) {
		if (vertexId == queriedVertexId) {
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
	protected Agent createAgent(final long vertexId, final long distance) {
		return new ConnectionDistanceAgent(vertexId, distance);
	}

}
