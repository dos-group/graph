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
    var type: String?
    
    init(o: AnyObject) throws {
        switch o {
        case is Int:
            object = o
            type = "i"
            break
            
        case is Double:
            object = o
            type = "d"
            break
            
        case is UInt64:
            object = o
            type = "l"
            break
            
        case is String:
            object = o
            type = "s"
            break
            
        case is Bool:
            object = o
            type = "b"
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
        type = "i"
        object = i
    }
    
    func set(l: UInt64) {
        type = "l"
        object = NSNumber(unsignedLongLong: l)
    }
    
    func set(d: Double) {
        type = "d"
        object = d
    }
    
    func set(b: Bool) {
        type = "b"
        object = b
    }
    
    func set(s: String) {
        type = "s"
        object = s
    }
    
    func set(o: AnyObject) {
        type = "o"
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
        
        if let v: String? = String(object!) {
            return v!
        } else {
            throw PrimitiveDataError.InvalidDataTypeConversionRequest
        }
    }
    
    func o() throws -> AnyObject {
        try checkForUninitializedObject()
        return object!
    }
    
    public func toString() -> String {
        do {
            return "[\(type!), \(try self.s())]"
        } catch {
            return ""
        }
    }
    
    private func checkForUninitializedObject() throws {
        guard object != nil else {
            throw PrimitiveDataError.UninitializedPrimitiveData
        }
    }
    
    public func getDataAbbrev() -> String {
        return type!
    }
    
    public class func getDataAbbrev(primitiveData: PrimitiveData) -> String {
        return primitiveData.getDataAbbrev()
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
    let lhsType: String = PrimitiveData.getDataAbbrev(lhs)
    let rhsType: String = PrimitiveData.getDataAbbrev(rhs)
    
    if lhsType == rhsType {
        do {
            switch lhsType {
                case "b": return try lhs.b() == rhs.b()
                case "i": return try lhs.i() == rhs.i()
                case "l": return try lhs.l() == rhs.l()
                case "d": return try lhs.d() == rhs.d()
                case "s": return try lhs.s() == rhs.s()
                default: return false
            }
        } catch {
            return false
        }
    }
    
    return false
}

public func !=(lhs: PrimitiveData, rhs: PrimitiveData) -> Bool {
    return !(lhs == rhs)
}