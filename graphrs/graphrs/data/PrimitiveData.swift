//
//  Data.swift
//  graphrs
//
//  Created by Peter Janacik on 23.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

public enum PrimitiveDataError: ErrorType {
    case UnsupportedPrimitiveDataType
    case InvalidDataTypeConversionRequest
    case UninitializedPrimitiveData
}

public class PrimitiveData: JsonReadableWritable {
    var object: AnyObject?
    
    init(o: AnyObject) throws {
        let type = try PrimitiveData.getDataAbbrev(o)
        
        switch type {
        case "b":
            let b: Bool = o as! Bool
            set(b)
            break
        case "i":
            let i: Int = o as! Int
            set(i)
            break
        case "l":
            let l: UInt64 = o as! UInt64
            set(l)
            break
        case "d":
            let d: Double = o as! Double
            set(d)
            break
        case "s":
            let s: String = o as! String
            set(s)
            break
        default:
            throw PrimitiveDataError.UnsupportedPrimitiveDataType
        }
    }
    
    init(abbrev: String, someString: String) {
        setFromSomeString(abbrev, s: someString)
    }
    
    init(i: Int) {
        set(i)
    }
    
    init(l: UInt64) {
        set(l)
    }
    
    init(d: Double) {
        set(d)
    }
    
    init(b: Bool) {
        set(b)
    }
    
    init(s: String) {
        set(s)
    }
    
    func set(i: Int) {
        object = i
    }
    
    func set(l: UInt64) {
        object = NSNumber(unsignedLongLong: l)
    }
    
    func set(d: Double) {
        object = d
    }
    
    func set(b: Bool) {
        object = b
    }
    
    func set(s: String) {
        object = s
    }
    
    func set(o: AnyObject) {
        object = o
    }
    
    func setFromSomeString(abbrev: String, s: String) {
        switch (abbrev) {
            case "i":
                set(Int(s)!)
                break
            case "l":
                set(UInt64(s)!)
                break
            case "d":
                set(Double(s)!)
                break
            case "b":
                set(s.toBool()!)
                break
            default:
                set(s)
        }
    }
    
    public func i() throws -> Int {
        try checkForUninitializedObject()
        
        if let v = object! as? Int {
            return v
        } else {
            throw PrimitiveDataError.InvalidDataTypeConversionRequest
        }
    }
    
    public func l() throws -> UInt64 {
        try checkForUninitializedObject()
        
        if let v = UInt64(String(object!)) {
            return v
        } else {
            throw PrimitiveDataError.InvalidDataTypeConversionRequest
        }
    }
    
    public func d() throws -> Double {
        try checkForUninitializedObject()
        
        if let v = object! as? Double {
            return v
        } else {
            throw PrimitiveDataError.InvalidDataTypeConversionRequest
        }
    }
    
    public func b() throws -> Bool {
        try checkForUninitializedObject()
        
        if let v = String(object!).toBool() {
            return v
        } else {
            throw PrimitiveDataError.InvalidDataTypeConversionRequest
        }
    }
    
    public func s() throws -> String {
        try checkForUninitializedObject()
        
        if let v = object! as? String {
            return v
        } else {
            throw PrimitiveDataError.InvalidDataTypeConversionRequest
        }
    }
    
    func o() throws -> AnyObject {
        try checkForUninitializedObject()
        return object!
    }
    
    func toString() -> String {
        do {
            return try self.s()
        } catch {
            return ""
        }
    }
    
    private func checkForUninitializedObject() throws {
        guard object != nil else {
            throw PrimitiveDataError.UninitializedPrimitiveData
        }
    }
    
    public func getDataAbbrev(o: AnyObject) throws -> String {
        return try PrimitiveData.getDataAbbrev(self)
    }
    
    public class func getDataAbbrev(o: AnyObject) throws -> String {
        if (o is Int) { return "i" }
        if (o is UInt64) { return "l" }
        if (o is Double) { return "d" }
        if (o is Bool) { return "b" }
        if (o is String) { return "s" }
        
        throw PrimitiveDataError.UnsupportedPrimitiveDataType
    }
    
    func accept(visitor: GraphDataVisitor) -> String {
        return visitor.visit(self)!
    }
    
    func getAsJson() -> String {
        return toString()
    }
    
    func setFromJson(json: String) {
    }
}

public func ==(lhs: PrimitiveData, rhs: PrimitiveData) -> Bool {
    do {
        let lhsType: String = try PrimitiveData.getDataAbbrev(lhs)
        let rhsType: String = try PrimitiveData.getDataAbbrev(lhs)
        
        guard lhsType == rhsType && lhs == rhs else {
            return false
        }
        
        return true
    } catch {
        return false
    }
}

public func !=(lhs: PrimitiveData, rhs: PrimitiveData) -> Bool {
    return !(lhs == rhs)
}