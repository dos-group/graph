package graphr.algorithms;

import graphr.processing.Agent;

public class RatingAgentPopulator extends UserDepandantDirectedSpreadingAgentPopulator{
	public RatingAgentPopulator(long queriedVertexId, long maxDistance, boolean settingVisibility, String userIDKey) {
		super(queriedVertexId, maxDistance, settingVisibility, userIDKey);
	} 
	/**
	 * This is for a global start of Agents, which could easily run into heap-space problems.
	 * @param maxDistance
	 * @param userIDKey
	 */
	public RatingAgentPopulator(long maxDistance, String userIDKey) {
		super(maxDistance, userIDKey);
	}
	

	@Override
	protected Agent createAgent(long vertexId, long maxDistance) {
		// TODO Auto-generated method stub
		return new RatingAgent(vertexId, maxDistance, settingVisibility);
	}
}
