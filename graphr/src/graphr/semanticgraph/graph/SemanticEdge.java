package graphr.semanticgraph.graph;

import graphr.data.GHT;
import graphr.graph.Edge;

/**
 * A SemanticEdge is a directed edge in a graph-based datastructure. It stores
 * semantic knowledge with values on the edge for semantic data processing.
 *
 * @author Florian
 */
public class SemanticEdge extends Edge<GHT, GHT> {

    private SemanticVertex source;

    /**
     * Generates a directed edge from a source vertex to a destination vertex
     * with a certain name (stored in label) and weight of zero.
     *
     * @param src source vertex
     * @param dst destination vertex
     * @param label name of the edge
     */
    public SemanticEdge(SemanticVertex src, SemanticVertex dst, String label) {
        super(new GHT());

        src.addEdge(this);
        dst.addEdge(this);
        this.setTarget(dst);
        this.setSource(src);
        data.put("label", label);
        data.put("weight", 0.0);
    }

    /**
     *
     * @return name of the edge
     */
    public String getLabel() {
        return data.getTable().get("label").s();
    }

    /**
     * Default is zero.
     *
     * @return weight of the edge
     */
    public double getWeight() {
        return data.getTable().get("weight").d();
    }

    /**
     *
     * @param weight weight of the edge
     */
    public void setWeight(double weight) {
        data.put("weight", weight);
    }

    /**
     *
     * @return source vertex
     */
    public SemanticVertex getSource() {
        return source;
    }

    /**
     *
     * @param src source vertex
     */
    public void setSource(SemanticVertex src) {
        this.source = src;
    }

}
