//
//  GraphElementVisitor.swift
//  graphrs
//
//  Created by Marcus Sellmann on 26.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

/**
 * Attempt to create a visitor pattern for graph serialization.
 * <br>
 * Interface for a visitor.
 *
 */
protocol GraphElementVisitor {
    /**
     * Called before graph, at the beginning
     */
    func before()
    
    func visit(graph: Graph)
    func visit(edge: Edge)
    func visit(vertex: Vertex)
    
    /**
     * Called after all elements were processed
     */
    func after()
}