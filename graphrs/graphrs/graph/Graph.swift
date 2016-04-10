//
//  Graph.swift
//  graphrs
//
//  Created by Peter Janacik on 23.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

public class Graph: GraphElementVisitorAcceptor {
    private var vertexCount: UInt64 {
        get {
            return UInt64(vertices.count)
        }
    }
    private var edgeCount: UInt64 {
        get {
            return UInt64(edges.count)
        }
    }
    
    private var vertices: [UInt64 : Vertex] = [UInt64 : Vertex]()
    private var edges: [UInt64 : Edge] = [UInt64 : Edge]()
    
    func addVertex(vertex: Vertex) {
        addVertex(vertexCount, vertex: vertex)
    }
    
    func addVertex(id: UInt64, vertex: Vertex) {
        vertices[id] = vertex
    }
    
    func addVertex(data: DictionaryElementData?) -> Vertex {
        let v = Vertex(data: data ?? DictionaryElementData())
        addVertex(v)
        return v
    }
    
    func addEdge(edge: Edge) {
        edges[edge.id] = edge
    }
    
    func addEdge(sideAId: UInt64, sideBId: UInt64, direction: Direction, data: DictionaryElementData?) throws -> Edge {
        return try addEdge(edgeCount, sideAId: sideAId, sideBId: sideBId, direction: direction, data: data)
    }
    
    func addEdge(id: UInt64, sideAId: UInt64, sideBId: UInt64, direction: Direction, data: DictionaryElementData?) throws -> Edge {
        do {
            let e = try Edge(sideA: vertices[sideAId]!, sideB: vertices[sideBId]!, direction: direction, data: data ?? DictionaryElementData())
            e.id = id
            edges[id] = e
            return e
        } catch ModelError.EdgeDirectionNotProvided {
            throw ModelError.EdgeDirectionNotProvided
        }
    }
    
    public func getVertex(id: UInt64) -> Vertex? {
        return vertices[id]
    }
    
    public func getVertices() -> [Vertex] {
        return Array(vertices.values)
    }
    
    public func getVertexDict() -> [UInt64 : Vertex] {
        return vertices
    }
    
    /**
        Gets the edge defined in the graph from either top level or vertices.
     
        @param id The id of the searched edge.
        @return The edge defined with the given id.
     */
    public func getEdgeFromAnywhere(id: UInt64) -> Edge? {
        let e1 = getEdge(id)
        let e2 = getVertexEdge(id)
        
        return e1 ?? e2
    }
    
    /**
        Gets the edges defined in the graph from top level and vertices.
     
        @return The edges defined in the graph.
     */
    public func getAllEdges() -> [Edge] {
        var result = [Edge]()
        result.appendContentsOf(getEdges())
        result.appendContentsOf(getVertexEdges())
        
        for i in 0 ..< result.count {
            for var j in i ..< result.count {
                if result[i] == result[j] {
                    result.removeAtIndex(j)
                    j -= 1
                }
            }
        }
        
        return result
    }
    
    /**
        Gets the edges defined in the graph from top level and vertices.
     
        @return The edges defined in the graph.
     */
    public func getAllEdgesDict() -> [UInt64 : Edge] {
        var result = [UInt64 : Edge]()
        let e1 = getEdgeDict()
        let e2 = getVertexEdgesDict()
        
        for entry in e2 {
            result[entry.0] = entry.1
        }
        
        for entry in e1 {
            result[entry.0] = entry.1
        }
        
        return result
    }
    
    /**
        Gets the top level edge defined in the graph.
     
        @param id The id of the searched edge.
        @return The edge defined with the given id.
    */
    public func getEdge(id: UInt64) -> Edge? {
        return edges[id]
    }
    
    /**
        Gets the top level edges defined in the graph.
     
        @return The edges defined in the graph.
    */
    public func getEdges() -> [Edge] {
        return Array(edges.values)
    }
    
    /**
        Gets the top level edges defined in the graph.
     
        @return The edges defined in the graph.
     */
    public func getEdgeDict() -> [UInt64 : Edge] {
        return edges
    }
    
    /**
        Gets the edge defined in one of the vertices.
     
        @param id The of the searched edge.
        @return The edges defined in each of the vertices.
     */
    public func getVertexEdge(id: UInt64) -> Edge? {
        for v in vertices {
            if let edge = v.1.getEdge(id) {
                return edge
            }
        }
        
        return nil
    }
    
    /**
        Gets the edges defined in each of the vertices.
     
        @return The edges defined in each of the vertices.
     */
    public func getVertexEdges() -> [Edge] {
        var result = [Edge]()
        
        for v in vertices {
            if let edges = v.1.getEdges() {
                for e in edges {
                    if !result.contains(e) {
                        result.append(e)
                    }
                }
            }
        }
        
        return result
    }
    
    /**
        Gets the edges defined in each of the vertices.
     
        @return The edges defined in each of the vertices.
     */
    public func getVertexEdgesDict() -> [UInt64 : Edge] {
        var result = [UInt64 : Edge]()
        
        for v in vertices {
            if let edges = v.1.getEdges() {
                for e in edges {
                    if result[e.id] == nil {
                        result[e.id] = e
                    }
                }
            }
        }
        
        return result
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
    
    /**
     * Part of the visitor design pattern -accept method.
     * <br>
     * Main entry method for the entire graph.
     */
    func accept(visitor: GraphElementVisitor) {
        visitor.before()
        visitor.visit(self)
        
        for v in vertices.values {
            v.accept(visitor)
        }
        
        visitor.after()
    }
}