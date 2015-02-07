package graphr.graph;

import graphr.data.GraphData;
import graphr.data.JsonReadableWritable;
import graphr.data.JsonKeyValueState;

public class Edge<DV extends GraphData, DE extends GraphData>
		extends GraphElement<DE> implements JsonReadableWritable {

	protected Vertex<DV, DE> target;

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

	public String getAsJson() {

		JsonKeyValueState j = new JsonKeyValueState();

		j.add("type", "Edge");
		j.add("id", new Integer(id).toString());
		j.add("data", (data== null ? "null" : data.getAsJson()));
		j.add("target", (target == null ? "null" : new Integer(target.id).toString()));

		return j.toJson();

	}

	public void setFromJson() {

	}

}
