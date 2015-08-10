package graphr.semanticgraph.slicecreator;

import graphr.semanticgraph.graph.SemanticGraph;
import graphr.semanticgraph.graph.SemanticSliceConnectionGraph;
import graphr.semanticgraph.graph.SemanticSliceGraph;
import graphr.semanticgraph.graph.SemanticVertex;
import graphr.semanticgraph.io.SemanticGraphIO;
import graphr.semanticgraph.io.wiktionary.WiktionarySense;
import graphr.semanticgraph.SemanticSliceCreator;
import graphr.semanticgraph.graph.SemanticSliceGraphType;
import graphr.data.GHT;
import graphr.graph.Vertex;
import java.util.List;

/**
 * This class creates or extends a semantic slice by all senses of a word with
 * the help of wiktionary. Senses which are potentially synonyms will be
 * connected by an edge. These relations are also taken from wiktionary.
 *
 * @author Florian
 */
public class WiktionarySenseGraphCreator implements SemanticSliceCreator {

    @Override
    public SemanticGraph createSemanticSliceGraph(SemanticGraph graph, String dataSource) {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public SemanticGraph createSemanticSliceConnectionGraph(SemanticGraph graph, SemanticSliceGraph<GHT, GHT> sourceGraph, SemanticSliceGraph<GHT, GHT> destinationGraph) {
        if (destinationGraph == null) {
            destinationGraph = new SemanticSliceGraph<GHT, GHT>(SemanticSliceGraphType.SENSEGRAPH);
            graph.addSemanticSlice(destinationGraph);
        }
        SemanticSliceConnectionGraph<GHT, GHT> semanticSliceConnectionGraph = new SemanticSliceConnectionGraph<GHT, GHT>(sourceGraph, destinationGraph);

        for (Vertex<GHT, GHT> v : sourceGraph.getVertices()) {
            SemanticVertex semanticVertex = (SemanticVertex) v;
            String lemma = semanticVertex.getValue();
            String posTag = semanticVertex.getType();

            List<WiktionarySense> senses = SemanticGraphIO.getWiktionarySenses(lemma, posTag);
            for (WiktionarySense sense : senses) {
                SemanticVertex senseVertex = this.getVertex(destinationGraph, sense.getDescriptionText());
                if (senseVertex == null) {
                    senseVertex = new SemanticVertex(SemanticSliceGraphType.SENSEGRAPH.getSliceName());
                    senseVertex.setValue(sense.getDescriptionText());
                    senseVertex.setType(sense.getPOSTag());
                    destinationGraph.addVertex(senseVertex);
                }
                semanticSliceConnectionGraph.addEdge(semanticVertex, senseVertex, SemanticSliceGraphType.SENSEGRAPH.getIncommingEdgeName());

                //Add relations back to WiktionaryLemmaGraph
                for (String relation : sense.getRelations()) {
                    SemanticVertex relationVertex = this.getVertex(sourceGraph, relation);
                    if (relationVertex != null) {
                        semanticSliceConnectionGraph.addEdge(senseVertex, relationVertex, SemanticSliceGraphType.WIKTIONARY_LEMMATA.getIncommingEdgeName());
                        //Connect to relationVertex Senses directly (by same POSTag)
                        List<WiktionarySense> relationSenses = SemanticGraphIO.getWiktionarySenses(relation, sense.getPOSTag());
                        for (WiktionarySense relationSense : relationSenses) {
                            SemanticVertex relationSenseVertex = this.getVertex(destinationGraph, relationSense.getDescriptionText());
                            if (relationSenseVertex == null) {
                                relationSenseVertex = new SemanticVertex(SemanticSliceGraphType.SENSEGRAPH.getSliceName());
                                relationSenseVertex.setValue(relationSense.getDescriptionText());
                                relationSenseVertex.setType(relationSense.getPOSTag());
                                destinationGraph.addVertex(relationSenseVertex);
                            }
                            semanticSliceConnectionGraph.addEdge(senseVertex, relationSenseVertex, SemanticSliceGraphType.SENSEGRAPH.getIncommingEdgeName());
                        }
                    }
                }
            }
        }

        graph.addSemanticSliceConnectionGraph(semanticSliceConnectionGraph);
        return graph;
    }

    private SemanticVertex getVertex(SemanticSliceGraph<GHT, GHT> graph, String lemma) {
        for (Vertex<GHT, GHT> v : graph.getVertices()) {
            SemanticVertex semanticVertex = (SemanticVertex) v;

            if (semanticVertex.getValue().equals(lemma)) {
                return semanticVertex;
            }
        }
        return null;
    }

}
