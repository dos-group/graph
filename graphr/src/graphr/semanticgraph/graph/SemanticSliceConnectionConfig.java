package graphr.semanticgraph.graph;

/**
 * This class represents the configuration of a connection (Edges) between two
 * Types of Semantic Slices. As source a dataset can be used or an already
 * created semantic slice. As destination a semantic slice needs to be created.
 *
 * @author Florian
 */
public class SemanticSliceConnectionConfig {

    private final String srcData; //A text, which is used as input source
    private final SemanticSliceGraphType src;
    private final SemanticSliceGraphType dest;

    /**
     * Generates a connection config with a semantic slice as a source.
     * @param src semantic slice as source
     * @param dest semantic slice as destination
     */
    public SemanticSliceConnectionConfig(SemanticSliceGraphType src, SemanticSliceGraphType dest) {
        if (src == null || dest == null) {
            throw new NullPointerException("Source or Destination is null");
        }
        this.src = src;
        this.dest = dest;
        this.srcData = null;
    }

    /**
     * Generates a connection config with a text as a source.
     * @param srcData text as source
     * @param dest semantic slice as destination
     */
    public SemanticSliceConnectionConfig(String srcData, SemanticSliceGraphType dest) {
        if (srcData == null || dest == null) {
            throw new NullPointerException("Source or Destination is null");
        }
        this.src = null;
        this.dest = dest;
        this.srcData = srcData;
    }

    /**
     *
     * @return type (name) of the source dataset (it is always a text)
     */
    public String getDataSourceObject() {
        return srcData;
    }

    /**
     *
     * @return type (name) of the source semantic slice
     */
    public SemanticSliceGraphType getSourceGraphType() {
        return src;
    }

    /**
     *
     * @return type (name) of the destinating semantic slice
     */
    public SemanticSliceGraphType getDestinationGraphType() {
        return dest;
    }

}
