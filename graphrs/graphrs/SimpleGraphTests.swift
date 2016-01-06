//
//  SimpleGraphTests.swift
//  graphrs
//
//  Created by Peter Janacik on 26.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

import Foundation

class SimpleGraphTests {
    
    func createAndTestBasicGraph() {
        
        let g = Graph()
        
        let v1 = g.addVertex()
        let v2 = g.addVertex()
        let v3 = g.addVertex()
        let v4 = g.addVertex()
        
        let e1 = g.addEdge(v1.id, sideBId: v2.id, direction: Direction.Forward)
        let e2 = g.addEdge(v1.id, sideBId: v3.id, direction: Direction.Forward)
        let e3 = g.addEdge(v3.id, sideBId: v4.id, direction: Direction.Forward)
        let e4 = g.addEdge(v3.id, sideBId: v1.id, direction: Direction.Backward)
        
        if let d = e1.data
        
    }
    
}