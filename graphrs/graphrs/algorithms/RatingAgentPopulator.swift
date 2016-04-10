//
//  RatingAgentPopulator.swift
//  graphrs
//
//  Created by Marcus Sellmann on 31.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

class RatingAgentPopulator: UserDependentDirectedSpreadingAgentPopulator {
    override init(queriedVertexId: UInt64, maxDistance: Int, settingVisibility: Bool, userIDKey: String) {
        super.init(queriedVertexId: queriedVertexId, maxDistance: maxDistance, settingVisibility: settingVisibility, userIDKey: userIDKey)
    }
    
    /**
     * This is for a global start of Agents, which could easily run into heap-space problems.
     * @param maxDistance
     * @param userIDKey
     */
    override init(maxDistance: Int, userIDKey: String) {
        super.init(maxDistance: maxDistance, userIDKey: userIDKey)
    }
    
    override func createAgent(vertexId: UInt64, maxDistance: Int, vertexProcessingFacade: VertexProcessingFacade) -> Agent {
        return RatingAgent(sourceId: vertexId, maxDistance: maxDistance, setVisible: settingVisibility, vertexProcessingFacade: vertexProcessingFacade)
    }
}