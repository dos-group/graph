package graphr.algorithms;

import graphr.data.GHT;
import graphr.data.PrimData;
import graphr.graph.Edge;
import graphr.graph.Edge.Direction;
import graphr.processing.Agent;

public class DirectedSpreadingAgent extends Agent{

	Edge usedEdge;
	long sourceId;
	long lastVertexId;
	long distance;
	long maxDistance;
	boolean setVisible;
	Direction direction = Direction.BOTH;
	

	

	public DirectedSpreadingAgent(long sourceId, long maxDistance, boolean setVisible) {
		this.sourceId = sourceId;
		this.maxDistance = maxDistance;
		this.setVisible = setVisible;
	}
	public DirectedSpreadingAgent() {
	}
	
	public DirectedSpreadingAgent getAgentForCopy() {
		return new DirectedSpreadingAgent();
	}
	
	public void addValuesToAgentForCopy(DirectedSpreadingAgent agentForCopy){
		agentForCopy.sourceId = sourceId;
		agentForCopy.maxDistance = maxDistance;
		agentForCopy.usedEdge = usedEdge;
		agentForCopy.lastVertexId = lastVertexId;
		agentForCopy.distance = distance;
		agentForCopy.direction = direction;
		agentForCopy.setVisible = setVisible;
	}
	
	@Override
	public Agent getCopy() {
		DirectedSpreadingAgent returnAgent = getAgentForCopy();
		addValuesToAgentForCopy(returnAgent);
		return returnAgent;
	}

	@Override
	public void setUsedEdge(Edge e) {
		usedEdge = e;
		// TODO Validate
	}

	@Override
	public void runStep() {
		if(distance<=maxDistance){//an sonsten macht man keine Aktion mehr!
			distance++;
			if(usedEdge==null){//direkt nach dem Initialisieren
				modifyInSourceVertex();
				lastVertexId = v.getId();
				v.broadcast();	
				return;
			}else{//nicht die Initialisierung
				if (!neuerVertexErlaubt()){
					return;
				}
				
				//Hier beginnt die wirkliche bearbeitung
				modifyEdge();
				
				if(this.direction== Direction.BOTH){
					if (usedEdge.getTargetId().equals(sourceId)){
						this.direction = Direction.INCOMING;
					}else{
						this.direction = Direction.OUTGOING;					
					}
					modifyAfterFirstStep();
				}
								
				//Hier bitte black magic einfügen!
				if(shouldModifyAndBroadcast()){
					modifyVertex();
					lastVertexId = v.getId();
					v.broadcast();
				}
			}
		}
	}
	
	protected boolean shouldModifyAndBroadcast() {
		return true;
	}
	
	/**
	 * Hier werden Dinge gesetzt, die nach dem ersten Schritt wichtig sind.
	 *  Bei verschiedenen Usern würde hier der User ausgewählt werden, die Sourcetime gesetzt usw
	 */
	protected void modifyAfterFirstStep(){

	}

	public void modifyEdge(){
		if(setVisible){
			GHT edgeData = (GHT) usedEdge.getData();
			edgeData.put("visible", true);
		}		
	}
	public void modifyVertex(){
		if(setVisible){
			v.setValue("visible", new PrimData(true));
		}		
	}
	public void modifyInSourceVertex(){
		if(setVisible){
			v.setValue("visible", new PrimData(true));
		}		
	}
	
	/**
	 * Nicht erlaubt ist: 
	 * 1. Benutzung eines falschen Users
	 * 2. bei Incomming Richtung nicht den letzten besuchten Vertex als Ziel zu haben
	 * 3. bei outgoing Richtung nicht den letzten besuchten Vertex als Quelle zu haben
	 * @return
	 */
	protected boolean neuerVertexErlaubt(){
		//edge ist in die falsche Richtung unterwegs
		if(direction.equals(Direction.INCOMING) && lastVertexId != usedEdge.getTargetId()){
			return false;
		}
		//edge ist in die falsche Richtung unterwegs
		if(direction.equals(Direction.OUTGOING) && lastVertexId != usedEdge.getSource().getId()){
			return false;
		}
		return true;
	}
	

}
