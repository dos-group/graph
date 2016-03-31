//
//  AgentPopulator.swift
//  graphrs
//
//  Created by Marcus Sellmann on 25.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import Foundation

protocol AgentPopulator {
    func getPopulation(vertexId: UInt64, vertexProcessingFacade: VertexProcessingFacade) -> [Agent]?
}