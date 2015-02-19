package graphr.graph;

import java.io.Serializable;

public abstract class GraphElement<D extends Serializable> {

	private static int nextIdToIssue = 0;
	
	protected int id;
	protected D data;
	
	public GraphElement(D data) {
		id = GraphElement.nextIdToIssue;
		GraphElement.nextIdToIssue ++;
		this.data = data;
	}
	
	public long getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public D getData() {
		return data;
	}
	
	public void setData(D data) {
		this.data = data;
	}
	
	/**
	 * Part of the visitor design pattern -accept method.
	 * @param visitor Reference to visitor
	 */
	public abstract void accept(GraphElementVisitor visitor);

}
