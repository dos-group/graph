package graphr.algorithms;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import graphr.data.PrimData;
import graphr.processing.Agent;
import graphr.processing.VertexProcessingFacade;

public class ConnectionDistanceAgent extends Agent {
	
	private static Logger log = LogManager.getLogger(); 
	
	int sourceId;
	int distance;
	int maxDistance;

	//-- Implementations of abstract stuff
	
	public ConnectionDistanceAgent(int sourceId, int maxDistance) {
		this.sourceId = sourceId;
		this.maxDistance = maxDistance;
	}
	
	public ConnectionDistanceAgent getCopy() {
		ConnectionDistanceAgent a = new ConnectionDistanceAgent(sourceId, maxDistance);
		a.distance = distance;
		a.v = v;
		return a;
	}

	@Override
	public void runStep() {
		log.debug("vid: " + v.getId());
		
		if ((sourceId == v.getId()) && (v.getValue("distance") == null)){
			modifyDistanceAndBroadcast(0);
		} else {
			PrimData vDistance = v.getValue("distance");
			log.debug("distance: " + (vDistance == null ? "null" : vDistance.i()));
			if ((vDistance == null) || (vDistance.i() > this.distance)) {
				modifyDistanceAndBroadcast(this.distance);
			} 
		}
	}
	
	//-- Elements of algorithm
	
	public void modifyDistanceAndBroadcast(int distance) {
		v.setValue("distance", new PrimData(distance));
		v.setValue("vislabel", new PrimData(getVisLabel()));
		this.distance = distance + 1;
		v.broadcast();
	}
	
	public String getVisLabel() {
		return v.getValue("label").s() + ": " + v.getValue("distance").i(); 
	}

}
