package graphr.semanticgraph.graph;

import graphr.graph.Graph;
import graphr.graph.GraphData;

/**
 * This class is the representation of a bipartite graph which has a source
 * graph and a destination graph and directed edges between them. Both graphs
 * are SemanticSliceGraphs.
 *
 * @author Florian
 * @param <DV>
 * @param <DE>
 */
public class SemanticSliceConnectionGraph<DV extends GraphData, DE extends GraphData>
        extends Graph<DV, DE> {

    private final SemanticSliceGraph srcGraph;
    private final SemanticSliceGraph destGraph;

    /**
     * Creates a new semantic slice connection graph, which is a bipartite graph
     * with two given semantic slice graphs.
     *
     * @param srcGraph first graph of the bipartite connection graph
     * @param destGraph second graph of the bipartite connection graph
     */
    public SemanticSliceConnectionGraph(SemanticSliceGraph srcGraph, SemanticSliceGraph destGraph) {
        this.srcGraph = srcGraph;
        this.destGraph = destGraph;
    }

    /**
     * Adds a new SemanticEdge inside the bipartite graph.
     *
     * @param src source vertex of the edge
     * @param dst destination vertex of the edge
     * @param label name of the edge
     * @return the newly created semantic edge
     */
    public SemanticEdge addEdge(SemanticVertex src, SemanticVertex dst, String label) {
        return new SemanticEdge(src, dst, label);
    }

    /**
     *
     * @return first graph of the bipartite connection graph
     */
    public SemanticSliceGraph getSourceGraph() {
        return srcGraph;
    }

    /**
     *
     * @return second graph of the bipartite connection graph
     */
    public SemanticSliceGraph getDestinationGraph() {
        return destGraph;
    }
}
