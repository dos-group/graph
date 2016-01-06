//
//  DictionaryElementData.swift
//  graphrs
//
//  Created by Peter Janacik on 26.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

import Foundation

class DictionaryElementData: ElementData {

    var d = [String: PrimitiveData]()
    
    override func updateData(key: String, value: PrimitiveData) {
        d[key] = value
    }
    
}