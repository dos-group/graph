//
//  EdgeFactory.swift
//  graphrs
//
//  Created by Marcus Sellmann on 09.04.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

public class EdgeFactory {
    public class func createEdge(source: Vertex, target: Vertex, data: DictionaryElementData?) -> Edge? {
        return self.createEdge(source, target: target, direction: .Forward, data: data)
    }
    
    public class func createEdge(source: Vertex, target: Vertex, data: [String : AnyObject]) -> Edge? {
        let dataDict = DictionaryElementData()
        
        for d in data {
            if let primData: PrimitiveData? = PrimitiveDataFactory.createPrimitiveData(d.1) {
                dataDict.updateData(d.0, value: primData!)
            }
        }
        
        return self.createEdge(source, target: target, data: dataDict)
    }
    
    public class func createEdge(source: Vertex, target: Vertex, direction: Direction, data: [String : AnyObject]) -> Edge? {
        let dataDict = DictionaryElementData()
        
        for d in data {
            if let primData: PrimitiveData? = PrimitiveDataFactory.createPrimitiveData(d.1) {
                dataDict.updateData(d.0, value: primData!)
            }
        }
        
        return self.createEdge(source, target: target, direction: direction, data: dataDict)
    }
    
    public class func createEdge(source: Vertex, target: Vertex, direction: Direction, data: DictionaryElementData?) -> Edge? {
        do {
            return try Edge(sideA: source, sideB: target, direction: direction, data: data)
        } catch {
            return nil
        }
    }
}