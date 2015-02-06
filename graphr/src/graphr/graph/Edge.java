package graphr.graph;

import graphr.data.JsonReadableWritable;

public class Edge<DV extends JsonReadableWritable,DE extends JsonReadableWritable> extends GraphElement<DE> {
	
	protected Vertex<DV,DE> target;
		
	public Vertex<DV, DE> getTarget() {
		return target;
	}

	public void setTarget(Vertex<DV, DE> target) {
		this.target = target;
	}

	public String toString() {
		return "(" + getId() + "," + data.toString() + "," 
				+ (target != null ? target.getId() : "null") + ")";
	}

}
