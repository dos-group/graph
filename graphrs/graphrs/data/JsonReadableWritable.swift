//
//  JsonReadableWritable.swift
//  graphrs
//
//  Created by Marcus Sellmann on 24.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import Foundation

protocol JsonReadable: GraphData {
    func getAsJson() -> String
}

protocol JsonWritable: GraphData {
    func setFromJson()
}

protocol JsonReadableWritable: JsonReadable, JsonWritable {}