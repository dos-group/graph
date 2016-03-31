//
//  ArrayExtension.swift
//  graphrs
//
//  Created by Marcus Sellmann on 26.03.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

/**
 * Swift 2 Array Extension
 *
 * Copyright © 2015 Paul Solt. All rights reserved.
 * http://supereasyapps.com/blog/2015/9/22/how-to-remove-an-array-of-objects-from-a-swift-2-array-removeobjectsinarray
 */
extension Array where Element: Equatable {
    mutating func removeObject(object: Element) {
        if let index = self.indexOf(object) {
            self.removeAtIndex(index)
        }
    }
    
    mutating func removeObjectsInArray(array: [Element]) {
        for object in array {
            self.removeObject(object)
        }
    }
}