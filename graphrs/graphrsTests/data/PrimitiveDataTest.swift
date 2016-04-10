//
//  PrimitiveDataTest.swift
//  graphrs
//
//  Created by Marcus Sellmann on 05.04.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

import XCTest
@testable import graphrs

class PrimitiveDataTest: XCTestCase {
    override func setUp() {
        super.setUp()
    }
    
    override func tearDown() {
        super.tearDown()
    }
    
    func testPrimitiveDataEmptyObject() {
        let testObject = "String" as AnyObject
        var result = ""
        
        do {
            result = try PrimitiveData.getDataAbbrev(testObject)
        } catch {}
        
        XCTAssert(result == "s")
    }
}