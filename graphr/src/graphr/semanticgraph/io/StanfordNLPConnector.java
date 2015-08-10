package graphr.semanticgraph.io;

import graphr.semanticgraph.graph.SemanticSliceGraph;
import graphr.semanticgraph.graph.SemanticVertex;
import graphr.semanticgraph.graph.SemanticSliceGraphType;
import graphr.util.Tupel;
import graphr.semanticgraph.io.enums.POSTag;
import graphr.semanticgraph.io.enums.POSTagStanfordNLP;
import edu.stanford.nlp.ling.CoreAnnotations;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import edu.stanford.nlp.ling.CoreAnnotations.LemmaAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.PartOfSpeechAnnotation;
import edu.stanford.nlp.ling.CoreAnnotations.TokensAnnotation;
import edu.stanford.nlp.ling.CoreLabel;
import edu.stanford.nlp.pipeline.Annotation;
import edu.stanford.nlp.ling.CoreAnnotations.SentencesAnnotation;
import edu.stanford.nlp.ling.IndexedWord;
import edu.stanford.nlp.pipeline.StanfordCoreNLP;
import edu.stanford.nlp.semgraph.SemanticGraph;
import edu.stanford.nlp.semgraph.SemanticGraphCoreAnnotations.CollapsedCCProcessedDependenciesAnnotation;
import edu.stanford.nlp.semgraph.SemanticGraphEdge;
import edu.stanford.nlp.util.CoreMap;
import graphr.data.GHT;
import java.util.ArrayList;

/**
 * This class provides static methods do different natural language processing
 * features of the Stanford NLP Library.
 *
 * @author Alexander, Florian
 */
public class StanfordNLPConnector {

    private StanfordNLPConnector() {
    }

    /**
     * Retrieves the lemma for the specified word.
     *
     * @param word
     * @return null if there is no lemma for the specified word
     */
    public static String getLemma(String word) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation document = new Annotation(word);
        pipeline.annotate(document);

        List<CoreLabel> coreLabels = document.get(TokensAnnotation.class);

        if (coreLabels.size() > 0) {
            return coreLabels.get(0).get(LemmaAnnotation.class);
        }
        return null;
    }

    /**
     * Retriefs lemmata with posTag for an untokenized text
     *
     * @param text untokenized text
     * @return list of lemmata (entry1) with posTag (entry2)
     */
    public static List<Tupel<String, String>> getLemmaFromText(String text) {
        List<Tupel<String, String>> lemmata = new ArrayList<Tupel<String, String>>();
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos, lemma");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(CoreAnnotations.SentencesAnnotation.class);
        for (CoreMap sentence : sentences) {
            for (CoreLabel token : sentence.get(CoreAnnotations.TokensAnnotation.class)) {
                String lemma = token.get(CoreAnnotations.LemmaAnnotation.class);
                String posTagNLP = token.get(CoreAnnotations.PartOfSpeechAnnotation.class);
                POSTagStanfordNLP stanfordPOSTag = POSTagStanfordNLP.valueOf(posTagNLP);
                String posTag = stanfordPOSTag.toPOSTag().name();
                lemmata.add(new Tupel<String, String>(lemma, posTag));
            }
        }
        return lemmata;
    }

    /**
     * Retrieves the Part-of-Speech tag for the specified word.
     *
     * @param word
     * @return null if there is no tag for the specified word
     */
    public static String getPOS(String word) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation document = new Annotation(word);
        pipeline.annotate(document);

        List<CoreLabel> coreLabels = document.get(TokensAnnotation.class);
        String pos = coreLabels.get(0).get(PartOfSpeechAnnotation.class);//This might not be a good solution to test a single word here

        return pos;
    }

    /**
     * Tokenizes a text and returns tokens with posTag.
     *
     * @param text untokenized text
     * @return list of tokens (entry1) and posTag (entry2)
     */
    public static List<Tupel<String, String>> getTokens(String text) {
        Properties props = new Properties();
        props.setProperty("annotators", "tokenize, ssplit, pos");
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);

        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        List<Tupel<String, String>> tokensWithPosTag = new LinkedList<Tupel<String, String>>();

        for (CoreLabel cl : document.get(TokensAnnotation.class)) {
            String posTagNLP = cl.get(CoreAnnotations.PartOfSpeechAnnotation.class);
            if (!posTagNLP.isEmpty() && containsPOSTag(posTagNLP)) {
                POSTagStanfordNLP stanfordPOSTag = POSTagStanfordNLP.valueOf(posTagNLP);
                String posTag = stanfordPOSTag.toPOSTag().name();
                tokensWithPosTag.add(new Tupel<String, String>(cl.value(), posTag));
            } else {
                String posTag = POSTag.non.name();
                tokensWithPosTag.add(new Tupel<String, String>(cl.value(), posTag));
            }

        }

        return tokensWithPosTag;
    }

    private static boolean containsPOSTag(String posTag) {
        for (POSTagStanfordNLP p : POSTagStanfordNLP.values()) {
            if (p.name().equals(posTag)) {
                return true;
            }
        }
        return false;
    }

    /**
     *
     * @param text untokenized text from a datasource
     * @param semanticSlice semantic slice, which gehts the connections
     * @return semantic slice with added Dependency connections provided by
     * StanfordNLP library.
     */
    public static SemanticSliceGraph setAllDependencies(String text, SemanticSliceGraph<GHT, GHT> semanticSlice) {
        Properties props = new Properties();
        props.put("annotators", "tokenize, ssplit, pos, lemma, ner, parse, dcoref");
        props.put("dcoref.score", true);
        StanfordCoreNLP pipeline = new StanfordCoreNLP(props);
        Annotation document = new Annotation(text);
        pipeline.annotate(document);

        List<CoreMap> sentences = document.get(SentencesAnnotation.class);
        List<CoreLabel> tokens = document.get(TokensAnnotation.class);
        for (CoreLabel token : tokens) {
            int vertexIndexSource = tokens.indexOf(token);
            SemanticVertex sourceVertex = (SemanticVertex) semanticSlice.getVertex(vertexIndexSource);
            for (CoreMap sentence : sentences) {
                SemanticGraph dependencies = sentence.get(CollapsedCCProcessedDependenciesAnnotation.class);
//                SemanticGraph dependencies2 = sentence.get(BasicDependenciesAnnotation.class);

                for (IndexedWord dependency : dependencies.vertexSet()) {
                    if (dependency.beginPosition() == token.beginPosition()) {
                        //Found our companion
                        List<SemanticGraphEdge> edges = dependencies.outgoingEdgeList(dependency);
                        for (SemanticGraphEdge edge : edges) {
                            for (CoreLabel targetToken : tokens) {
                                if (edge.getTarget().beginPosition() == targetToken.beginPosition()) {
                                    int vertexIndexTarget = tokens.indexOf(targetToken);
                                    SemanticVertex targetVertex = (SemanticVertex) semanticSlice.getVertex(vertexIndexTarget);
                                    semanticSlice.addEdge(sourceVertex, targetVertex, SemanticSliceGraphType.TOKEN_WITH_NLPCLUSTER.getIncommingEdgeName());
                                }
                            }
                        }
                    }
                }
            }
        }
        return semanticSlice;
    }

}
