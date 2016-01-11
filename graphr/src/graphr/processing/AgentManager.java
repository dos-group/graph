package graphr.processing;

import graphr.data.GHT;
import graphr.data.PrimData;
import graphr.graph.Edge;
import graphr.graph.Graph;
import graphr.graph.Vertex;
import graphr.graph.Edge.Direction;

import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Manages simulations of agents.
 * <BR>
 * <b>NOT FINISHED</b> 
 * Should be Vertex driven, i.e. run only such vertices that have some agents waiting there to run. 
 * In other words, make it "vertex driven".
 */
public class AgentManager implements VertexProcessingFacade {

	Hashtable<Long, ArrayList<Agent>> schedule;
	Graph<?, ?> graph;
	private Direction direction;

	long localVertexId;
	Hashtable<Long, ArrayList<Agent>> newSchedule;
	Agent currentlyExecutedAgent;

	public AgentManager(Graph<?, ?> graph, AgentPopulator pop) {
		this(graph, pop, Direction.OUTGOING);
	}

	public AgentManager(Graph<?,?> graph, AgentPopulator pop, Direction direction) {

		// Basic init stuff
		schedule = new Hashtable<Long, ArrayList<Agent>>();
		this.graph = graph;
		this.direction = direction;

		// Getting population for initial schedule
		for (Vertex<?, ?> v : graph.getVertices()) {
			ArrayList<Agent> al = pop.getPopulation(v.getId());
			if (al != null) {
				schedule.put(new Long(v.getId()), al);
			}
		}

	}

	/**
	 * Perform bulk step, i.e. for every vertex that has some waiting agents -run them as they "arrived".
	 * @return True if at least one agent executed otherwise false.
	 */
	public void runBulkStep() {

		newSchedule = new Hashtable<Long, ArrayList<Agent>>();

		for (Long vertexId : schedule.keySet()) {
			localVertexId = vertexId;
			ArrayList<Agent> agents = schedule.get(vertexId);
			for (Agent agent : agents) {
				currentlyExecutedAgent = agent;
				agent.setVertexProcessingFacade(this);
				agent.runStep();
			}
		}

		localVertexId = -1;
		currentlyExecutedAgent = null;

		schedule = newSchedule;
	}

	/**
	 * Run given number of bulksteps or until all agents "halt".
	 * @param numberOfBulkSteps
	 * @return Remaining number of bulk steps -if greater than 0 than agents halted. 
	 */
	public long runProcessing(long numberOfBulkSteps) {
		long currentStep = 0;

		while ((currentStep <= numberOfBulkSteps) && 
				( !((currentStep > 0) && (schedule.size() == 0)) )) {
			runBulkStep();
			currentStep++;
		}

		return numberOfBulkSteps - currentStep;
	}

	// -- implements ProcessingFacade

	@Override
	public PrimData getValue(String key) {
		GHT data = (GHT) graph.getVertex(localVertexId).getData();
		return data.getTable().get(key);
	}

	@Override
	public void setValue(String key, PrimData value) {
		GHT data = (GHT) graph.getVertex(localVertexId).getData();
		data.getTable().put(key, (PrimData) value);
	}

	public long getId() {
		return localVertexId;
	}

	@Override
	public void broadcast() {
		Vertex<?, ?> vertex = graph.getVertex(localVertexId);
		if (direction == Direction.OUTGOING || direction == Direction.BOTH) {
			for (Edge<?, ?> e : vertex.getEdges(Direction.OUTGOING)) {
				addAgent(new Long(e.getTarget().getId()), e);
			}
		}
		if (direction == Direction.INCOMING || direction == Direction.BOTH) {
			for (Edge<?, ?> e : vertex.getEdges(Direction.INCOMING)) {
				addAgent(new Long(e.getSource().getId()), e);
			}
		}
	}

	private void addAgent(final Long nextHopId, Edge e) {
		ArrayList<Agent> agentsList = newSchedule.get(nextHopId);
		if (agentsList == null) {
			agentsList = new ArrayList<Agent>();
			newSchedule.put(nextHopId, agentsList);
		}
		Agent newAgent = currentlyExecutedAgent.getCopy();
		newAgent.setUsedEdge(e);
		agentsList.add(newAgent);
	}

}
