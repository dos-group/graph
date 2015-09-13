package graphr.graph;

import graphr.data.GHT;
import graphr.graph.Edge.Direction;

import org.junit.Test;

public class GraphTest {
	@Test
	public void testGetEdges() {
		Graph<GHT, GHT> graph = getExampleGraph();

		for (Vertex<GHT, GHT> v : graph.getVertices()) {
			System.out.println(v);

			for (Edge<GHT, GHT> e : v.getEdges(Direction.BOTH)) {
				System.out.println(e);
			}
		}
	}

	public Graph<GHT, GHT> getExampleGraph() {
		Vertex<GHT, GHT> v0 = new Vertex<GHT, GHT>(new GHT());
		Vertex<GHT, GHT> v1 = new Vertex<GHT, GHT>(new GHT());
		Vertex<GHT, GHT> v2 = new Vertex<GHT, GHT>(new GHT());
		Vertex<GHT, GHT> v3 = new Vertex<GHT, GHT>(new GHT());

		Edge<GHT, GHT> e01 = new Edge<GHT, GHT>(new GHT());
		e01.setSource(v0);
		e01.setTarget(v1);

		Edge<GHT, GHT> e21 = new Edge<GHT, GHT>(new GHT());
		e21.setSource(v2);
		e21.setTarget(v1);

		Edge<GHT, GHT> e13 = new Edge<GHT, GHT>(new GHT());
		e13.setSource(v1);
		e13.setTarget(v3);

		v0.addEdge(e01);
		v1.addEdge(e01);
		v1.addEdge(e21);
		v2.addEdge(e21);
		v1.addEdge(e13);
		v3.addEdge(e13);

		Graph<GHT, GHT> g = new Graph<GHT, GHT>();
		g.addVertex(v0);
		g.addVertex(v1);
		g.addVertex(v2);
		g.addVertex(v3);

		return g;
	}
}
