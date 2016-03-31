//
//  Data.swift
//  graphrs
//
//  Created by Peter Janacik on 23.12.15.
//  Copyright © 2015 CITBDA. All rights reserved.
//

import Foundation

enum PrimitiveData: JsonReadable {
    
    case D(Double)
    case I(Int)
    case S(String)
    case B(Bool)
    
    func getAbbrev() -> String {
        switch self {
            case .D:
                return "d"
            case .I:
                return "i"
            case .S:
                return "s"
            case .B:
                return "b"
        }
    }
    
    func toInt() -> Int {
        return Int(self.toString())!
    }
    
    func toDouble() -> Double {
        return Double(self.toString())!
    }
    
    func toString() -> String {
        return String(self)
    }
    
    func toBool() -> Bool {
        return self.toString().toBool()!
    }
    
    func getAsJson() -> String {
        return toString()
    }
    
    func accept(visitor: GraphDataVisitor) -> AnyObject {
        return visitor.visit(self)
    }
}

func ==(lhs: PrimitiveData, rhs: PrimitiveData) -> Bool {
    guard lhs.getAbbrev() == rhs.getAbbrev() else {
        return false
    }
    
    switch lhs {
        case let .D(da):
            switch rhs {
                case let .D(db):
                    return da == db
                default:
                    return false
            }
        
        case let .I(ia):
            switch rhs {
                case let .I(ib):
                    return ia == ib
                default:
                    return false
            }
        
        case let .S(sa):
            switch rhs {
                case let .S(sb):
                    return sa == sb
                default:
                    return false
            }
        
        case let .B(ba):
            switch rhs {
                case let .B(bb):
                    return ba == bb
                default:
                    return false
            }
    }
}

func !=(lhs: PrimitiveData, rhs: PrimitiveData) -> Bool {
    return !(lhs == rhs)
}