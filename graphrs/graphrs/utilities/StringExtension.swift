//
//  StringExtension.swift
//  graphrs
//
//  Created by Marcus Sellmann on 25.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import Foundation

extension String {
    func toBool() -> Bool? {
        let trueValues = ["true", "yes", "t", "y", "1"]
        let falseValues = ["false", "no", "f", "n", "0"]
        
        let lowerSelf = self.lowercaseString
        
        if trueValues.contains(lowerSelf) {
            return true
        } else if falseValues.contains(lowerSelf) {
            return false
        } else {
            return nil
        }
    }
    
    /**
    Splitting the string by the given regular expression.
     Source: http://stackoverflow.com/a/25818476
    */
    func split(regex: String) -> [String] {
        do{
            let regEx = try NSRegularExpression(pattern: regex, options: NSRegularExpressionOptions())
            let stop = "{}"
            let modifiedString = regEx.stringByReplacingMatchesInString (self, options: NSMatchingOptions(), range: NSMakeRange(0, characters.count), withTemplate:stop)
            return modifiedString.componentsSeparatedByString(stop)
        } catch {
            return []
        }
    }
}