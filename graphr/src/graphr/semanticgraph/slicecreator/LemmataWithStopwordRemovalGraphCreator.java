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
 * Graph. All lemmas which are stopwords, will be not added as vertex to the
 * slice.
 *
 * @author Florian
 */
public class LemmataWithStopwordRemovalGraphCreator implements SemanticSliceCreator {

    @Override
    public SemanticGraph createSemanticSliceGraph(SemanticGraph graph, String dataSource) {

        SemanticSliceGraph<GHT, GHT> semanticSlice = new SemanticSliceGraph<GHT, GHT>(SemanticSliceGraphType.LEMMATA_WITH_STOPWORD_REMOVAL);
        String text = dataSource;

        List<Tupel<String, String>> lemmata = SemanticGraphIO.getLemmaFromText(text);
        for (Tupel<String, String> lemma : lemmata) {
            if (!SemanticGraphIO.isStopword(lemma.getEntry1())) {
                //check if lemma is already as vertex available
                SemanticVertex lemmaVertex = this.getLemmaVertex(semanticSlice, lemma.getEntry1(), lemma.getEntry2());

                if (lemmaVertex == null) { //lemma vertex was not created yet
                    lemmaVertex = new SemanticVertex(SemanticSliceGraphType.LEMMATA_WITH_STOPWORD_REMOVAL.getSliceName());
                    lemmaVertex.setValue(lemma.getEntry1());
                    String strPOS = lemma.getEntry2();
                    lemmaVertex.setType(strPOS);
                    semanticSlice.addVertex(lemmaVertex);
                }
            }
        }

        graph.addSemanticSlice(semanticSlice);
        return graph;
    }

    @Override
    public SemanticGraph createSemanticSliceConnectionGraph(SemanticGraph graph, SemanticSliceGraph<GHT, GHT> sourceGraph, SemanticSliceGraph<GHT, GHT> destinationGraph) {
        if (destinationGraph == null) {
            destinationGraph = new SemanticSliceGraph<GHT, GHT>(SemanticSliceGraphType.LEMMATA_WITH_STOPWORD_REMOVAL);
            graph.addSemanticSlice(destinationGraph);
        }
        SemanticSliceConnectionGraph<GHT, GHT> semanticSliceConnectionGraph = new SemanticSliceConnectionGraph<GHT, GHT>(sourceGraph, destinationGraph);

        for (Vertex<GHT, GHT> v : sourceGraph.getVertices()) {
            SemanticVertex semanticVertex = (SemanticVertex) v;

            String word = semanticVertex.getValue();
            String lemma = SemanticGraphIO.getLemma(word);

            if (!SemanticGraphIO.isStopword(lemma) && !semanticVertex.getType().equals(POSTag.SYMBOL.name()) && !semanticVertex.getType().equals(POSTag.non.name())) {

                SemanticVertex lemmaVertex = this.getLemmaVertex(destinationGraph, lemma, semanticVertex.getType());
                if (lemmaVertex == null) {
                    lemmaVertex = new SemanticVertex(SemanticSliceGraphType.LEMMATA_WITH_STOPWORD_REMOVAL.getSliceName());
                    lemmaVertex.setValue(lemma);
                    //Take type from source vertex
                    lemmaVertex.setType(semanticVertex.getType());
                    destinationGraph.addVertex(lemmaVertex);
                }
                semanticSliceConnectionGraph.addEdge(semanticVertex, lemmaVertex, SemanticSliceGraphType.LEMMATA_WITH_STOPWORD_REMOVAL.getIncommingEdgeName());
            }
        }

        graph.addSemanticSliceConnectionGraph(semanticSliceConnectionGraph);
        return graph;
    }

    /**
     * This method looks for distincted vertices by their value and posTag
     *
     * @param graph
     * @param lemma
     * @return
     */
    private SemanticVertex getLemmaVertex(SemanticSliceGraph<GHT, GHT> graph, String lemma, String PosTag) {
        for (Vertex<GHT, GHT> v : graph.getVertices()) {
            SemanticVertex semanticVertex = (SemanticVertex) v;

            if (semanticVertex.getValue().equals(lemma) && semanticVertex.getType().equals(PosTag)) {
                return semanticVertex;
            }
        }
        return null;
    }

}
