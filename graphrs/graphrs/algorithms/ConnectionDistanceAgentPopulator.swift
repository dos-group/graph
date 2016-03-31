//
//  ConnectionDistanceAgentPopulator.swift
//  graphrs
//
//  Created by Marcus Sellmann on 30.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import Foundation

class ConnectionDistanceAgentPopulator: AgentPopulator {
    var queriedVertexId: UInt64
    var maxDistance: Int
    
    init(queriedVertexId: UInt64, maxDistance: Int) {
        self.queriedVertexId = queriedVertexId
        self.maxDistance = maxDistance
    }
    
    func getPopulation(vertexId: UInt64, vertexProcessingFacade: VertexProcessingFacade) -> [Agent]? {
        if (vertexId == queriedVertexId) {
            let a = createAgent(vertexId, distance: maxDistance, vertexProcessingFacade: vertexProcessingFacade)
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
    func createAgent(vertexId: UInt64, distance: Int, vertexProcessingFacade: VertexProcessingFacade) -> Agent {
        return ConnectionDistanceAgent(sourceId: vertexId, maxDistance: distance, vertexProcessingFacade: vertexProcessingFacade)
    }
}