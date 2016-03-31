//
//  GraphData.swift
//  graphrs
//
//  Created by Marcus Sellmann on 24.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import Foundation

protocol GraphData {
    /**
     * Part of the visitor design pattern -all graph data must implement accept method
     * @param visitor Reference to a particular visitor
     * @return Object that is serializable
     */
    func accept(visitor: GraphDataVisitor) -> AnyObject
}