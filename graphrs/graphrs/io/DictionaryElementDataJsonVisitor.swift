//
//  DictionaryElementDataJsonVisitor.swift
//  graphrs
//
//  Created by Marcus Sellmann on 01.04.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

/**
 * Part of the visitor design pattern for graph data -implementing customized JSON format
 */
public class DictionaryElementDataJsonVisitor: GraphDataVisitor {
    func visit(data: GraphData) -> String? {
        do {
            let dict: AnyObject = (data as! DictionaryElementData).d as AnyObject
            let jsonData = try NSJSONSerialization.dataWithJSONObject(dict, options: NSJSONWritingOptions.PrettyPrinted)
            let jsonString: String = NSString(data: jsonData, encoding: NSUTF8StringEncoding) as! String
            
            return jsonString
        } catch let error as NSError {
            print(error)
        }
        
        return nil
    }
}