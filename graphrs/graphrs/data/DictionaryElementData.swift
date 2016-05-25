//
//  DictionaryElementData.swift
//  graphrs
//
//  Created by Peter Janacik on 26.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

public enum DictionaryElementDataConversionError: ErrorType {
    case InvalidSourceStringFormat
    case InvalidNumberOfElementsInString
    case PossibleInvalidPrimitiveDataType
    case InvalidPrimitiveDataType
}

public class DictionaryElementData: ElementData, GraphData {
    var d = [String: PrimitiveData]()
    
    init() {}
    
    public func getData(key: String) -> PrimitiveData? {
        return d[key]
    }
    
    public func updateData(key: String, value: PrimitiveData) {
        d[key] = value
    }
    
    public func removeData(key: String) {
        d[key] = nil
    }
    
    //-- Conversions
    func fromString(data: String) throws {
        let trimmedData = data.stringByTrimmingCharactersInSet(NSCharacterSet.whitespaceAndNewlineCharacterSet())
        
        // Check that the first and last character is a curly bracket
        guard trimmedData.characters.first != nil && trimmedData.characters.last != nil &&
              trimmedData.characters.first == "{" && trimmedData.characters.last == "}" else {
            throw DictionaryElementDataConversionError.InvalidSourceStringFormat
        }
        
        // Drop the brackets
        let dataWithRemovedCapsule = String(trimmedData.characters.dropFirst().dropLast())
        
        // Split the string by cutting the commas
        let strings = dataWithRemovedCapsule.componentsSeparatedByString(",")
        
        // Check for the correct number of elements, has to be a multiple of three
        guard strings.count % 3 == 0 else {
            throw DictionaryElementDataConversionError.InvalidNumberOfElementsInString
        }
        
        var i = 0
        
        while i < strings.count {
            guard ["i", "d", "s", "b"].contains(strings[i + 1]) else {
                throw DictionaryElementDataConversionError.PossibleInvalidPrimitiveDataType
            }
            
            switch (strings[i + 1]) {
                case "i":
                    d[strings[i]] = PrimitiveData(i: Int(strings[i + 2])!)
                    break
                case "d":
                    d[strings[i]] = PrimitiveData(d: Double(strings[i + 2])!)
                    break
                case "s":
                    d[strings[i]] = PrimitiveData(s: strings[i + 2])
                    break
                case "b":
                    d[strings[i]] = PrimitiveData(b: strings[i + 2].toBool()!)
                    break
                default:
                    throw DictionaryElementDataConversionError.InvalidPrimitiveDataType
            }
            
            i += 3
        }
    }
    
    func toString() -> String {
        var s = "{"
        var remainingKeys = d.count
        
        for k in d.keys {
            s += self.primitiveDataToString(k, d: d[k]!)
            remainingKeys -= 1
            
            if (remainingKeys > 0) {
                s += ",";
            }
        }
        
        return s + "}";
    }
    
    //-- Generic Converters
    func primitiveDataToString(key: String, d: PrimitiveData) -> String {
        return "(\(key),\(PrimitiveData.getDataAbbrev(d)),\(d.toString()))"
    }
    
    func accept(visitor: GraphDataVisitor) -> String {
        return visitor.visit(self)!
    }
}

public func ==(lhs: DictionaryElementData, rhs: DictionaryElementData) -> Bool {
    guard lhs.d.count == rhs.d.count else {
        return false
    }
    
    for myEntry in lhs.d {
        let otherValue = rhs.d[myEntry.0]
        
        guard otherValue != nil && myEntry.1 == otherValue! else {
            return false
        }
    }
    
    return true
}

public func !=(lhs: DictionaryElementData, rhs: DictionaryElementData) -> Bool {
    return !(lhs == rhs)
}
