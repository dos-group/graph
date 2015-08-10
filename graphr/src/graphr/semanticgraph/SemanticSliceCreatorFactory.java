package graphr.semanticgraph;

import graphr.semanticgraph.graph.SemanticSliceGraphType;

/**
 * This Class implements the Factory Pattern to create easy a Semantic Slice
 * Creator Object, just by selecting the SemanticSliceCreatorType. Also, this
 * class is Singleton, because we just need to use one such instance.
 *
 * @author Florian
 */
public class SemanticSliceCreatorFactory {

    private static SemanticSliceCreatorFactory instance;

    private SemanticSliceCreatorFactory() {
    }

    /**
     *
     * @return instance of this class
     */
    public static SemanticSliceCreatorFactory getInstance() {
        if (instance == null) {
            return new SemanticSliceCreatorFactory();
        }
        return instance;
    }

    /**
     * Gets a new instance of a SemanticSliceCreator by its type.
     *
     * @param creatorType type of the creator
     * @return semantic slice creator
     */
    public SemanticSliceCreator getSemanticSliceCreator(SemanticSliceGraphType creatorType) {
        try {
            return (SemanticSliceCreator) creatorType.getCreator().newInstance();
        } catch (final InstantiationException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }
}
