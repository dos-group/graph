package graphr.semanticgraph.io.wiktionary;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a sense of a word based on wiktionary.
 *
 * @author Florian
 */
public class WiktionarySense {

    private final String descriptionText;
    private final String posTag;
    private final List<String> relations;

    /**
     *
     * @param descriptionText sense as a sentence
     * @param posTag POSTag of the underlying word
     */
    public WiktionarySense(String descriptionText, String posTag) {
        this.descriptionText = descriptionText;
        this.posTag = posTag;
        this.relations = new ArrayList<String>();
    }

    /**
     *
     * @param relation word which relates to this sense
     */
    public void addRelation(String relation) {
        this.relations.add(relation);
    }

    /**
     *
     * @return sense as a sentence
     */
    public String getDescriptionText() {
        return descriptionText;
    }

    /**
     *
     * @return all relations to other words
     */
    public List<String> getRelations() {
        return relations;
    }

    /**
     *
     * @return POSTag
     */
    public String getPOSTag() {
        return posTag;
    }
}
