package graphr.semanticgraph.io;

import graphr.semanticgraph.graph.SemanticSliceGraph;
import graphr.semanticgraph.io.wiktionary.WiktionaryLemma;
import graphr.semanticgraph.io.wiktionary.WiktionarySense;
import graphr.util.Tupel;
import java.util.HashMap;
import java.util.List;

import graphr.semanticgraph.io.enums.POSTagStanfordNLP;
import graphr.semanticgraph.io.enums.POSTag;

import graphr.data.GHT;

/**
 * A wrapper class for IO implementations that should hide the implementation
 * details from the user. It also offers caching capabilities. It is build for
 * the semantic graph creation.
 *
 * @author Alexander, Florian
 */
public class SemanticGraphIO {

    // since the LemmaGraph and SynonymGraph implementations generally check for
    // existing vertices, before adding another vertex, there is already some
    // sort of caching in the graph creation implementation
    // since core.io is an independent package/module, we cannot assume that
    // this is always the case and thus caching is encouraged in order to
    // minimize the IO access
    private static HashMap<String, String> lemmaCache = new HashMap<String, String>();
    private static HashMap<String, POSTagStanfordNLP> posCache = new HashMap<String, POSTagStanfordNLP>();
    private static HashMap<String, List<String>> synCache = new HashMap<String, List<String>>();

    /**
     *
     * @param text untokenized text
     * @return all lemmas from the text. Tupel: Entry1: lemma, Entry2: posTag
     */
    public static List<Tupel<String, String>> getLemmaFromText(String text) {
        return StanfordNLPConnector.getLemmaFromText(text);
    }

    /**
     *
     * @param word token (single word)
     * @return lemma of this word
     */
    public static String getLemma(String word) {
        System.out.println(word);
        if (lemmaCache.containsKey(word)) {
            return lemmaCache.get(word);
        } else {
            String lemma = StanfordNLPConnector.getLemma(word);

            lemmaCache.put(word, lemma);
            return lemma;
        }
    }

    /**
     *
     * @param word tokenized word (single word)
     * @return POSTag of this word
     */
    public static POSTag getPOS(String word) {
        if (posCache.containsKey(word)) {
            return posCache.get(word).toPOSTag();
        } else {
            String pos = StanfordNLPConnector.getPOS(word);

            // TODO cache null results too? depends on case inside library
            if (pos == null) {
                posCache.put(word, null);
                return null;
            }

            try {
                POSTagStanfordNLP posTag = POSTagStanfordNLP.valueOf(pos);
                posCache.put(word, posTag);
                return posTag.toPOSTag();
            } catch (IllegalArgumentException e) {
                return null;
            }
        }
    }

    /**
     *
     * @param word tokenized word (single word)
     * @return checks if word is a stopword
     */
    public static boolean isStopword(String word) {
        return StopwordConnector.getInstance().isStopword(word);
    }

    /**
     *
     * @param text untokenized text
     * @return all tokens (entry1) with posTag (entry2)
     */
    public static List<Tupel<String, String>> getTokens(String text) {
        return StanfordNLPConnector.getTokens(text);
    }

    /**
     *
     * @param text untokenized text from a datasource
     * @param semanticSlice semantic slice, which gehts the connections
     * @return semantic slice with added Dependency connections provided by
     * StanfordNLP library.
     */
    public static SemanticSliceGraph setAllDependencies(String text, SemanticSliceGraph<GHT, GHT> semanticSlice) {
        return StanfordNLPConnector.setAllDependencies(text, semanticSlice);
    }

    /**
     *
     * @return all wiktionary lemmas
     */
    public static List<WiktionaryLemma> getWiktionaryLemmas() {
        return WiktionaryConnector.getInstance().getAllWiktionaryLemmas();
    }

    /**
     *
     * @param lemma lemmatized word
     * @param posTag POSTag
     * @return all wiktionary lemmas with this word and POSTag
     */
    public static List<WiktionaryLemma> getWiktionaryLemma(String lemma, String posTag) {
        return WiktionaryConnector.getInstance().getWiktionaryLemma(lemma, posTag);
    }

    /**
     *
     * @param lemma lemma
     * @param posTag POSTag
     * @return all wiktionary senses with this lemma and POSTag
     */
    public static List<WiktionarySense> getWiktionarySenses(String lemma, String posTag) {
        return WiktionaryConnector.getInstance().getWiktionarySenses(lemma, posTag);
    }

    /**
     *
     * @param text untokenized text
     * @return text which contains no stopwords anymore
     */
    public static String removeStopwords(String text) {
        return StopwordConnector.getInstance().removeStopwords(text);
    }
}
