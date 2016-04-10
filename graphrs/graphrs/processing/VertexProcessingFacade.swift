//
//  VertexProcessingFacade.swift
//  graphrs
//
//  Created by Marcus Sellmann on 25.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

protocol VertexProcessingFacade {
    //-- Getting and setting local values
    func getValue(key: String) -> PrimitiveData?
    func setValue(key: String, value: PrimitiveData)
    func getId() -> UInt64
    
    //-- Agent replication
    func broadcast()
}