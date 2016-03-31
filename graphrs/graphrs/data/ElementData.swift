//
//  ElementData.swift
//  graphrs
//
//  Created by Peter Janacik on 26.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

import Foundation

protocol ElementData {
    func updateData(key: String, value: PrimitiveData)
    func toString() -> String
}