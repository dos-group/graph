package graphr.semanticgraph;

import graphr.semanticgraph.graph.SemanticGraph;
import graphr.semanticgraph.graph.SemanticSliceGraph;
import graphr.data.GHT;

/**
 * This interfaces is used to create a semanticslice graphs and
 * connection-graphs between semantic slices.
 *
 * @author Florian
 */
public interface SemanticSliceCreator {

    /**
     * This method creates from given initial data a Semantic Slice.
     *
     * @param graph Already created Semantic Graph
     * @param dataSource Any kind of Data as input source to create the semantic
     * slice
     * @return Semantic Graph with new created Semantic Slice Graph
     */
    public SemanticGraph createSemanticSliceGraph(SemanticGraph graph, String dataSource);

    /**
     * This method connects two already created Semantic Slices. As a result the
     * Semantic Graph will be extended by a SemanticSliceConnectionGraph. When
     * the destination graph is set to null, a new desination graph instance is
     * generated.
     *
     * @param graph Already created SemanticGraph
     * @param sourceGraph Already created Semantic Slice, which will only have
     * outgoing edges
     * @param destinationGraph Already created Semantic Slice, which will only
     * have incomming edges. Can be null.
     * @return Semantic Graph extended by a SemanticSliceConnectionGraph
     */
    public SemanticGraph createSemanticSliceConnectionGraph(SemanticGraph graph, SemanticSliceGraph<GHT, GHT> sourceGraph, SemanticSliceGraph<GHT, GHT> destinationGraph);

}
