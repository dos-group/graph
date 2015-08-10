package graphr.semanticgraph;

import graphr.semanticgraph.graph.SemanticSliceConnectionConfig;
import graphr.semanticgraph.graph.SemanticSliceGraphType;
import graphr.semanticgraph.graph.SemanticGraphConfig;
import graphr.semanticgraph.graph.SemanticGraph;
import graphr.semanticgraph.graph.SemanticSliceGraph;

import java.util.List;

/**
 * This class generates or extends the hole semantic graph.
 *
 * @author Florian
 */
public class SemanticGraphGenerator {

    private static SemanticGraphGenerator instance;

    private SemanticGraphGenerator() {
    }

    public static SemanticGraphGenerator getInstance() {
        if (instance == null) {
            instance = new SemanticGraphGenerator();
        }
        return instance;
    }

    /**
     *
     * @param config
     * @return
     */
    public SemanticGraph createSemanticGraph(SemanticGraphConfig config) {

        SemanticGraph graph = new SemanticGraph(config);
        List<SemanticSliceConnectionConfig> connections = config.getSemanticSliceConnections();

        for (SemanticSliceConnectionConfig connection : connections) {
            this.createConnection(graph, connection);
        }

        return graph;
    }

    private SemanticGraph createConnection(SemanticGraph graph, SemanticSliceConnectionConfig connection) {
        SemanticSliceCreator creator = SemanticSliceCreatorFactory.getInstance().getSemanticSliceCreator(connection.getDestinationGraphType());

        if (connection.getSourceGraphType() == null && connection.getDataSourceObject() != null) { //Use DataObject as Source
            graph = creator.createSemanticSliceGraph(graph, connection.getDataSourceObject());
        } else if (connection.getSourceGraphType() != null && connection.getDataSourceObject() == null) { //Use Source Graph as Source
            SemanticSliceGraph sourceGraph = null;
            if (graph.isSliceAlreadyCreated(connection.getSourceGraphType())) {
                sourceGraph = graph.getSemanticSlice(connection.getSourceGraphType());
            } else {
                return graph;
            }

            //Check if destination slice was already created, if yes: get it, else: create and connect with source slice
            if (graph.isSliceAlreadyCreated(connection.getDestinationGraphType())) {
                SemanticSliceGraph destinationGraph = graph.getSemanticSlice(connection.getSourceGraphType());
                graph = creator.createSemanticSliceConnectionGraph(graph, sourceGraph, destinationGraph);
            } else {
                //DestinationGraph and ConnectionGraph creation by SourceGraph
                graph = creator.createSemanticSliceConnectionGraph(graph, sourceGraph, null);
            }
        } else {
            //This never should happen: Source Graph and Source DataObject are null: graoh is not generated.
        }
        return graph;
    }

    public SemanticGraph extendSemanticGraph(SemanticGraph graph, SemanticSliceGraphType src, SemanticSliceGraphType dest) {
        if (graph != null) {
            SemanticSliceConnectionConfig connection = new SemanticSliceConnectionConfig(src, dest);
            graph.getConfig().addConnection(connection);
            return this.createConnection(graph, connection);
        }
        return null;
    }

    public SemanticGraph extendSemanticGraph(SemanticGraph graph, String srcData, SemanticSliceGraphType dest) {
        if (graph != null) {
            SemanticSliceConnectionConfig connection = new SemanticSliceConnectionConfig(srcData, dest);
            graph.getConfig().addConnection(connection);
            return this.createConnection(graph, connection);
        }
        return null;
    }

}
