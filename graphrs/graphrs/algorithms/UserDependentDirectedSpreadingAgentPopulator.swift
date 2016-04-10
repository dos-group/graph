//
//  UserDepandantDirectedSpreadingAgentPopulator.swift
//  graphrs
//
//  Created by Marcus Sellmann on 31.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

class UserDependentDirectedSpreadingAgentPopulator: DirectedSpreadingAgentPopulator {
    init(maxDistance: Int, userIDKey: String) {
        super.init(maxDistance: maxDistance)
        
        if (!userIDKey.isEmpty) {
            UserDependentDirectedSpreadingAgent.setuserIdKey(userIDKey)
        }
    }
    
    init(queriedVertexId: UInt64, maxDistance: Int, userIDKey: String) {
        super.init(queriedVertexId: queriedVertexId, maxDistance: maxDistance)
        
        if (!userIDKey.isEmpty) {
            UserDependentDirectedSpreadingAgent.setuserIdKey(userIDKey)
        }
    }
    
    init(queriedVertexId: UInt64, maxDistance: Int, settingVisibility: Bool, userIDKey: String) {
        super.init(queriedVertexId: queriedVertexId, maxDistance: maxDistance, settingVisibility: settingVisibility)
        
        if (!userIDKey.isEmpty) {
            UserDependentDirectedSpreadingAgent.setuserIdKey(userIDKey)
        }
    }
    
    override func createAgent(vertexId: UInt64, maxDistance: Int, vertexProcessingFacade: VertexProcessingFacade) -> Agent {
        return UserDependentDirectedSpreadingAgent(sourceId: vertexId, maxDistance: maxDistance, setVisible: settingVisibility, vertexProcessingFacade: vertexProcessingFacade)
    }
}