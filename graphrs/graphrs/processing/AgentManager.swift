//
//  AgentManager.swift
//  graphrs
//
//  Created by Marcus Sellmann on 25.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

/**
 * Manages simulations of agents.
 * <BR>
 * <b>NOT FINISHED</b>
 * Should be Vertex driven, i.e. run only such vertices that have some agents waiting there to run.
 * In other words, make it "vertex driven".
 */
class AgentManager: VertexProcessingFacade {
    var schedule = [UInt64 : [Agent]]()
    var graph: Graph
    var direction: Direction
    
    var localVertexId: UInt64 = 0
    var newSchedule = [UInt64 : [Agent]]()
    var currentlyExecutedAgent: Agent?
    
    convenience init(graph: Graph, pop: AgentPopulator) {
        self.init(graph: graph, pop: pop, direction: Direction.Forward)
    }
    
    init(graph: Graph, pop: AgentPopulator, direction: Direction) {
        // Basic init stuff
        schedule = [UInt64 : [Agent]]()
        self.graph = graph;
        self.direction = direction;
        
        // Getting population for initial schedule
        for v in graph.getVertices() {
            let al = pop.getPopulation(v.id, vertexProcessingFacade: self)
            if (al!.count > 0) {
                schedule[v.id] = al
            }
        }
    }
    
    /**
     * Perform bulk step, i.e. for every vertex that has some waiting agents -run them as they "arrived".
     * @return True if at least one agent executed otherwise false.
     */
    func runBulkStep() {
        newSchedule = [UInt64 : [Agent]]()
        
        for vertexId in schedule.keys {
            localVertexId = vertexId
            let agents = schedule[vertexId]
            for var agent in agents! {
                currentlyExecutedAgent = agent
                agent.v = self
                agent.runStep()
            }
        }
        
        localVertexId = 0;
        currentlyExecutedAgent = nil;
        schedule = newSchedule;
    }
    
    /**
     * Run given number of bulksteps or until all agents "halt".
     * @param numberOfBulkSteps
     * @return Remaining number of bulk steps -if greater than 0 than agents halted.
     */
    func runProcessing(numberOfBulkSteps: UInt64) -> UInt64 {
        var currentStep: UInt64 = 0
        
        while((currentStep <= numberOfBulkSteps) &&
             (!((currentStep > 0) && (schedule.count == 0)) )) {
            runBulkStep()
            currentStep += 1
        }
        
        return numberOfBulkSteps - currentStep;
    }
    
    // -- implements ProcessingFacade
    
    func getValue(key: String) -> PrimitiveData? {
        let data: DictionaryElementData = graph.getVertex(localVertexId).data as! DictionaryElementData
        return data.d[key]
    }
    
    func setValue(key: String, value: PrimitiveData) {
        let data: DictionaryElementData = graph.getVertex(localVertexId).data as! DictionaryElementData
        data.d[key] = value
    }
    
    func getId() -> UInt64 {
        return localVertexId;
    }
    
    func broadcast() {
        let vertex = graph.getVertex(localVertexId)
        
        if (direction == Direction.Forward || direction == Direction.Bi) {
            for e in vertex.getEdges(Direction.Forward)! {
                addAgent(e.sideB.id, e: e)
            }
        }
        
        if (direction == Direction.Backward || direction == Direction.Bi) {
            for e in vertex.getEdges(Direction.Backward)! {
                addAgent(e.sideA.id, e: e)
            }
        }
    }
    
    func addAgent(nextHopId: UInt64, e: Edge) {
        var agentsList = newSchedule[nextHopId];
        
        if (agentsList == nil) {
            agentsList = [Agent]()
            newSchedule[nextHopId] = agentsList
        }
        
        let newAgent = currentlyExecutedAgent!.getCopy();
        newAgent.setUsedEdge(e);
        agentsList!.append(newAgent)
    }
}