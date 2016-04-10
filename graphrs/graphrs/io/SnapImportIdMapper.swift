//
//  SnapImportIdMapper.swift
//  graphrs
//
//  Created by Marcus Sellmann on 01.04.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

/**
 * Interface for all context classes providing mapping of IDs into our own IDs
 *
 */
protocol SnapImportIdMapper {
    /**
     * For given raw ID in the String format find and return our valid ID
     * @param rawId
     * @return
     */
    func getId(rawId: String) -> UInt64
}