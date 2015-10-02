package graphr.algorithms.ranking;

import graphr.data.GHT;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;
import graphr.io.FileSystemHandler;
import graphr.io.JsonVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import org.junit.Test;

/**
 *
 * @author Florian
 */
public class PageRankTest {

//    @Test
    public void testPageRankAlgorithm() {
        String path = System.getProperty("user.home") + "/Desktop/" + "1443607358734wholeGraph_normalgraphWordnet.json";
        Graph<GHT, GHT> graph = loadGraph(path);
//        Graph<GHT, GHT> graph = createSampleGraph();
        double dumpingFactor = 0.85;
        int maxNumberOfIterations = 40;
        Map<Vertex<GHT, GHT>, Double> pageRankValues = PageRank.calculatePageRank(graph, dumpingFactor, maxNumberOfIterations);
//        for (Vertex<GHT, GHT> keyV : pageRankValues.keySet()) {
//            System.out.println(keyV.getData().getTable().get("value") + " " + pageRankValues.get(keyV));
//        }
        List<Map.Entry<Vertex<GHT, GHT>, Double>> sortedMap = sortMap(pageRankValues);
        for(Map.Entry<Vertex<GHT, GHT>, Double> entry : sortedMap){
            System.out.println(entry.getValue() + " " + entry.getKey().getData().getTable().get("value"));
        }
    }

    private Graph<GHT, GHT> loadGraph(String path) {
        String graphAsJsonString = FileSystemHandler.getInstance().read(path);
        JsonVisitor<GHT, GHT> jsonVisitor = new JsonVisitor<GHT, GHT>();
        Graph<GHT, GHT> graph = jsonVisitor.parseJsonString(graphAsJsonString);
        return graph;
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

    private List<Map.Entry<Vertex<GHT, GHT>, Double>> sortMap(
            Map<Vertex<GHT, GHT>, Double> ranking) {
        List<Map.Entry<Vertex<GHT, GHT>, Double>> sortetList = new ArrayList<Map.Entry<Vertex<GHT, GHT>, Double>>(
                ranking.entrySet());

        Collections.sort(sortetList,
                new Comparator<Map.Entry<Vertex<GHT, GHT>, Double>>() {

                    @Override
                    public int compare(
                            Map.Entry<Vertex<GHT, GHT>, Double> entry1,
                            Map.Entry<Vertex<GHT, GHT>, Double> entry2) {
                                if (entry1.getValue() >= entry2.getValue()) {
                                    return 1;
                                } else if(entry1.getValue() < entry2.getValue()){
                                    return -1;
                                } else {
                                    return 0;
                                }
                            }
                });
        Collections.reverse(sortetList);

        return sortetList;
    }
}
