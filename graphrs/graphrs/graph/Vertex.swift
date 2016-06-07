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
        
        var incommingEdges = [Edge]()
        var outgoingEdges = [Edge]()
        var bidirectEdges = [Edge]()
        
        for e in edgeCollection {
            switch e.direction {
                case .Forward:
                    outgoingEdges.append(e)
                    break
                
                case .Backward:
                    incommingEdges.append(e)
                    break
                    
                case .Bi:
                    bidirectEdges.append(e)
                    break
                    
                default:
                    break
            }
        }
        
        switch direction {
            case .Forward:
                return outgoingEdges
                
            case .Backward:
                return incommingEdges
                
            case .Bi:
                return bidirectEdges
                
            default:
                break
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
        return getOutgoingEdges().count
    }
    
    public func numberIncomingEdges() -> Int {
        return getIncomingEdges().count
    }
    
    public func getOutgoingEdges() -> [Edge] {
        return getEdges(.Forward) ?? [Edge]()
    }
    
    public func getIncomingEdges() -> [Edge] {
        return getEdges(.Backward) ?? [Edge]()
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