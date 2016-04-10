//
//  GraphElement.swift
//  graphrs
//
//  Created by Peter Janacik on 23.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

protocol GraphElementVisitorAcceptor {
    func accept(visitor: GraphElementVisitor)
}

public class GraphElement: GraphElementVisitorAcceptor {
    static var nextIdToIssue: UInt64 = 0
    
    var id: UInt64
    var data: ElementData
    
    init(data: DictionaryElementData? = DictionaryElementData()) {
        self.id = GraphElement.nextIdToIssue
        GraphElement.nextIdToIssue += 1
        self.data = data!
    }
    
    public func getId() -> UInt64 {
        return id
    }
    
    public func getData() -> DictionaryElementData {
        return data as! DictionaryElementData
    }
    
    func accept(visitor: GraphElementVisitor) {}
}