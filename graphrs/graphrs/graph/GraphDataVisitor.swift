//
//  GraphDataVisitor.swift
//  graphrs
//
//  Created by Marcus Sellmann on 24.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

protocol GraphDataVisitor {
    func visit(data: GraphData) -> String?
}