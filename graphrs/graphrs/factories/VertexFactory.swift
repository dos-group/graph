//
//  VertexFactory.swift
//  graphrs
//
//  Created by Marcus Sellmann on 09.04.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

public class VertexFactory {
    public class func createVertex() -> Vertex {
        return self.createVertex(nil)
    }
    
    public class func createVertex(id: UInt64) -> Vertex {
        return self.createVertex(id, data: nil)
    }
    
    public class func createVertex(data: [String : AnyObject]) -> Vertex {
        let dataDict = DictionaryElementData()
        
        for d in data {
            if let primData: PrimitiveData? = PrimitiveDataFactory.createPrimitiveData(d.1) {
                dataDict.updateData(d.0, value: primData!)
            }
        }
        
        return self.createVertex(dataDict)
    }
    
    public class func createVertex(data: DictionaryElementData?) -> Vertex {
        let v: Vertex
        
        if (data != nil) {
            v = Vertex(data: data!)
        } else {
            v = Vertex()
        }
        
        return v
    }
    
    public class func createVertex(id: UInt64, data: [String : AnyObject]) -> Vertex {
        let v = self.createVertex(data)
        v.id = id
        
        return v
    }
    
    public class func createVertex(id: UInt64, data: DictionaryElementData?) -> Vertex {
        let v = self.createVertex(data)
        v.id = id
        
        return v
    }
}