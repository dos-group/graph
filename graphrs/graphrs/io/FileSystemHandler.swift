//
//  FileSystemHandler.swift
//  graphrs
//
//  Created by Marcus Sellmann on 01.04.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

#if os(OSX)
import AppKit
#endif

public enum FileSystemHandlerErrors: ErrorType {
    case FileIsNotExisting
    case FileCantBeRead
}

public class FileSystemHandler {
    #if os(OSX)
    public class func openFileDialog() -> String? {
        let myFileDialog: NSOpenPanel = NSOpenPanel()
        myFileDialog.runModal()
        
        // Get the path to the file chosen in the NSOpenPanel
        let path = myFileDialog.URL?.path
        
        // Make sure that a path was chosen
        if (path != nil) {
            do {
                let text = try String(contentsOfFile: path!, encoding: NSUTF8StringEncoding)
                return text
            } catch {}
        }
        
        return nil
    }
    #endif
    
    public class func readFileFromBundle(fileName: String) throws -> String {
        let file = NSBundle.mainBundle().pathForResource(fileName, ofType: nil)
        let files = NSBundle.mainBundle().pathsForResourcesOfType(nil, inDirectory: nil)
        
        if file != nil {
            if let fileContent: String? = try NSString(contentsOfFile: file!, encoding: NSUTF8StringEncoding) as String {
                return fileContent!
            }
            
            throw FileSystemHandlerErrors.FileCantBeRead
        }
        
        throw FileSystemHandlerErrors.FileIsNotExisting
    }
    
    public class func searchAndReadInDocuments(fileName: String) throws -> String {
        return try searchAndRead(fileName, dir: .DocumentDirectory)
    }
    
    public class func searchAndRead(fileName: String, dir: NSSearchPathDirectory) throws -> String {
        var content = ""
        
        if let dir : NSString = NSSearchPathForDirectoriesInDomains(dir, NSSearchPathDomainMask.AllDomainsMask, true).first {
            let path = dir.stringByAppendingPathComponent(fileName);
            
            content = try NSString(contentsOfFile: path, encoding: NSUTF8StringEncoding) as String
        }
        
        return content
    }
    
    public class func write(content: String, fileName: String, dir: NSSearchPathDirectory) -> Bool {
        var succ: Bool = false
        
        if let dir: NSString = NSSearchPathForDirectoriesInDomains(dir, NSSearchPathDomainMask.AllDomainsMask, true).first {
            let path = dir.stringByAppendingPathComponent(fileName)
            
            do {
                try content.writeToFile(path, atomically: false, encoding: NSUTF8StringEncoding)
                succ = true
            } catch {}
        }
        
        return succ
    }
}