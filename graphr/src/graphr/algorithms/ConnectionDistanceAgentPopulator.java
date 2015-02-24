package graphr.algorithms;

import java.util.ArrayList;

import graphr.processing.Agent;
import graphr.processing.AgentPopulator;

public class ConnectionDistanceAgentPopulator extends AgentPopulator {
	
	private int queriedVertexId;
	private int maxDistance;
	
	public ConnectionDistanceAgentPopulator(int queriedVertexId, int maxDistance) {
		this.queriedVertexId = queriedVertexId;
		this.maxDistance = maxDistance;
	}

	public ArrayList<Agent> getPopulation(int vertexId) {
		
		if (vertexId == queriedVertexId) {
			ConnectionDistanceAgent a = new ConnectionDistanceAgent(null, vertexId, maxDistance);
			ArrayList<Agent> list = new ArrayList<Agent>();
			list.add(a);
			return list;
		} else {
			return null;
		}
	}

}
