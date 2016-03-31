//
//  UserDepandantDirectedSpreadingAgent.swift
//  graphrs
//
//  Created by Marcus Sellmann on 30.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import Foundation

class UserDependentDirectedSpreadingAgent: DirectedSpreadingAgent {
    static var userIdKey: String = "userID"
    static var allreadyHandled: Set<String> = Set<String>()
    var userID: String = ""
    
    override init(vertexProcessingFacade: VertexProcessingFacade) {
        super.init(vertexProcessingFacade: vertexProcessingFacade)
    }
    
    override init(sourceId: UInt64, maxDistance: Int, setVisible: Bool, vertexProcessingFacade: VertexProcessingFacade) {
        super.init(sourceId: sourceId, maxDistance: maxDistance, setVisible: setVisible, vertexProcessingFacade: vertexProcessingFacade)
    }
    
    class func setuserIdKey(newKey: String) {
        userIdKey = newKey
    }
    
    override func shouldModifyAndBroadcast() -> Bool {
        let signaturstring: String = "\(userID);\(v.getId());\(sourceId)"
        
        if (UserDependentDirectedSpreadingAgent.allreadyHandled.contains(signaturstring)) {
            return false
        } else {
            UserDependentDirectedSpreadingAgent.allreadyHandled.insert(signaturstring)
            return super.shouldModifyAndBroadcast()
        }
    }
    
    override func getAgentForCopy() -> DirectedSpreadingAgent {
        return UserDependentDirectedSpreadingAgent(vertexProcessingFacade: self.v)
    }
    
    override func addValuesToAgentForCopy(agentForCopy: DirectedSpreadingAgent) {
        let returnagent = agentForCopy as! UserDependentDirectedSpreadingAgent
        returnagent.userID = userID
        super.addValuesToAgentForCopy(returnagent)
    }
    
    override func neuerVertexErlaubt() -> Bool {
        let edgeData = usedEdge?.data as! DictionaryElementData
        
        //edge für einen Falschen Benutzer gewählt
        if (userID.isEmpty && !(userID == edgeData.d[UserDependentDirectedSpreadingAgent.userIdKey]?.toString())) {
            return false
        }
        
        return super.neuerVertexErlaubt();
    }
    
    override func modifyVertex() {
        super.modifyVertex()
    }
    
    override func modifyInSourceVertex() {
        super.modifyInSourceVertex()
    }
    
    override func modifyAfterFirstStep() {
        if (userID.isEmpty) {
            let edgeData = usedEdge?.data as! DictionaryElementData
            userID = (edgeData.d[UserDependentDirectedSpreadingAgent.userIdKey]?.toString())!
        }
    
        super.modifyAfterFirstStep();
    }
}