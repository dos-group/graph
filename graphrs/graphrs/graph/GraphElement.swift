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

public class GraphElement: GraphElementVisitorAcceptor, Equatable {
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
    
    public func getDataDict() -> [String : PrimitiveData] {
        return (data as! DictionaryElementData).d
    }
    
    func accept(visitor: GraphElementVisitor) {}
}

public func ==(lhs: GraphElement, rhs: GraphElement) -> Bool {
    return lhs.id == rhs.id
}

public func !=(lhs: GraphElement, rhs: GraphElement) -> Bool {
    return !(lhs == rhs)
}