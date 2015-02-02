package graphr.graph;

public abstract class GraphElement<D> {

	private static int nextIdToIssue = 0;
	
	protected int id;
	protected D data;
	
	public GraphElement() {
		id = GraphElement.nextIdToIssue;
		GraphElement.nextIdToIssue ++;
	}
	
	public long getId() {
		return id;
	}
	
	public D getData() {
		return data;
	}
	
	public void setData(D data) {
		this.data = data;
	}
	
}
