//
//  Graph.swift
//  graphrs
//
//  Created by Peter Janacik on 23.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

class Graph {
    
    private var model = GraphModel()
    
    func addVertex(vertex: Vertex) {
        model.addVertex(vertex)
    }
    
    func addVertex(data: DictionaryElementData = DictionaryElementData()) -> Vertex {
        return model.addVertex(data)
    }
    
    func addEdge(sideAId: UInt64, sideBId: UInt64, direction: Direction, data: DictionaryElementData = DictionaryElementData()) throws -> Edge {
        
        do {
            let m = try model.addEdge(sideAId, sideBId: sideBId, direction: direction, data: data)
            return m
        } catch {
            print("E: Unspecified edge direction!")
            throw ModelError.EdgeDirectionUnspecified
        }
    }
    
    func getVertices() -> [Vertex] {
        return model.getVertices()
    }
    
    func getVertex(id: UInt64) -> Vertex {
        return model.getVertex(id)!
    }
    
    func deleteVerticesWithoutCorrectProterty(propertyName: String, referenceValue: PrimitiveData) {
        model.deleteVerticesWithoutCorrectProterty(propertyName, referenceValue: referenceValue)
    }
    
    func deleteEdgesWithoutCorrectProterty(propertyName: String, referenceValue: PrimitiveData) {
        model.deleteEdgesWithoutCorrectProterty(propertyName, referenceValue: referenceValue)
    }
    
    /**
     * Part of the visitor design pattern -accept method.
     * <br>
     * Main entry method for the entire graph.
     */
    func accept(visitor: GraphElementVisitor) {
        visitor.before()
        visitor.visit(self)
        
        for v in model.getVertices() {
            v.accept(visitor)
        }
        
        visitor.after()
    }
}