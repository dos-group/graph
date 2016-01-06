//
//  Edge.swift
//  graphrs
//
//  Created by Peter Janacik on 23.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

import Foundation

class Edge: GraphElement {
    
    var sideA: Vertex
    var sideB: Vertex
    var direction: Direction
    
    init(id: UInt64, sideA: Vertex, sideB: Vertex, direction: Direction) throws {
        
        self.direction = direction
        
        if (direction == .Bi && sideA.id > sideB.id) {
            self.sideA = sideB
            self.sideB = sideA
        } else {
            self.sideA = sideA
            self.sideB = sideB
        }
        
        super.init(id: id)
        
        if direction == .Undefined {
            throw ModelError.EdgeDirectionNotProvided
        }
        
    }
    
}