package graphr.algorithms;

import java.util.ArrayList;

import graphr.data.PrimData;
import graphr.processing.Agent;
import graphr.processing.VertexProcessingFacade;

public class ConnectionDistanceAgent extends Agent {
	
	int sourceId;
	int distance;
	int maxDistance;

	//-- Implementations of abstract stuff
	
	public ConnectionDistanceAgent(VertexProcessingFacade facade, int sourceId, int maxDistance) {
		super(facade);
		this.sourceId = sourceId;
		this.maxDistance = maxDistance;
	}
	
	public ConnectionDistanceAgent getCopy() {
		return new ConnectionDistanceAgent(v, sourceId, maxDistance);
	}

	@Override
	public void runStep() {
		if ((sourceId == v.getId()) && (v.getValue("distance") == null)){
			modifyDistanceAndBroadcast(0);
		} else {
			PrimData distance = v.getValue("distance");
			if ((distance != null) && (distance.i() < this.distance)) {
				modifyDistanceAndBroadcast(this.distance);
			}
		}
	}
	
	//-- Elements of algorithm
	
	public void modifyDistanceAndBroadcast(int distance) {
		v.setValue("distance", new PrimData(distance));
		this.distance ++;
		v.broadcast();
	}

}
