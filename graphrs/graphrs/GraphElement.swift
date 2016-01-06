//
//  GraphElement.swift
//  graphrs
//
//  Created by Peter Janacik on 23.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

import Foundation

class GraphElement {
    
    let id: UInt64
    var data: ElementData
    
    init(id: UInt64) {
        self.id = id;
        data = ElementData()
    }
    
    
}