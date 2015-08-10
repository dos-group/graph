package graphr.semanticgraph.graph;

import java.util.ArrayList;
import java.util.List;

/**
 * This class stores the configurations to create a SemanticGraph. It stores
 * semantic slice connections which represent the different parts of the whole
 * graph and their relation to eachother. Please note, that the order of
 * semantic slice connection needs to be right, for the creation of the semantic
 * graph. Newly generated slices need to be created from a datasource.
 *
 * @author Florian
 */
public class SemanticGraphConfig {

    private final List<SemanticSliceConnectionConfig> semanticSliceConnections;

    /**
     * Creates a new and empty configuration object.
     */
    public SemanticGraphConfig() {
        semanticSliceConnections = new ArrayList<SemanticSliceConnectionConfig>();
    }

    /**
     * Adds a new semantic slice connection to this configuration object.
     *
     * @param semanticSliceConnection a new semantic slice connection
     */
    public void addConnection(SemanticSliceConnectionConfig semanticSliceConnection) {
        semanticSliceConnections.add(semanticSliceConnection);
    }

    /**
     *
     * @return all semantic slice connections
     */
    public List<SemanticSliceConnectionConfig> getSemanticSliceConnections() {
        return semanticSliceConnections;
    }

}
