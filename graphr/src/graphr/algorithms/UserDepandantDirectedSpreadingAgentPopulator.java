package graphr.algorithms;

import graphr.processing.Agent;

public class UserDepandantDirectedSpreadingAgentPopulator extends DirectedSpreadingAgentPopulator {


	public UserDepandantDirectedSpreadingAgentPopulator(long maxDistance, String userIDKey){
		super(maxDistance);
		if(userIDKey!=null){
			UserDepandantDirectedSpreadingAgent.setuserIdKey(userIDKey);
		}
	}
	public UserDepandantDirectedSpreadingAgentPopulator(long queriedVertexId, long maxDistance, String userIDKey) {
		super(queriedVertexId, maxDistance);
		if(userIDKey!=null){
			UserDepandantDirectedSpreadingAgent.setuserIdKey(userIDKey);
		}
	} 
	public UserDepandantDirectedSpreadingAgentPopulator(long queriedVertexId, long maxDistance, boolean settingVisibility, String userIDKey) {
		super(queriedVertexId, maxDistance, settingVisibility);
		if(userIDKey!=null){
			UserDepandantDirectedSpreadingAgent.setuserIdKey(userIDKey);
		}
	} 
	

	@Override
	protected Agent createAgent(long vertexId, long maxDistance) {
		// TODO Auto-generated method stub
		return new UserDepandantDirectedSpreadingAgent(vertexId, maxDistance, settingVisibility);
	}
}
