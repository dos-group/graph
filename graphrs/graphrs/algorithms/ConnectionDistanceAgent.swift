//
//  ConnectionDistanceAgent.swift
//  graphrs
//
//  Created by Marcus Sellmann on 30.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import Foundation

class ConnectionDistanceAgent : Agent {
    var v: VertexProcessingFacade
    var sourceId: UInt64
    var distance: Int = 0
    var maxDistance: Int
    
    // -- Implementations of abstract stuff
    init(sourceId: UInt64, maxDistance: Int, vertexProcessingFacade: VertexProcessingFacade) {
        self.sourceId = sourceId
        self.maxDistance = maxDistance
        self.v = vertexProcessingFacade
    }
    
    func getCopy() -> Agent {
        let a = ConnectionDistanceAgent(sourceId: sourceId, maxDistance: maxDistance, vertexProcessingFacade: v)
        a.distance = distance
        
        return a;
    }
    
    func runBefore() {
    }
    
    func runStep() {
        if ((sourceId == v.getId()) && (v.getValue("distance") == nil)) {
            modifyDistanceAndBroadcast(0);
        } else {
            let vDistance = (v.getValue("distance")!.toInt())
            
            if (vDistance > self.distance) {
                modifyDistanceAndBroadcast(self.distance)
            }
        }
    }
    
    func runAfter() {
    }
    
    // -- Elements of algorithm
    
    func modifyDistanceAndBroadcast(distance: Int) {
        v.setValue("distance", value: PrimitiveData.I(distance))
        v.setValue("vislabel", value: PrimitiveData.S(getVisLabel()))
        self.distance += 1
        v.broadcast()
    }
    
    func getVisLabel() -> String {
        let d = v.getValue("distance")!.toInt()
        var cs = ""
        
        switch (d) {
            case 0:
                cs = ""
                break;
            case 1:
                cs = ": directly connected"
                break;
            case 2:
                cs = ": 2nd degree"
                break;
            case 3:
                cs = ": 3rd degree"
                break;
            default:
                cs = ": \(d)th degree"
        }
        
        return String(v.getValue("label")) + cs
    }
    
    func setUsedEdge(e: Edge) {
    }
}