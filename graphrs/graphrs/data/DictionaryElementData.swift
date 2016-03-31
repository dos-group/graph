//
//  DictionaryElementData.swift
//  graphrs
//
//  Created by Peter Janacik on 26.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

import Foundation

enum DictionaryElementDataConversionError: ErrorType {
    case InvalidSourceStringFormat
    case InvalidNumberOfElementsInString
    case PossibleInvalidPrimitiveDataType
    case InvalidPrimitiveDataType
}

class DictionaryElementData: ElementData {
    var d = [String: PrimitiveData]()
    
    func updateData(key: String, value: PrimitiveData) {
        d[key] = value
    }
    
    func removeData(key: String) {
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
                    d[strings[i]] = PrimitiveData.I(Int(strings[i + 2])!)
                    break
                case "d":
                    d[strings[i]] = PrimitiveData.D(Double(strings[i + 2])!)
                    break
                case "s":
                    d[strings[i]] = PrimitiveData.S(strings[i + 2])
                    break
                case "b":
                    d[strings[i]] = PrimitiveData.B(strings[i + 2].toBool()!)
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
        return "(\(key),\(d.getAbbrev()),\(d))"
    }
}

func ==(lhs: DictionaryElementData, rhs: DictionaryElementData) -> Bool {
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