package graphr.semanticgraph.io.wiktionary;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a lemma of a word in wiktionary. Most likely known as
 * WiktionaryPage.
 *
 * @author Florian
 */
public class WiktionaryLemma {

    private final String lemmaName;
    private final String pos;
    private final List<WiktionaryLemma> relations;

    /**
     * Generates a WiktionaryLemma by its name and POSTag.
     *
     * @param name lemma
     * @param pos POSTag
     */
    public WiktionaryLemma(String name, String pos) {
        this.pos = pos;
        this.lemmaName = name;
        this.relations = new ArrayList<WiktionaryLemma>();
    }

    /**
     * Adds a relating lemma. These relations will be filtered by Synonyms.
     *
     * @param relation relation to another WiktionaryLemma
     */
    public void addRelation(WiktionaryLemma relation) {
        this.relations.add(relation);
    }

    /**
     *
     * @return lemma
     */
    public String getLemma() {
        return lemmaName;
    }

    /**
     *
     * @return all relations to other WiktionaryLemmas
     */
    public List<WiktionaryLemma> getRelations() {
        return relations;
    }

    /**
     *
     * @return POSTag as String
     */
    public String getPOS() {
        return pos;
    }
}
