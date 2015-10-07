package graphr.algorithms.ranking;

import graphr.data.GHT;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;
import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author Florian
 */
public class PageRank {

    public static Map<Vertex<GHT, GHT>, Double> calculatePageRank(Graph<GHT, GHT> graph, double dumpingFactor, int maxNumberOfIterations) {
        //init
        Map<Vertex<GHT, GHT>, Double> pageRank = new HashMap<Vertex<GHT, GHT>, Double>();
        for (Vertex<GHT, GHT> v : graph.getVertices()) {
            pageRank.put(v, 1.0);
        }

        Map<Vertex<GHT, GHT>, Double> tmpPageRank = new HashMap<Vertex<GHT, GHT>, Double>();

        //iteration
        for (int i = 0; i < maxNumberOfIterations; i++) {
            for (Vertex<GHT, GHT> p : graph.getVertices()) {
                //calculate new pagerank value for vertex p
                double neighbourSum = 0.0;
                for (Edge<GHT, GHT> incommingEdge : p.getEdges(Edge.Direction.INCOMING)) {
                    if(!pageRank.containsKey(incommingEdge.getSource())){
                        pageRank.put(incommingEdge.getSource(), 1.0);
                    }
                    double numberOfOutgoingEdges = (double) incommingEdge.getSource().getEdges(Edge.Direction.OUTGOING).size();
                    double pageRankOfNeighbour = pageRank.get(incommingEdge.getSource());
                    neighbourSum += (pageRankOfNeighbour / numberOfOutgoingEdges);
                }
                double pr = (1 - dumpingFactor) + (dumpingFactor * neighbourSum);
                tmpPageRank.put(p, pr);
            }

            //replace tmpPageRank values with pageRank values
            pageRank.putAll(tmpPageRank);
        }

        return pageRank;
    }

}
