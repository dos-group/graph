//
//  JsonReadableWritable.swift
//  graphrs
//
//  Created by Marcus Sellmann on 24.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

protocol JsonReadable: GraphData {
    func setFromJson(json: String)
}

protocol JsonWritable: GraphData {
    func getAsJson() -> String
}

protocol JsonReadableWritable: JsonReadable, JsonWritable {}