package graphr.algorithms;

import java.util.ArrayList;

import graphr.processing.Agent;
import graphr.processing.ProcessingFacade;

public class ShortestPathAgent extends Agent {
	
	int sourceId;
	int hops;

	public ShortestPathAgent(ProcessingFacade facade, int sourceId) {
		super(facade);
		this.sourceId = sourceId;
	}
	
	public ShortestPathAgent getCopy() {
		return new ShortestPathAgent(facade, sourceId);
	}

	@Override
	public void runStep() {
		
	}

}
