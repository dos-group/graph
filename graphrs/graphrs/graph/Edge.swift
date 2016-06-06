//
//  Edge.swift
//  graphrs
//
//  Created by Peter Janacik on 23.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

public class Edge: GraphElement, Equatable, Hashable {
    var sideA: Vertex
    var sideB: Vertex
    var direction: Direction
    
    init(sideA: Vertex, sideB: Vertex, direction: Direction, data: DictionaryElementData?) throws {
        self.direction = direction
        
        if (direction == .Bi && sideA.id > sideB.id) {
            self.sideA = sideB
            self.sideB = sideA
        } else {
            self.sideA = sideA
            self.sideB = sideB
        }
        
        super.init(data: data ?? DictionaryElementData())
        
        if direction == .Undefined {
            throw ModelError.EdgeDirectionNotProvided
        }
    }
    
    public func getSource() -> Vertex {
        if direction == .Backward {
            return sideB
        }
        
        return sideA
    }
    
    public func getTarget() -> Vertex {
        if direction == .Backward {
            return sideA
        }
        
        return sideB
    }
    
    public func getDirection() -> Direction {
        return direction
    }
    
    override func accept(visitor: GraphElementVisitor) {
        visitor.visit(self)
    }
    
    public func toString() -> String {
        return "Edge (id=\(id), data=\(data.toString()), src=\(sideA.id), target=\(sideB.id))"
    }
    
    public var hashValue: Int {
        return self.toString().hashValue
    }
}

public func ==(lhs: Edge, rhs: Edge) -> Bool {
    if (lhs.sideA == rhs.sideA &&
        lhs.sideB == rhs.sideB &&
        lhs.direction == rhs.direction) {
        return true
    }
    
    return false
}

public func !=(lhs: Edge, rhs: Edge) -> Bool {
    return !(lhs == rhs)
}