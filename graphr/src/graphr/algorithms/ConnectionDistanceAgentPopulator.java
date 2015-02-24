package graphr.algorithms;

import java.util.ArrayList;

import graphr.processing.Agent;
import graphr.processing.AgentPopulator;

public class ConnectionDistanceAgentPopulator extends AgentPopulator {
	
	private boolean initialAgentGenerated;

	public ArrayList<Agent> getPopulation(int vertexId) {
		
		if (!initialAgentGenerated) {
			ConnectionDistanceAgent a = new ConnectionDistanceAgent(null, vertexId, 100);
			initialAgentGenerated = true;
			ArrayList<Agent> list = new ArrayList<Agent>();
			list.add(a);
			return list;
		} else {
			return null;
		}
	}

}
