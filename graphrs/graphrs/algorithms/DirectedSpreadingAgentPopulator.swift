//
//  DirectedSpreadingAgentPopulator.swift
//  graphrs
//
//  Created by Marcus Sellmann on 30.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

class DirectedSpreadingAgentPopulator: AgentPopulator {
    var queriedVertexId: UInt64 = 0
    var maxDistance: Int = 0
    var startEverywhere: Bool = false
    var settingVisibility: Bool = false
    
    init(maxDistance: Int) {
        self.maxDistance = maxDistance
        self.startEverywhere = true
    }
    
    init(queriedVertexId: UInt64, maxDistance: Int) {
        self.queriedVertexId = queriedVertexId
        self.maxDistance = maxDistance
    }
    
    init(queriedVertexId: UInt64, maxDistance: Int, settingVisibility: Bool) {
        self.queriedVertexId = queriedVertexId
        self.maxDistance = maxDistance
        self.settingVisibility = settingVisibility
    }
    
    func getPopulation(vertexId: UInt64, vertexProcessingFacade: VertexProcessingFacade) -> [Agent]? {
        if (startEverywhere || vertexId == queriedVertexId) {
            let a = createAgent(vertexId, maxDistance: maxDistance, vertexProcessingFacade: vertexProcessingFacade)
            return [a]
        } else {
            return nil
        }
    }
    
    /**
     * @param vertexId
     *            The searched vertex id
     * @param distance
     *            The maximum distance
     * @return a agent used to get the population
     */
    func createAgent(vertexId: UInt64, maxDistance: Int, vertexProcessingFacade: VertexProcessingFacade) -> Agent {
        return DirectedSpreadingAgent(sourceId: vertexId, maxDistance: maxDistance, setVisible: settingVisibility, vertexProcessingFacade: vertexProcessingFacade)
    }
}