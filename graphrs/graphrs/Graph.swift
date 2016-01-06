//
//  Graph.swift
//  graphrs
//
//  Created by Peter Janacik on 23.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

import Foundation
import Darwin

class Graph {
    
    private var model = GraphModel()
    
    func addVertex(data: ElementData = DictionaryElementData()) -> Vertex {
           return model.addVertex(data)
    }
    
    func addEdge(sideAId: UInt64, sideBId: UInt64, direction: Direction, data: ElementData = DictionaryElementData()) throws -> Edge {
        
        if let m = try? model.addEdge(sideAId, sideBId: sideBId, direction: direction, data: data) {return m}

        print("Exiting: unspecified edge direction")
        exit(1)
        
    }
    
    
}