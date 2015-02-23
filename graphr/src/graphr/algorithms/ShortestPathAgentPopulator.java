package graphr.algorithms;

import java.util.ArrayList;

import graphr.processing.Agent;
import graphr.processing.AgentPopulator;

public class ShortestPathAgentPopulator extends AgentPopulator {
	
	private boolean initialAgentGenerated;

	public ArrayList<Agent> getPopulation(int vertexId) {
		
		if (!initialAgentGenerated) {
			ShortestPathAgent a = new ShortestPathAgent(null, vertexId);
			initialAgentGenerated = true;
			ArrayList<Agent> list = new ArrayList<Agent>();
			list.add(a);
			return list;
		} else {
			return null;
		}
	}

}
