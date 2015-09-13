package graphr.graph;

import graphr.data.GHT;

//import graphr.data.JsonKeyValueState;

public class Edge<DV extends GraphData, DE extends GraphData> extends
		GraphElement<DE> {
	public enum Direction {
		BOTH, INCOMING, OUTGOING
	}

	protected Vertex<DV, DE> target;
	private Vertex<GHT, GHT> source;

	public Edge(DE data) {
		super(data);
	}

	public Edge(Vertex<DV, DE> target, DE data) {
		super(data);
		this.target = target;
	}

	public Vertex<DV, DE> getTarget() {
		return target;
	}

	public Long getTargetId() {
		return target.getId();
	}

	public void setTarget(Vertex<DV, DE> target) {
		this.target = target;
	}

	public String toString() {
		return "Edge (id=" + getId() + ", data="
				+ (data != null ? data.toString() : "null") + ", src="
				+ (source != null ? source.getId() : "null") + ", target="
				+ (target != null ? target.getId() : "null") + ")";
	}

	/**
	 * Part of the visitor design pattern -accept method. <br>
	 * This method is actually never called. It is responsibility of the vertex'
	 * visitor method to handle edges as well.
	 */
	@Override
	public void accept(GraphElementVisitor visitor) {
		visitor.visit(this);
	}

	public void setSource(Vertex<GHT, GHT> source) {
		this.source = source;
	}

	public Vertex<GHT, GHT> getSource() {
		return source;
	}

	// public String getAsJson() {
	//
	// JsonKeyValueState j = new JsonKeyValueState();
	//
	// j.add("type", "Edge");
	// j.add("id", new Integer(id).toString());
	// j.add("data", (data== null ? "null" : data.getAsJson()));
	// j.add("target", (target == null ? "null" : new
	// Integer(target.id).toString()));
	//
	// return j.getAsJson();
	//
	// }

}
