//
//  Agent.swift
//  graphrs
//
//  Created by Marcus Sellmann on 25.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import Foundation

/**
 * Parent class for all graph's agents. User is expected to extend this class and implemented given methods.
 */
protocol Agent {
    var v: VertexProcessingFacade { get set }
    
    func getCopy() -> Agent
    func setUsedEdge(e: Edge)
    
    /**
     * Called before simulation starts to do some initialization. Here comes user's code.
     */
    func runBefore()
    
    /**
     * Called after simulation stops to do some clean up. Here comes user's code.
     */
    func runAfter()
    
    /**
     * Perform computation on given vertex and decide where to move in the next step
     * @param vertex On which vertex to perform computation
     * @return null if agent halts otherwise where to move next
     */
    func runStep()
    
}