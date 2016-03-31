//
//  BasicGraphWithDataTest.swift
//  graphrs
//
//  Created by Marcus Sellmann on 31.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import XCTest
@testable import graphrs

class BasicGraphWithDataTest: XCTestCase {
    var graph: Graph?
    var populator: AgentPopulator?
    var manager: AgentManager?
    
    override func setUp() {
        super.setUp()
        
        graph = Graph()
        
        let dict1 = DictionaryElementData()
        dict1.updateData("label", value: PrimitiveData.S("Anna"))
        dict1.updateData("name", value: PrimitiveData.S("Anna"))
        dict1.updateData("age", value: PrimitiveData.I(24))
        
        let dict2 = DictionaryElementData()
        dict2.updateData("label", value: PrimitiveData.S("Frank"))
        dict2.updateData("name", value: PrimitiveData.S("Martin"))
        dict2.updateData("age", value: PrimitiveData.I(28))
        dict2.updateData("income", value: PrimitiveData.D(24.7))
        
        let dict3 = DictionaryElementData()
        dict3.updateData("label", value: PrimitiveData.S("Josh"))
        
        let dict4 = DictionaryElementData()
        dict4.updateData("label", value: PrimitiveData.S("Nilay"))
        
        let dict5 = DictionaryElementData()
        dict5.updateData("label", value: PrimitiveData.S("Lisa"))
        
        let v1 = graph!.addVertex(dict1)
        let v2 = graph!.addVertex(dict2)
        let v3 = graph!.addVertex(dict3)
        let v4 = graph!.addVertex(dict4)
        let v5 = graph!.addVertex(dict5)
        
        do {
            let e11: Edge? = try Edge(sideA: v1, sideB: v1, direction: .Forward, data: DictionaryElementData())
            let e12: Edge? = try Edge(sideA: v1, sideB: v2, direction: .Bi, data: DictionaryElementData())
            let e24: Edge? = try Edge(sideA: v2, sideB: v4, direction: .Forward, data: DictionaryElementData())
            let e23: Edge? = try Edge(sideA: v2, sideB: v3, direction: .Forward, data: DictionaryElementData())
            let e34: Edge? = try Edge(sideA: v3, sideB: v4, direction: .Backward, data: DictionaryElementData())
            let e35: Edge? = try Edge(sideA: v3, sideB: v5, direction: .Forward, data: DictionaryElementData())
            let e45: Edge? = try Edge(sideA: v4, sideB: v5, direction: .Forward, data: DictionaryElementData())
            
            v1.addEdge(e11!)
            
            v1.addEdge(e12!)
            v2.addEdge(e12!)
            
            v2.addEdge(e24!)
            v4.addEdge(e24!)
            
            v2.addEdge(e23!)
            v3.addEdge(e23!)
            
            v3.addEdge(e34!)
            v4.addEdge(e34!)
            
            v3.addEdge(e35!)
            v5.addEdge(e35!)
            
            v4.addEdge(e45!)
            v5.addEdge(e45!)
        } catch {
            exit(1)
        }
    }
    
    override func tearDown() {
        graph = nil
        super.tearDown()
    }
    
    func testBasicGraphWithData() {
        //populator = UserDepandantDirectedSpreadingAgentPopulator(775, 500, true,  "userID")
        //populator = ModuleAddTimedistanceAgentPopulator(30133, 500, true,  "user")
        populator = RatingAgentPopulator(queriedVertexId: 30133, maxDistance: 500, settingVisibility: true, userIDKey: "user")
        manager = AgentManager(graph: graph!, pop: populator!, direction: Direction.Bi)
        manager!.runProcessing(500)
        
        //Nur bei gerateten sinnvoll
        //graph.handleRating(2.5)
        
        print("Startin stuff")
        
        //graph!.deleteVerticesWithoutCorrectProterty("visible" , PrimitiveData.B(true))
        //graph!.deleteEdgesWithoutCorrectProterty("visible" , PrimitiveData.B(true))
        //graph!.createJsonString("target/ausgabe.json")
        
        print("Calculation has finished.")
    }
}