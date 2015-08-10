package graphr.semanticgraph.slicecreator;

import graphr.semanticgraph.graph.SemanticGraph;
import graphr.semanticgraph.graph.SemanticSliceGraph;
import graphr.semanticgraph.graph.SemanticVertex;
import graphr.data.GHT;
import graphr.semanticgraph.io.SemanticGraphIO;
import graphr.semanticgraph.SemanticSliceCreator;
import graphr.semanticgraph.graph.SemanticSliceGraphType;
import graphr.util.Tupel;
import java.util.LinkedList;
import java.util.List;

/**
 * This is a class which implements the creation of a Tokenized Semantic Slice
 * Graph. Right now, this class can just create Slices by a simple String as
 * DataObject.
 *
 * @author Florian
 */
public class TokenNLPDependencyGraphCreator implements SemanticSliceCreator {

    @Override
    public SemanticGraph createSemanticSliceGraph(SemanticGraph graph, String dataSource) {
        SemanticSliceGraph<GHT, GHT> semanticSlice = new SemanticSliceGraph<GHT, GHT>(SemanticSliceGraphType.TOKEN_WITH_NLPCLUSTER);

        String text = dataSource;
        List<Tupel<String, String>> strTokens = SemanticGraphIO.getTokens(text);

        List<SemanticVertex> tokens = new LinkedList<SemanticVertex>();
        for (Tupel<String, String> strToken : strTokens) {
            SemanticVertex tokenVertex = new SemanticVertex(SemanticSliceGraphType.TOKEN_WITH_NLPCLUSTER.getSliceName());
            tokenVertex.setValue(strToken.getEntry1());
            tokenVertex.setType(strToken.getEntry2());
            tokens.add(tokenVertex);
            semanticSlice.addVertex(tokenVertex);
        }

        for (int i = 0; i < tokens.size() - 1; i++) {
            SemanticVertex curr = tokens.get(i);
            SemanticVertex next = tokens.get(i + 1);
            semanticSlice.addEdge(curr, next, SemanticSliceGraphType.TOKEN_WITH_NLPCLUSTER.getIncommingEdgeName());
        }

        //Add stanford nlp connections
        semanticSlice = SemanticGraphIO.setAllDependencies(text, semanticSlice);
        graph.addSemanticSlice(semanticSlice);
        return graph;
    }

    @Override
    public SemanticGraph createSemanticSliceConnectionGraph(SemanticGraph graph, SemanticSliceGraph<GHT, GHT> sourceGraph, SemanticSliceGraph<GHT, GHT> destinationGraph) {
        throw new UnsupportedOperationException("Not supported."); //To change body of generated methods, choose Tools | Templates.
    }

}
