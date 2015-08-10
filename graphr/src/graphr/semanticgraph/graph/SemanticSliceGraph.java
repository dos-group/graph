package graphr.semanticgraph.graph;

import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.GraphData;
import graphr.graph.Vertex;
import java.util.Collection;

/**
 * This class creates the base structure of a graph from graphr lib. It
 * represents a part of a whole graph with certain vertex or edge related
 * properties. These substructures are calles semantic slices. A semantic slice
 * can be connected to other semantic slices.
 *
 * @author Florian, Alexander
 * @param <DV>
 * @param <DE>
 */
public class SemanticSliceGraph<DV extends GraphData, DE extends GraphData>
        extends Graph<DV, DE> {

    private final SemanticSliceGraphType sliceType;

    /**
     * Creates a semantic slice graph with a specific type (name).
     *
     * @param sliceType type (name) of the semantic slice graph
     */
    public SemanticSliceGraph(SemanticSliceGraphType sliceType) {
        this.sliceType = sliceType;
    }

    /**
     *
     * @return type (name) of the semantic slice graph
     */
    public SemanticSliceGraphType getSliceType() {
        return sliceType;
    }

    /**
     * Checks if an edge (specified by a source vertex and destination vertex)
     * is already created inside the graph.
     *
     * @param src source vertex of edge
     * @param dst destination vertex of edge
     * @return yes, if edge is in the graph; no, else
     */
    public boolean hasEdge(Vertex<DV, DE> src, Vertex<DV, DE> dst) {
        for (Edge edge : src.getEdges()) {
            if (edge.getTarget() == dst) {
                return true;
            }
        }

        return false;
    }

    /**
     * Adds a new directed semantic edge between two semantic vertices. The name
     * of the edge is stored in label.
     *
     * @param src source vertex of edge
     * @param dst destination vertex of edge
     * @param label name of the edge
     * @return the newly created edge
     */
    public SemanticEdge addEdge(SemanticVertex src, SemanticVertex dst, String label) {
        return new SemanticEdge(src, dst, label);
    }

    /**
     *
     * @return all semantic vertices inside this semantic slice graph
     */
    public Collection<SemanticVertex> getSemanticVertices() {
        Collection<SemanticVertex> semanticVertices = (Collection<SemanticVertex>) (Collection<?>) this.getVertices();
        return semanticVertices;
    }

    @Override
    public String toString() {
        String erg = this.sliceType + "\n" + super.toString();
        return erg;
    }

}
