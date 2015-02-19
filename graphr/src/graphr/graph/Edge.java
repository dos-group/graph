package graphr.graph;

//import graphr.data.JsonKeyValueState;


public class Edge<DV extends GraphData, DE extends GraphData> extends GraphElement<DE> {

	public Edge(DE data) {
		super(data);
	}


	protected Vertex<DV, DE> target;

	public Vertex<DV, DE> getTarget() {
		return target;
	}

	public void setTarget(Vertex<DV, DE> target) {
		this.target = target;
	}

	public String toString() {
		return "(" + getId() + "," + (data != null ? data.toString() : "null") + ","
				+ (target != null ? target.getId() : "null") + ")";
	}
	

	/**
	 * Part of the visitor design pattern -accept method.
	 * <br>
	 * This method is actually never called. It is responsibility of the vertex' visitor method to handle edges as well.
	 */
	@Override
	public void accept(GraphElementVisitor visitor) {
		visitor.visit(this);
	}

//	public String getAsJson() {
//
//		JsonKeyValueState j = new JsonKeyValueState();
//
//		j.add("type", "Edge");
//		j.add("id", new Integer(id).toString());
//		j.add("data", (data== null ? "null" : data.getAsJson()));
//		j.add("target", (target == null ? "null" : new Integer(target.id).toString()));
//
//		return j.getAsJson();
//
//	}

}
