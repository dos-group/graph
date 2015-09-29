package graphr.algorithms.ranking;

import graphr.data.GHT;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author Florian
 */
public class HubsAndAuthoritiesTest {

    @Test
    public void testHubsAndAuthoritiesAlgorithm() {
        Graph<GHT, GHT> graph = createSampleGraph();
        int maxNumberOfIterations = 20;

        Map<Vertex<GHT, GHT>, Double> rankingValues = HubsAndAuthorities.calculateHubsAndAuthorities(graph, maxNumberOfIterations);
        for (Vertex<GHT, GHT> keyV : rankingValues.keySet()) {
            System.out.println(keyV.getData().getTable().get("value") + " " + rankingValues.get(keyV));
        }
    }

    private Graph<GHT, GHT> createSampleGraph() {
        Graph<GHT, GHT> graph = new Graph<GHT, GHT>();
        GHT pageAght = new GHT();
        pageAght.put("value", "Page A");
        Vertex<GHT, GHT> pageA = new Vertex<GHT, GHT>(pageAght);
        GHT pageBght = new GHT();
        pageBght.put("value", "Page B");
        Vertex<GHT, GHT> pageB = new Vertex<GHT, GHT>(pageBght);
        GHT pageCght = new GHT();
        pageCght.put("value", "Page C");
        Vertex<GHT, GHT> pageC = new Vertex<GHT, GHT>(pageCght);
        GHT pageDght = new GHT();
        pageDght.put("value", "Page D");
        Vertex<GHT, GHT> pageD = new Vertex<GHT, GHT>(pageDght);

        Edge<GHT, GHT> edgeAB = new Edge<GHT, GHT>(new GHT());
        edgeAB.setSource(pageA);
        edgeAB.setTarget(pageB);
        pageA.addEdge(edgeAB);
        pageB.addEdge(edgeAB);
        Edge<GHT, GHT> edgeAC = new Edge<GHT, GHT>(new GHT());
        edgeAC.setSource(pageA);
        edgeAC.setTarget(pageC);
        pageA.addEdge(edgeAC);
        pageC.addEdge(edgeAC);
        Edge<GHT, GHT> edgeCA = new Edge<GHT, GHT>(new GHT());
        edgeCA.setSource(pageC);
        edgeCA.setTarget(pageA);
        pageC.addEdge(edgeCA);
        pageA.addEdge(edgeCA);
        Edge<GHT, GHT> edgeDC = new Edge<GHT, GHT>(new GHT());
        edgeDC.setSource(pageD);
        edgeDC.setTarget(pageC);
        pageD.addEdge(edgeDC);
        pageC.addEdge(edgeDC);
        Edge<GHT, GHT> edgeBC = new Edge<GHT, GHT>(new GHT());
        edgeBC.setSource(pageB);
        edgeBC.setTarget(pageC);
        pageB.addEdge(edgeBC);
        pageC.addEdge(edgeBC);

        graph.addVertex(pageA);
        graph.addVertex(pageB);
        graph.addVertex(pageC);
        graph.addVertex(pageD);

        return graph;
    }
}
