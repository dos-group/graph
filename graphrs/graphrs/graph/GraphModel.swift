//
//  GraphModel.swift
//  graphrs
//
//  Created by Peter Janacik on 26.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

import Foundation

class GraphModel {
    private var vertexCount: UInt64 = 0
    private var edgeCount: UInt64 = 0
    
    private var vertices = [UInt64 : Vertex]()
    private var edges = [UInt64 : Edge]()
    
    func addVertex(vertex: Vertex) {
        vertexCount += 1
        vertices[vertexCount] = vertex
    }
    
    func addVertex(data: DictionaryElementData = DictionaryElementData()) -> Vertex {
        let v = Vertex(data: data)
        addVertex(v)
        return v
    }
    
    func addEdge(sideAId: UInt64, sideBId: UInt64, direction: Direction, data: DictionaryElementData = DictionaryElementData()) throws -> Edge {
        do {
            edgeCount += 1
            let e = try Edge(sideA: vertices[sideAId]!, sideB: vertices[sideBId]!, direction: direction, data: data)
            edges[edgeCount] = e
            return e
        } catch ModelError.EdgeDirectionNotProvided {
            edgeCount -= 1
            throw ModelError.EdgeDirectionNotProvided
        }
        
    }
    
    func getVertex(id: UInt64) -> Vertex? {
        return vertices[id]
    }
    
    func getVertices() -> [Vertex] {
        return Array(vertices.values)
    }
    
    func getVertexDict() -> [UInt64 : Vertex] {
        return vertices
    }
    
    func getEdge(id: UInt64) -> Edge? {
        return edges[id]
    }
    
    func getEdges() -> [Edge] {
        return Array(edges.values)
    }
    
    func getEdgeDict() -> [UInt64 : Edge] {
        return edges
    }
    
    func deleteVerticesWithoutCorrectProterty(propertyName: String, referenceValue: PrimitiveData) {
        var deletables = [UInt64]()
        
        for v in vertices.values {
            let property = (v.data as! DictionaryElementData).d[propertyName]
            
            if (property == nil || property! != referenceValue) {
                deletables.append(v.id)
            }
        }
        
        for id in deletables {
            vertices.removeValueForKey(id)
        }
    }
    
    func deletePropertyFromVertices(propertyName: String) {
        for v in vertices.values {
            (v.data as! DictionaryElementData).removeData(propertyName)
        }
    }
    
    func deleteEdgesWithoutCorrectProterty(propertyName: String, referenceValue: PrimitiveData) {
        var deletables = [UInt64]()
        
        for e in edges.values {
            let property = (e.data as! DictionaryElementData).d[propertyName]
            
            if (property == nil || property! != referenceValue) {
                deletables.append(e.id)
            }
        }
        
        for id in deletables {
            edges.removeValueForKey(id)
        }
    }
    
    func deletePropertyFromEdges(propertyName: String) {
        for e in edges {
            (e.1.data as! DictionaryElementData).removeData(propertyName)
        }
    }
}