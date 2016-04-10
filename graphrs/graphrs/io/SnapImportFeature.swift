//
//  SnapImportFeature.swift
//  graphrs
//
//  Created by Marcus Sellmann on 01.04.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

/**
 * Interface for class implementing parsing of feature name definition
 */
protocol SnapImportFeature {
    /**
     * Returns name of the feature from the feature string
     */
    func getFeatureName(feature: String) -> String
    
    /**
     * Returns value of the feature from the feature string
     */
    func getFeatureValue(feature: String) -> String
}