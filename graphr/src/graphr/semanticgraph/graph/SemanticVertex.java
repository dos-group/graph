package graphr.semanticgraph.graph;

import graphr.data.GHT;
import graphr.graph.Vertex;

/**
 * This class represents a semantic vertex in a graph-based datastructure. A
 * semantic vertex
 *
 * @author Florian
 */
public class SemanticVertex extends Vertex<GHT, GHT> {

    /**
     * Basic constructor, which creates a SemanticVertex with a certain label.
     * The label describes the partition where the vertex is placed in the
     * graph. In the context of semantic slices, the label represents the name
     * of a @SemanticSliceGraph.
     *
     * @param label name of the semantic slice
     */
    public SemanticVertex(String label) {
        super(new GHT());
        data.put("label", label);
    }

    /**
     *
     * @return name of the semantic slice
     */
    public String getLabel() {
        return data.getTable().get("label").s();
    }

    /**
     *
     * @return POSTag of the value parameter (word)
     */
    public String getType() {
        return data.getTable().get("type").s();
    }

    /**
     * Type is needed, to distinguish between words with different POSTags and
     * therefore with different semantic meaning.
     *
     * @param type POSTag of the value parameter (word)
     */
    public void setType(String type) {
        data.put("type", type);
    }

    /**
     *
     * @return word or sense
     */
    public String getValue() {
        return data.getTable().get("value").s();
    }

    /**
     * Value stores a word or a sense (of a word).
     *
     * @param value
     */
    public void setValue(String value) {
        this.data.put("value", value);
    }

    /**
     * Removing an edge directed from or into this vertex.
     *
     * @param edgeToRemove edge to be removed
     */
    public void removeEdge(SemanticEdge edgeToRemove) {
        this.getEdges().remove(edgeToRemove);
    }

}
