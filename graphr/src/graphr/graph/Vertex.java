package graphr.graph;

//import graphr.data.JsonArrayState;
//import graphr.data.JsonKeyValueState;
//import graphr.data.JsonReadableWritable;

import graphr.graph.Edge.Direction;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Hashtable;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Vertex<DV extends GraphData, DE extends GraphData> extends
		GraphElement<DV> {
	private static Logger log = LogManager.getLogger();

	Hashtable<Long, Edge<DV, DE>> edges;

	public Vertex(DV data) {
		super(data);
		edges = new Hashtable<Long, Edge<DV, DE>>();
	}

	public void addEdge(Edge<DV, DE> e) {
		edges.put(new Long(e.id), e);
	}

	/**
	 * Returns outgoing edges.
	 * 
	 * @return
	 */
	public Collection<Edge<DV, DE>> getEdges() {
		return getEdges(Direction.OUTGOING);
	}

	/**
	 * Returns incoming and/or outgoing edges.
	 * 
	 * @param direction
	 * @return
	 */
	public Collection<Edge<DV, DE>> getEdges(Direction direction) {
		Collection<Edge<DV, DE>> coll = edges.values();

		if (direction.equals(Direction.BOTH))
			return coll;

		List<Edge<DV, DE>> outgoingEdges = new ArrayList<Edge<DV, DE>>();

		for (Edge<DV, DE> e : coll) {
			if (e.getSource().equals(this)) {
				outgoingEdges.add(e);
			}
		}

		if (direction.equals(Direction.OUTGOING)) {
			return outgoingEdges;
		} else if (direction.equals(Direction.INCOMING)) {
			coll.removeAll(outgoingEdges);
			return coll;
		}

		log.error("Unknown Direction " + direction);
		return null;
	}

	public Edge<DV, DE> getEdge(long id) {
		return edges.get(id);
	}

	public String toString() {
		return "Vertex (id=" + id + ")";
	}

	/**
	 * Part of the visitor design pattern -accept method.
	 */
	@Override
	public void accept(GraphElementVisitor visitor) {
		visitor.visit(this);
	}

	// public String getAsJson() {
	//
	// JsonKeyValueState j = new JsonKeyValueState();
	//
	// j.add("type", "Vertex");
	// j.add("id", new Integer(id).toString());
	// j.add("data", data != null ? data.getAsJson() : "null");
	//
	// JsonArrayState edgesForJson = new JsonArrayState();
	//
	// for (Edge<DV,DE> e : edges.values()) {
	// edgesForJson.add(e.getAsJson());
	// }
	//
	// j.add("edges", edgesForJson.getAsJson());
	//
	// return j.getAsJson();
	// }

}
