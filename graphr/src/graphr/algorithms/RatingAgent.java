package graphr.algorithms;

import graphr.data.GHT;
import graphr.graph.Edge.Direction;

public class RatingAgent extends UserDepandantDirectedSpreadingAgent{

	long excampleAbsoluteTimedistance;
	long timeOfSourcevertexVisiting;
	
	public RatingAgent() {
	}

	public RatingAgent(long sourceId, long maxDistance, boolean setVisible) {
		super(sourceId, maxDistance, setVisible);
	}
	@Override
	public DirectedSpreadingAgent getAgentForCopy() {
		return new RatingAgent();
	}	
	@Override
	public void addValuesToAgentForCopy(DirectedSpreadingAgent agentForCopy) {
		RatingAgent returnagent = (RatingAgent) agentForCopy;
		/**
		 * If there are any variables from this class, which should be copied, you can add them here
		 */
		returnagent.excampleAbsoluteTimedistance = this.excampleAbsoluteTimedistance;
		returnagent.timeOfSourcevertexVisiting = this.timeOfSourcevertexVisiting;
		
		
		super.addValuesToAgentForCopy(returnagent);
	}
	
	@Override
	protected boolean neuerVertexErlaubt() {
		/**
		 * There should be no need to change this.
		 * BUT: in case you want to stop the agent from spreading after a certain amount of time, you should implement it in here.
		 * Just do an if-statement and return "false" if you allready have reached the timelimit.
		 * The call for the super-method is needed still!
		 */
		/*
		if(excampleAbsoluteTimedistance>100){
			return false;
		}
		*/
		return super.neuerVertexErlaubt();
	}

	@Override
	public void modifyVertex() {
		/**
		 * This is the place for the algorithm. the vertex can be changed.
		 */
		/*
		GHT edgeData = (GHT) usedEdge.getData();
		if(direction.equals(Direction.OUTGOING)){
			excampleAbsoluteTimedistance += edgeData.getTable().get("startSemester").i();
		}else{
			excampleAbsoluteTimedistance += edgeData.getTable().get("endSemester").i();
		}
		*/
		super.modifyVertex();
	}
	
	@Override
	public void modifyInSourceVertex() {
		/**
		 * If there is a need to do something in the first vertex, do it here. 
		 * But there should not be a need for
		 */
		super.modifyInSourceVertex();
	}

	@Override
	protected void modifyAfterFirstStep(){
		/**
		 * It might be helpfull to do some special tasks in the first visited vertex after the source one. 
		 * This is the method is called there only.
		 * the "modifyVertex()" method is still called afterwards!
		 */
		/*
		GHT edgeData = (GHT) usedEdge.getData();
		timeOfSourcevertexVisiting = edgeData.getTable().get("startSemester").l();
		if(direction.equals(Direction.INCOMING)){
			timeOfSourcevertexVisiting = edgeData.getTable().get("endSemester").l();
		}
		*/
		
		super.modifyAfterFirstStep();
	}
}
