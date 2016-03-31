//
//  RatingAgent.swift
//  graphrs
//
//  Created by Marcus Sellmann on 31.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import Foundation

class RatingAgent: UserDependentDirectedSpreadingAgent {
    var exampleAbsoluteTimedistance: Int = 0
    var timeOfSourcevertexVisiting: Int = 0
    
    override init(vertexProcessingFacade: VertexProcessingFacade) {
        super.init(vertexProcessingFacade: vertexProcessingFacade)
    }
    
    override init(sourceId: UInt64, maxDistance: Int, setVisible: Bool, vertexProcessingFacade: VertexProcessingFacade) {
        super.init(sourceId: sourceId, maxDistance: maxDistance, setVisible: setVisible, vertexProcessingFacade: vertexProcessingFacade)
    }
    
    override func getAgentForCopy() -> DirectedSpreadingAgent {
        return RatingAgent(vertexProcessingFacade: self.v)
    }
    
    override func addValuesToAgentForCopy(agentForCopy: DirectedSpreadingAgent) {
        let returnagent = agentForCopy as! RatingAgent
        /**
         * If there are any variables from this class, which should be copied, you can add them here
         */
        returnagent.exampleAbsoluteTimedistance = self.exampleAbsoluteTimedistance
        returnagent.timeOfSourcevertexVisiting = self.timeOfSourcevertexVisiting
        
        super.addValuesToAgentForCopy(returnagent)
    }
    
    override func neuerVertexErlaubt() -> Bool {
        /**
         * There should be no need to change this.
         * BUT: in case you want to stop the agent from spreading after a certain amount of time, you should implement it in here.
         * Just do an if-statement and return "false" if you allready have reached the timelimit.
         * The call for the super-method is needed still!
         */
        /*
         if(excampleAbsoluteTimedistance>100){
         return false;
         }
         */
        return super.neuerVertexErlaubt()
    }
    
    override func modifyVertex() {
        /**
         * This is the place for the algorithm. the vertex can be changed.
         */
        /*
         GHT edgeData = (GHT) usedEdge.getData();
         if(direction.equals(Direction.OUTGOING)){
         excampleAbsoluteTimedistance += edgeData.getTable().get("startSemester").i();
         }else{
         excampleAbsoluteTimedistance += edgeData.getTable().get("endSemester").i();
         }
         */
        super.modifyVertex()
    }
    
    override func modifyInSourceVertex() {
        /**
         * If there is a need to do something in the first vertex, do it here.
         * But there should not be a need for
         */
        super.modifyInSourceVertex()
    }
    
    override func modifyAfterFirstStep() {
        /**
         * It might be helpfull to do some special tasks in the first visited vertex after the source one.
         * This is the method is called there only.
         * the "modifyVertex()" method is still called afterwards!
         */
        /*
         GHT edgeData = (GHT) usedEdge.getData();
         timeOfSourcevertexVisiting = edgeData.getTable().get("startSemester").l();
         if(direction.equals(Direction.INCOMING)){
         timeOfSourcevertexVisiting = edgeData.getTable().get("endSemester").l();
         }
         */
        
        super.modifyAfterFirstStep()
    }
}