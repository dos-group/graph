package graphr.semanticgraph.slicecreator;

import graphr.semanticgraph.graph.SemanticGraph;
import graphr.semanticgraph.graph.SemanticSliceConnectionGraph;
import graphr.semanticgraph.graph.SemanticSliceGraph;
import graphr.semanticgraph.graph.SemanticVertex;
import graphr.semanticgraph.io.SemanticGraphIO;
import graphr.semanticgraph.io.wiktionary.WiktionaryLemma;
import graphr.semanticgraph.SemanticSliceCreator;
import graphr.semanticgraph.graph.SemanticSliceGraphType;
import graphr.data.GHT;
import graphr.graph.Vertex;
import java.util.List;

/**
 * This class creates or extends a semantic slice by lemmata from wiktionary.
 *
 * @author Florian
 */
public class WiktionaryLemmaGraphCreator implements SemanticSliceCreator {

    @Override
    public SemanticGraph createSemanticSliceGraph(SemanticGraph graph, String dataSource) {
        //Datasource is not needed
        SemanticSliceGraph<GHT, GHT> semanticSlice = new SemanticSliceGraph<GHT, GHT>(SemanticSliceGraphType.WIKTIONARY_LEMMATA);
        List<WiktionaryLemma> lemmas = SemanticGraphIO.getWiktionaryLemmas();
        for (WiktionaryLemma lemma : lemmas) {
            //Check if lemma exists in slice already
            SemanticVertex vertex = this.getVertex(semanticSlice, lemma.getLemma(), lemma.getPOS());
            if (vertex == null) {
                vertex = new SemanticVertex(SemanticSliceGraphType.WIKTIONARY_LEMMATA.getSliceName());
                vertex.setValue(lemma.getLemma());
                vertex.setType(lemma.getPOS());
                semanticSlice.addVertex(vertex);
            }

            //Add relation edges
            for (WiktionaryLemma relation : lemma.getRelations()) {
                SemanticVertex relationVertex = this.getVertex(semanticSlice, relation.getLemma(), relation.getPOS());
                if (relationVertex == null) {
                    relationVertex = new SemanticVertex(SemanticSliceGraphType.WIKTIONARY_LEMMATA.getSliceName());
                    relationVertex.setValue(relation.getLemma());
                    relationVertex.setType(relation.getPOS());
                    semanticSlice.addVertex(relationVertex);
                }
                semanticSlice.addEdge(vertex, relationVertex, SemanticSliceGraphType.WIKTIONARY_LEMMATA.getIncommingEdgeName());
            }
        }
        graph.addSemanticSlice(semanticSlice);
        return graph;
    }

    @Override
    public SemanticGraph createSemanticSliceConnectionGraph(SemanticGraph graph, SemanticSliceGraph<GHT, GHT> sourceGraph, SemanticSliceGraph<GHT, GHT> destinationGraph) {
        if (destinationGraph == null) {
            destinationGraph = new SemanticSliceGraph<GHT, GHT>(SemanticSliceGraphType.WIKTIONARY_LEMMATA);
            graph.addSemanticSlice(destinationGraph);
        }
        SemanticSliceConnectionGraph<GHT, GHT> semanticSliceConnectionGraph = new SemanticSliceConnectionGraph<GHT, GHT>(sourceGraph, destinationGraph);

        for (Vertex<GHT, GHT> v : sourceGraph.getVertices()) {
            SemanticVertex semanticVertex = (SemanticVertex) v;
            String lemma = semanticVertex.getValue();
            String lemmaPOSTag = semanticVertex.getType();

            List<WiktionaryLemma> wktLemmas = SemanticGraphIO.getWiktionaryLemma(lemma, lemmaPOSTag);
            for (WiktionaryLemma wktLemma : wktLemmas) {
                //Check if lemma exists in slice already
                SemanticVertex vertex = this.getVertex(destinationGraph, wktLemma.getLemma(), semanticVertex.getType());
                if (vertex == null) {
                    vertex = new SemanticVertex(SemanticSliceGraphType.WIKTIONARY_LEMMATA.getSliceName());
                    vertex.setValue(wktLemma.getLemma());
                    vertex.setType(semanticVertex.getType());
                    destinationGraph.addVertex(vertex);
                }
                semanticSliceConnectionGraph.addEdge(semanticVertex, vertex, SemanticSliceGraphType.WIKTIONARY_LEMMATA.getIncommingEdgeName());

                //Add relation edges
                for (WiktionaryLemma relation : wktLemma.getRelations()) {
                    SemanticVertex relationVertex = this.getVertex(destinationGraph, relation.getLemma(), relation.getPOS());
                    if (relationVertex == null) {
                        relationVertex = new SemanticVertex(SemanticSliceGraphType.WIKTIONARY_LEMMATA.getSliceName());
                        relationVertex.setValue(relation.getLemma());
                        relationVertex.setType(relation.getPOS());
                        destinationGraph.addVertex(relationVertex);
                    }
                    destinationGraph.addEdge(vertex, relationVertex, SemanticSliceGraphType.WIKTIONARY_LEMMATA.getIncommingEdgeName());
                }
            }
        }

        graph.addSemanticSliceConnectionGraph(semanticSliceConnectionGraph);
        return graph;
    }

    private SemanticVertex getVertex(SemanticSliceGraph<GHT, GHT> graph, String lemma, String PosTag) {
        for (Vertex<GHT, GHT> v : graph.getVertices()) {
            SemanticVertex semanticVertex = (SemanticVertex) v;

            if (semanticVertex.getValue().equals(lemma) && semanticVertex.getType().equals(PosTag)) {
                return semanticVertex;
            }
        }
        return null;
    }

}
