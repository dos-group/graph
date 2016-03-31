//
//  GraphElement.swift
//  graphrs
//
//  Created by Peter Janacik on 23.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

import Foundation

protocol GraphElementVisitorAcceptor {
    func accept(visitor: GraphElementVisitor)
}

class GraphElement: GraphElementVisitorAcceptor {
    static var nextIdToIssue: UInt64 = 0
    
    let id: UInt64
    var data: ElementData
    
    init(data: DictionaryElementData? = DictionaryElementData()) {
        self.id = GraphElement.nextIdToIssue
        GraphElement.nextIdToIssue += 1
        self.data = data!
    }
    
    func accept(visitor: GraphElementVisitor) {}
}