//
//  GraphModel.swift
//  graphrs
//
//  Created by Peter Janacik on 26.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

import Foundation

class GraphModel {
    
    private var vertexCount:UInt64 = 0
    private var edgeCount:UInt64 = 0
    
    private var vertices = [UInt64: Vertex]()
    private var edges = [UInt64: Edge]()
    
    func addVertex(data: ElementData = DictionaryElementData()) -> Vertex {
        
        let newId = vertexCount++
        let v = Vertex(id: newId)
        vertices[newId] = v
        return v
        
    }
    
    func addEdge(sideAId: UInt64, sideBId: UInt64, direction: Direction, data: ElementData = DictionaryElementData()) throws -> Edge {
        
        do {
            let newId = edgeCount++
            let e = try Edge(id: newId, sideA: vertices[sideAId]!, sideB: vertices[sideBId]!, direction: direction)
            edges[newId] = e
            return e
        } catch ModelError.EdgeDirectionNotProvided {
            edgeCount--
            throw ModelError.EdgeDirectionNotProvided
        }
        
    }
    
}