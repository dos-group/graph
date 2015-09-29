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
public class HubsAndAuthorities {

    public static Map<Vertex<GHT, GHT>, Double> calculateHubsAndAuthorities(Graph<GHT, GHT> graph, int maxNumberOfIterations) {
        //init
        Map<Vertex<GHT, GHT>, Double> hub = new HashMap<Vertex<GHT, GHT>, Double>();
        Map<Vertex<GHT, GHT>, Double> auth = new HashMap<Vertex<GHT, GHT>, Double>();
        for (Vertex<GHT, GHT> v : graph.getVertices()) {
            hub.put(v, 1.0);
            auth.put(v, 1.0);
        }

        //iterations
        for (int i = 0; i < maxNumberOfIterations; i++) {
            //Authrity update rule
            for (Vertex<GHT, GHT> p : graph.getVertices()) {
                double newAuthValue = 0.0;
                for (Edge<GHT, GHT> incommingEdge : p.getEdges(Edge.Direction.INCOMING)) {
                    newAuthValue += hub.get(incommingEdge.getSource());
                }
                auth.put(p, newAuthValue);
            }
            //Hub update rule
            for (Vertex<GHT, GHT> p : graph.getVertices()) {
                double newHubValue = 0.0;
                for (Edge<GHT, GHT> outgoingEdge : p.getEdges(Edge.Direction.OUTGOING)) {
                    newHubValue += auth.get(outgoingEdge.getTarget());
                }
                hub.put(p, newHubValue);
            }
        }

        //normalize
        double sumAllAuthValues = 0.0;
        double sumAllHubValues = 0.0;
        for (Vertex<GHT, GHT> v : graph.getVertices()) {
            sumAllAuthValues += auth.get(v);
            sumAllHubValues += hub.get(v);
        }
        for (Vertex<GHT, GHT> v : graph.getVertices()) {
            if (sumAllAuthValues == 0) {
                auth.put(v, 0.0);
            } else {
                auth.put(v, auth.get(v) / sumAllAuthValues);
            }
            if (sumAllHubValues == 0) {
                hub.put(v, 0.9);
            } else {
                hub.put(v, hub.get(v) / sumAllHubValues);
            }
        }

        return auth;
    }
}
