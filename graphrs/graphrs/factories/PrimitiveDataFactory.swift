//
//  PrimitiveDataFactory.swift
//  graphrs
//
//  Created by Marcus Sellmann on 09.04.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

public class PrimitiveDataFactory {
    public class func createPrimitiveData(value: Int) -> PrimitiveData {
        return PrimitiveData(i: value)
    }
    
    public class func createPrimitiveData(value: UInt64) -> PrimitiveData {
        return PrimitiveData(l: value)
    }
    
    public class func createPrimitiveData(value: Double) -> PrimitiveData {
        return PrimitiveData(d: value)
    }
    
    public class func createPrimitiveData(value: Bool) -> PrimitiveData {
        return PrimitiveData(b: value)
    }
    
    public class func createPrimitiveData(value: String) -> PrimitiveData {
        return PrimitiveData(s: value)
    }
    
    public class func createPrimitiveData(value: AnyObject) -> PrimitiveData? {
        do {
            return try PrimitiveData(o: value)
        } catch {
            return nil
        }
    }
}