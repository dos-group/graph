package graphr.algorithms;

import java.util.HashSet;
import java.util.Set;

import graphr.data.GHT;

public class UserDepandantDirectedSpreadingAgent extends DirectedSpreadingAgent{

	static String userIdKey = "userID";

	String userID;

	static Set<String> allreadyHandled = new HashSet<>();
	
	public UserDepandantDirectedSpreadingAgent() {
	}

	public UserDepandantDirectedSpreadingAgent(long sourceId, long maxDistance, boolean setVisible) {
		super(sourceId, maxDistance, setVisible);
	}

	public static void setuserIdKey(String newKey){
		userIdKey = newKey;
	}

	@Override
	protected boolean shouldModifyAndBroadcast() {
		String signaturstring = userID + ";" + v.getId() + ";" + sourceId;
		if(allreadyHandled.contains(signaturstring)){
			return false;
		}else{
			allreadyHandled.add(signaturstring);
			return super.shouldModifyAndBroadcast();
		}		
	}
	@Override
	public DirectedSpreadingAgent getAgentForCopy() {
		return new UserDepandantDirectedSpreadingAgent();
	}	
	@Override
	public void addValuesToAgentForCopy(DirectedSpreadingAgent agentForCopy) {
		UserDepandantDirectedSpreadingAgent returnagent = (UserDepandantDirectedSpreadingAgent) agentForCopy;
		returnagent.userID = userID;
		super.addValuesToAgentForCopy(returnagent);
	}
	
	@Override
	protected boolean neuerVertexErlaubt() {

		GHT edgeData = (GHT) usedEdge.getData();
		//edge für einen Falschen benutzer gewählt
		if(userID!=null && !userID.equals(edgeData.getTable().get(userIdKey).toString())){
			return false;
		}
		return super.neuerVertexErlaubt();
	}

	@Override
	public void modifyVertex() {
		super.modifyVertex();
	}
	
	@Override
	public void modifyInSourceVertex() {
		super.modifyInSourceVertex();
	}

	@Override
	protected void modifyAfterFirstStep(){

		if(userID==null){
			GHT edgeData = (GHT) usedEdge.getData();
			userID = edgeData.getTable().get(userIdKey).toString();
		}
		super.modifyAfterFirstStep();
	}
	
}
