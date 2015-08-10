package graphr.semanticgraph.slicecreator;

import graphr.semanticgraph.graph.SemanticGraph;
import graphr.semanticgraph.graph.SemanticSliceConnectionGraph;
import graphr.semanticgraph.SemanticSliceCreator;
import graphr.semanticgraph.graph.SemanticSliceGraph;
import graphr.semanticgraph.graph.SemanticVertex;
import graphr.semanticgraph.io.SemanticGraphIO;
import graphr.semanticgraph.graph.SemanticSliceGraphType;
import graphr.util.Tupel;
import graphr.semanticgraph.io.enums.POSTag;
import graphr.data.GHT;
import graphr.graph.Vertex;
import java.util.List;

/**
 * This is a class which implements the creation of a Lemmata Semantic Slice
 * Graph. Using the Stanford NLP library for getting the lemma of a word.
 *
 * @author Florian
 */
public class LemmataGraphCreator implements SemanticSliceCreator {

    @Override
    public SemanticGraph createSemanticSliceGraph(SemanticGraph graph, String dataSource) {

        SemanticSliceGraph<GHT, GHT> semanticSlice = new SemanticSliceGraph<GHT, GHT>(SemanticSliceGraphType.LEMMATA);
        String text = dataSource;

        List<Tupel<String, String>> lemmata = SemanticGraphIO.getLemmaFromText(text);
        for (Tupel<String, String> lemma : lemmata) {
            //check if lemma is already as vertex available
            SemanticVertex lemmaVertex = this.getLemmaVertex(semanticSlice, lemma.getEntry1());

            if (lemmaVertex == null) { //lemma vertex was not created yet
                lemmaVertex = new SemanticVertex(SemanticSliceGraphType.LEMMATA.getSliceName());
                lemmaVertex.setValue(lemma.getEntry1());
                String posTag = lemma.getEntry2();
                lemmaVertex.setType(posTag);

                semanticSlice.addVertex(lemmaVertex);
            }
        }
        graph.addSemanticSlice(semanticSlice);

        return graph;
    }

    @Override
    public SemanticGraph createSemanticSliceConnectionGraph(SemanticGraph graph, SemanticSliceGraph<GHT, GHT> sourceGraph, SemanticSliceGraph<GHT, GHT> destinationGraph) {

        if (destinationGraph == null) {
            destinationGraph = new SemanticSliceGraph<GHT, GHT>(SemanticSliceGraphType.LEMMATA);
            graph.addSemanticSlice(destinationGraph);
        }
        SemanticSliceConnectionGraph<GHT, GHT> semanticSliceConnectionGraph = new SemanticSliceConnectionGraph<GHT, GHT>(sourceGraph, destinationGraph);

        for (Vertex<GHT, GHT> v : sourceGraph.getVertices()) {
            SemanticVertex semanticVertex = (SemanticVertex) v;

            String word = semanticVertex.getValue();
            String lemma = SemanticGraphIO.getLemma(word);

            SemanticVertex lemmaVertex = this.getLemmaVertex(destinationGraph, lemma);
            if (lemmaVertex == null) {
                lemmaVertex = new SemanticVertex(SemanticSliceGraphType.LEMMATA.getSliceName());
                lemmaVertex.setValue(lemma);

                POSTag posTag = SemanticGraphIO.getPOS(lemma);
                if (posTag == null) {
                    continue;
                }
                String strPOS = posTag.toString();
                lemmaVertex.setType(strPOS);

                destinationGraph.addVertex(lemmaVertex);
            }
            semanticSliceConnectionGraph.addEdge(semanticVertex, lemmaVertex, SemanticSliceGraphType.LEMMATA.getIncommingEdgeName());
        }
        graph.addSemanticSliceConnectionGraph(semanticSliceConnectionGraph);
        return graph;
    }

    private SemanticVertex getLemmaVertex(SemanticSliceGraph<GHT, GHT> graph, String lemma) {
        for (Vertex<GHT, GHT> v : graph.getVertices()) {
            SemanticVertex semanticVertex = (SemanticVertex) v;

            if (semanticVertex.getValue().equals(lemma)) {
                return semanticVertex;
            }
        }
        return null;
    }

}
