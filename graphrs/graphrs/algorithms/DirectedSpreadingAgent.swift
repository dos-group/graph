//
//  DirectedSpreadingAgent.swift
//  graphrs
//
//  Created by Marcus Sellmann on 30.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import Foundation

class DirectedSpreadingAgent: Agent {
    var v: VertexProcessingFacade
    var usedEdge: Edge? = nil
    var sourceId: UInt64 = 0
    var lastVertexId: UInt64 = 0
    var distance: Int = 0
    var maxDistance: Int = 0
    var setVisible: Bool = false
    var direction = Direction.Bi
    var fistStepDone: Bool = false
    
    init(sourceId: UInt64, maxDistance: Int, setVisible: Bool, vertexProcessingFacade: VertexProcessingFacade) {
        self.sourceId = sourceId
        self.maxDistance = maxDistance
        self.setVisible = setVisible
        self.v = vertexProcessingFacade
    }
    
    init(vertexProcessingFacade: VertexProcessingFacade) {
        self.v = vertexProcessingFacade
    }
    
    func getAgentForCopy() -> DirectedSpreadingAgent {
        return DirectedSpreadingAgent(vertexProcessingFacade: self.v)
    }
    
    func addValuesToAgentForCopy(agentForCopy: DirectedSpreadingAgent){
        agentForCopy.sourceId = sourceId
        agentForCopy.maxDistance = maxDistance
        agentForCopy.usedEdge = usedEdge
        agentForCopy.lastVertexId = lastVertexId
        agentForCopy.distance = distance
        agentForCopy.direction = direction
        agentForCopy.setVisible = setVisible
    }
    
    func getCopy() -> Agent {
        let returnAgent = getAgentForCopy()
        addValuesToAgentForCopy(returnAgent)
        return returnAgent
    }
    
    func setUsedEdge(e: Edge) {
        usedEdge = e
        // TODO Validate
    }
    
    func runBefore() {
    }
    
    func runStep() {
        if (distance <= maxDistance) { //an sonsten macht man keine Aktion mehr!
            distance += 1
            if (usedEdge == nil) { //direkt nach dem Initialisieren
                modifyInSourceVertex()
                lastVertexId = v.getId()
                v.broadcast()
                return
            } else { //nicht die Initialisierung
                if (!neuerVertexErlaubt()) {
                    return
                }
                    
                //Hier beginnt die wirkliche Bearbeitung
                modifyEdge()
                
                if (self.direction == Direction.Bi) {
                    if (usedEdge!.sideB.id == sourceId) {
                        self.direction = Direction.Backward
                    } else {
                        self.direction = Direction.Forward
                    }
                }
                
                if (!fistStepDone) {
                    fistStepDone = true
                    modifyAfterFirstStep()
                }
        
                //Hier bitte black magic einfügen!
                if (shouldModifyAndBroadcast()) {
                    modifyVertex()
                    lastVertexId = v.getId()
                    v.broadcast()
                }
            }
        }
    }
    
    func runAfter() {
    }
    
    func shouldModifyAndBroadcast() -> Bool {
        return true
    }
    
    /**
     * Hier werden Dinge gesetzt, die nach dem ersten Schritt wichtig sind.
     * Bei verschiedenen Usern würde hier der User ausgewählt werden, die Sourcetime gesetzt usw
     */
    func modifyAfterFirstStep() {
    }
    
    func modifyEdge() {
        if (setVisible) {
            let edgeData = usedEdge?.data as! DictionaryElementData
            edgeData.updateData("visible", value: PrimitiveData.B(true))
        }
    }
    
    func modifyVertex() {
        if (setVisible) {
            v.setValue("visible", value: PrimitiveData.B(true))
        }
    }
    
    func modifyInSourceVertex(){
        if (setVisible) {
            v.setValue("visible", value: PrimitiveData.B(true))
        }
    }
    
    /**
     * Nicht erlaubt ist:
     * 1. Benutzung eines falschen Users
     * 2. bei Incomming Richtung nicht den letzten besuchten Vertex als Ziel zu haben
     * 3. bei outgoing Richtung nicht den letzten besuchten Vertex als Quelle zu haben
     * @return
     */
    func neuerVertexErlaubt() -> Bool {
        //edge ist in die falsche Richtung unterwegs
        if (direction == Direction.Backward && lastVertexId != usedEdge?.sideB.id) {
            return false
        }
    
        //edge ist in die falsche Richtung unterwegs
        if (direction == Direction.Forward && lastVertexId != usedEdge?.sideA.id) {
            return false
        }
    
        return true
    }
}