//
//  Vertex.swift
//  graphrs
//
//  Created by Peter Janacik on 23.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

public class Vertex: GraphElement, Hashable {
    private var edges = [UInt64: Edge]()
    
    public func addEdge(e: Edge) {
        edges[e.id] = e
    }
    
    func removeEdgeOnBothSides(e: Edge) {
        e.sideA.edges.removeValueForKey(e.id)
        e.sideB.edges.removeValueForKey(e.id)
    }
    
    /**
     * Returns outgoing edges.
     *
     * @return
     */
    public func getEdges() -> [Edge]? {
        return getEdges(Direction.Forward)
    }
    
    /**
     * Returns incoming and/or outgoing edges.
     *
     * @param direction
     * @return
     */
    public func getEdges(direction: Direction) -> [Edge]? {
        let edgeCollection = Array(edges.values)
        
        if (direction == Direction.Undefined) {
            return edgeCollection
        }
        
        var outgoingEdges = [Edge]()
        
        for e in edgeCollection {
            if (e.sideA.id == self.id) {
                outgoingEdges.append(e)
            }
        }
        
        if (direction == Direction.Forward) {
            return outgoingEdges
        } else if (direction == Direction.Backward) {
            var incommingEdges = edgeCollection
            incommingEdges.removeObjectsInArray(outgoingEdges)
            
            return incommingEdges
        }
        
        print("Unknown Direction \(direction)")
        return nil
    }
    
    public func getEdge(id: UInt64) -> Edge? {
        return edges[id]
    }
    
    public func getEdgeCount() -> Int {
        return edges.count
    }
    
    public func hasOutgoingEdge() -> Bool {
        return numberOutgoingEdges() > 0
    }
    
    public func hasIncommingEdge() -> Bool {
        return numberIncomingEdges() > 0
    }
    
    public func numberOutgoingEdges() -> Int {
        var count = 0
        
        for edge in edges {
            if edge.1.getSource() == self {
                count += 1
            }
        }
        
        return count
    }
    
    public func numberIncomingEdges() -> Int {
        var count = 0
        
        for edge in edges {
            if edge.1.getTarget() == self {
                count += 1
            }
        }
        
        return count
    }
    
    public func toString() -> String {
        return "Vertex (id=\(id))"
    }
    
    /**
     * Part of the visitor design pattern -accept method.
     */
    override func accept(visitor: GraphElementVisitor) {
        visitor.visit(self)
    }
    
    public var hashValue: Int {
        return self.toString().hashValue
    }
}

public func ==(lhs: Vertex, rhs: Vertex) -> Bool {
    if (lhs.id == rhs.id && (lhs.data as! DictionaryElementData) == (rhs.data as! DictionaryElementData)) {
        return true
    }
    
    return false
}

public func !=(lhs: Vertex, rhs: Vertex) -> Bool {
    return !(lhs == rhs)
}