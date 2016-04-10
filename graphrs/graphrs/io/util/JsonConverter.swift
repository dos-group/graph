//
//  JsonConverter.swift
//  graphrs
//
//  Created by Marcus Sellmann on 01.04.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

class JsonConverter {
    class func dictToJson(dict: [String : String]) -> String {
        var result = "{"
        
        for entry in dict {
            result += "\"" + entry.0 + "\":" + entry.1 + ","
        }
        
        if (result.characters.count > 1) {
            result.removeAtIndex(result.endIndex)
        }
        
        return result + "}"
    }
    
    class func arrayToJson(array: [String]) -> String {
        var result = "["
        
        for entry in array {
            result += "\"" + entry + "\","
        }
        
        if (result.characters.count > 1) {
            result.removeAtIndex(result.endIndex)
        }
        
        return result + "]"
    }
}