package graphr.algorithms;

import java.util.ArrayList;

import graphr.processing.Agent;
import graphr.processing.AgentPopulator;
import graphr.processing.VertexProcessingFacade;

public class ConnectionDistanceAgentPopulator extends AgentPopulator {
	
	private long queriedVertexId;
	private long maxDistance;
	
	public ConnectionDistanceAgentPopulator(long queriedVertexId, long maxDistance) {
		this.queriedVertexId = queriedVertexId;
		this.maxDistance = maxDistance;
	}

	public ArrayList<Agent> getPopulation(long vertexId) {
		
		if (vertexId == queriedVertexId) {
			ConnectionDistanceAgent a = new ConnectionDistanceAgent(vertexId, maxDistance);
			ArrayList<Agent> list = new ArrayList<Agent>();
			list.add(a);
			return list;
		} else {
			return null;
		}
	}

}
