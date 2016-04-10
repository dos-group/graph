//
//  Edge.swift
//  graphrs
//
//  Created by Peter Janacik on 23.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

class Edge: GraphElement, Equatable {
    var sideA: Vertex
    var sideB: Vertex
    var direction: Direction
    
    init(sideA: Vertex, sideB: Vertex, direction: Direction, data: DictionaryElementData? = DictionaryElementData()) throws {
        self.direction = direction
        
        if (direction == .Bi && sideA.id > sideB.id) {
            self.sideA = sideB
            self.sideB = sideA
        } else {
            self.sideA = sideA
            self.sideB = sideB
        }
        
        super.init(data: data!)
        
        if direction == .Undefined {
            throw ModelError.EdgeDirectionNotProvided
        }
    }
    
    override func accept(visitor: GraphElementVisitor) {
        visitor.visit(self)
    }
    
    func toString() -> String {
        return "Edge (id=\(id), data=\(data.toString()), src=\(sideA.id), target=\(sideB.id))"
    }
}

func ==(lhs: Edge, rhs: Edge) -> Bool {
    if (lhs.sideA == rhs.sideA &&
        lhs.sideB == rhs.sideB &&
        lhs.direction == rhs.direction) {
        return true
    }
    
    return false
}