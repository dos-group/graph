package graphr.semanticgraph.graph;

import graphr.data.GHT;
import graphr.graph.Graph;
import graphr.graph.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a semantic graph. It should be created with the
 * SemanticGraphGenerator. The semantic graph creates a groundstructure of a
 * graph with semantic annotations, which can be used for analysis of texts.
 *
 * @author Florian
 */
public class SemanticGraph {

    private final SemanticGraphConfig config;
    private List<SemanticSliceGraph<GHT, GHT>> semanticSlices = new ArrayList<>();
    private List<SemanticSliceConnectionGraph> semanticSliceConnections = new ArrayList<>();
    private Graph<GHT, GHT> wholeGraph = null;

    /**
     *
     * @param config configuration object for creating this graph
     */
    public SemanticGraph(SemanticGraphConfig config) {
        this.config = config;
    }

    /**
     * Checks if a slice with the given type (name) is already created in the
     * graph.
     *
     * @param graphType type (name) of the slice graph
     * @return yes, if slice is already created inside this semantic graph
     */
    public boolean isSliceAlreadyCreated(SemanticSliceGraphType graphType) {
        if (numberOfSlices() == 0) {
            return false;
        }
        if (getSemanticSlice(graphType) == null) {
            return false;
        }
        return true;
    }

    private int numberOfSlices() {
        return semanticSlices.size();
    }

    /**
     * Gets the semantic slice with a given type (name).
     *
     * @param graphType type (name) of the slice
     * @return semantic slice graph (null if not found)
     */
    public SemanticSliceGraph<GHT, GHT> getSemanticSlice(SemanticSliceGraphType graphType) {
        for (SemanticSliceGraph semanticSlice : semanticSlices) {
            if (graphType == semanticSlice.getSliceType()) {
                return semanticSlice;
            }
        }
        return null;
    }

    /**
     * This method removes a semantic slice from this graph.
     *
     * @param graphType type (name) of the semantic slice to be removed
     */
    public void removeSemanticSlice(SemanticSliceGraphType graphType) {
        for (SemanticSliceGraph semanticSlice : semanticSlices) {
            if (graphType == semanticSlice.getSliceType()) {
                semanticSlices.remove(semanticSlice);
                return;
            }
        }
    }

    /**
     *
     * @return configuration object of this semantic graph
     */
    public SemanticGraphConfig getConfig() {
        return config;
    }

    /**
     * Puts all slice graphs together in a single graph. As default, this
     * datastructure is not generated.
     *
     * @return a graph containing all semantic slice graphs
     */
    public Graph<GHT, GHT> loadWholeGraph() {
        this.wholeGraph = new Graph<GHT, GHT>();
        for (SemanticSliceGraph<GHT, GHT> slice : semanticSlices) {
            for (Vertex v : slice.getVertices()) {
                wholeGraph.addVertex(v);
            }
        }
        return wholeGraph;
    }

    /**
     * Destroyes the graph containing all semantic slice graphs. (Default is
     * null).
     */
    public void unloadWholeGraph() {
        this.wholeGraph = null;
    }

    /**
     *
     * @param semanticSliceGraph semantic slice graph to be added to the
     * semantic graph
     */
    public void addSemanticSlice(SemanticSliceGraph semanticSliceGraph) {
        this.semanticSlices.add(semanticSliceGraph);
    }

    /**
     *
     * @param semanticSliceConnectionGraph
     */
    public void addSemanticSliceConnectionGraph(SemanticSliceConnectionGraph semanticSliceConnectionGraph) {
        semanticSliceConnections.add(semanticSliceConnectionGraph);
    }

    /**
     *
     * @return all semantic slices
     */
    public List<SemanticSliceGraph<GHT, GHT>> getSemanticSlices() {
        return semanticSlices;
    }

    /**
     *
     * @param semanticSlices all semantic slice graphs to be inserted into this
     * semantic graph
     */
    public void setSemanticSlices(List<SemanticSliceGraph<GHT, GHT>> semanticSlices) {
        this.semanticSlices = semanticSlices;
    }

    /**
     *
     * @return all semantic slice connections
     */
    public List<SemanticSliceConnectionGraph> getSemanticSliceConnections() {
        return semanticSliceConnections;
    }

    /**
     * Removes a single semantic slice connection from this graph (but not the
     * slices itselfes)
     *
     * @param connectionSlice connection slice to be removed
     */
    public void removeConnectionSlice(SemanticSliceConnectionGraph connectionSlice) {
        this.semanticSliceConnections.remove(connectionSlice);
    }

    /**
     * Gets a semantic slice connection graph by searching for the right source
     * slice and destination slice.
     *
     * @param sourceSliceType type (name) of the source slice
     * @param destinationSliceType type (name) of the destination slice
     * @return semantic slice connection graph with source graph and destination
     * graph with the given types (names)
     */
    public SemanticSliceConnectionGraph getSemanticSliceConnectionGraph(SemanticSliceGraphType sourceSliceType, SemanticSliceGraphType destinationSliceType) {
        for (SemanticSliceConnectionGraph connectionGraph : semanticSliceConnections) {
            if (connectionGraph.getSourceGraph().getSliceType() == sourceSliceType && connectionGraph.getDestinationGraph().getSliceType() == destinationSliceType) {
                return connectionGraph;
            }
        }
        return null;
    }
    
}
