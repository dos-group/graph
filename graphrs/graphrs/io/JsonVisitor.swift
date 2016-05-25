//
//  JsonVisitor.swift
//  graphrs
//
//  Created by Marcus Sellmann on 01.04.16.
//  Copyright © 2016 CITBDA. All rights reserved.
//

public enum JsonVisitorError: ErrorType {
    case GraphExpectedError
    case VertexExpectedError
    case EdgeExpectedError
    case GraphConversionError
}

/**
 * Attempt to create a visitor pattern for graph serialization. <br>
 * Visitor that serializes/desirializes a graph into customized JSON output of
 * the following format:
 *
 * <pre>
 * {
 *  "vertices": [
 * 	 {
 * 	  "type" : "Vertex"
 * 	  "id" : id
 *   "data" : { "key1" : "value1", "key2" : "value2", ...}
 *   "edges" : [{ "type": "Edge", "id" : id, "target" : targetId, "data": {same as vertex} }, {}, ...]
 * 	 },
 *  {...}
 *  ],
 *  "type" : graph
 * }
 * </pre>
 *
 * Assuming: https://github.com/douglascrockford/JSON-java
 *
 *
 *
 */
public class JsonVisitor: GraphElementVisitor {
    private static let EDGE_TARGET_KEY: String = "JSonVisitor_Edge_target"
    private static let EDGE_SOURCE_KEY: String = "JSonVisitor_Edge_source"
    
    var root = [String : String]()
    var verticesForJson = [String]()
    var dataVisitor: GraphDataVisitor? = nil
    
    func before() {
        dataVisitor = DictionaryElementDataJsonVisitor()
    }
    
    func visit(graph: Graph) {
        root["type"] = "Graph"
    }
    
    func visit(vertex: Vertex) {
        var vertexProp = [String : String]()
        
        vertexProp["type"] = "Vertex"
        vertexProp["id"] = String(vertex.id)
        vertexProp["data"] = (vertex.data as! DictionaryElementData).accept(dataVisitor!)
        
        // get JSON array for edges
        var edgesForJson = [String]()
        
        for e in vertex.getEdges()! {
            //create only outgoing edges
            if e.sideA.id == vertex.id {
                var edgeProp = [String : String]()
                edgeProp["type"] = "Edge"
                edgeProp["id"] = String(e.id)
                edgeProp["data"] = (e.data as! DictionaryElementData).accept(dataVisitor!)
                edgeProp["target"] = String(e.sideB.id)
                edgesForJson.append(JsonConverter.dictToJson(edgeProp))
            }
        }
        
        vertexProp["edges"] = JsonConverter.arrayToJson(edgesForJson)
        
        // add serialized vertex into the list of vertices
        verticesForJson.append(JsonConverter.dictToJson(vertexProp))
    }
    
    func visit(edge: Edge) {
        // do nothing
    }
    
    func after() {
        root["vertices"] = JsonConverter.arrayToJson(verticesForJson)
    }
    
    func getJsonString() -> String {
        return JsonConverter.dictToJson(root)
    }
    
    /**
     * Experimental method to parse graph in JSON. Initialization of edges is
     * done in two steps. Firstly they are created during parsing a vertex but
     * their target vertex is set to null. Once all vertices are parsed, proper
     * references to target vertex (of the edge) is set. We use help structure
     * that is map of edge to id of the vertex. Assuming:
     * https://github.com/douglascrockford/JSON-java
     *
     * @param readString
     *            String to be parsed
     * @return Initialized graph
     *//*
    func parseJsonString(readString: String) -> Graph {
        JSONObject jo = new JSONObject(readString);
        return parseJsonObject(jo);
    }
    
    public Graph<GHT, GHT> parseJsonStream(final InputStream stream) throws IllegalArgumentException {
        try {
            JSONTokener tokener = new JSONTokener(stream);
            JSONObject js = new JSONObject(tokener);
            return parseJsonObject(js);
        } catch (JSONException e) {
            throw new IllegalArgumentException(e);
        }
    }*/
    
    /**
     * Experimental method to parse graph in JSON. Initialization of edges is
     * done in two steps. Firstly they are created during parsing a vertex but
     * their target vertex is set to null. Once all vertices are parsed, proper
     * references to target vertex (of the edge) is set. We use help structure
     * that is map of edge to id of the vertex. Assuming:
     * https://github.com/douglascrockford/JSON-java
     *
     * @param jo
     *            JSONObject to be parsed
     * @return Initialized graph
     */
    func parseJsonObject(jo: String) throws -> Graph {
        do {
            // Init
            
            let parsedGraph = Graph()
            
            let data = try NSJSONSerialization.JSONObjectWithData(jo.dataUsingEncoding(NSUTF8StringEncoding)!, options: .AllowFragments)
            var rootDict = data as! [String : AnyObject]
            
            if (rootDict["type"] as! String != "Graph") {
                throw JsonVisitorError.GraphExpectedError
            }
            
            // Add vertices
            
            let verticesDict = rootDict["vertices"] as! [AnyObject]
            
            for vertex in verticesDict {
                let vert = vertex as! [String : AnyObject]
                
                if (vert["type"] as! String != "Vertex") {
                    throw JsonVisitorError.VertexExpectedError
                }
                
                do {
                    let v = try jsonObjectToVertex(vert)
                    parsedGraph.addVertex(v.id, vertex: v)
                } catch {}
            }
            
            // Fix edge targets
            
            let vertices = parsedGraph.getVertexDict()
            
            for v in vertices {
                let edges: [Edge] = v.1.getEdges(Direction.Undefined)!
                
                for edge in edges {
                    let edgeData = edge.data as! DictionaryElementData
                    
                    if let targetId = edgeData.d[JsonVisitor.EDGE_TARGET_KEY] {
                        if let targetV = vertices[try targetId.l()] {
                            edge.sideB = targetV
                            
                            // Add missing incoming edge to vertex
                            if (targetV.getEdges(Direction.Undefined)!.isEmpty || !(targetV.getEdges(Direction.Undefined)!.contains(edge))) {
                                targetV.addEdge(edge)
                            }
                        }
                    }
                    
                    if let sourceId = edgeData.d[JsonVisitor.EDGE_SOURCE_KEY] {
                        if let sourceV = vertices[try sourceId.l()] {
                            edge.sideA = sourceV
                        }
                    }
                    
                    if (edgeData.d[JsonVisitor.EDGE_SOURCE_KEY] == nil || edgeData.d[JsonVisitor.EDGE_TARGET_KEY] == nil) {
                        v.1.removeEdgeOnBothSides(edge)
                    }
                    
                    edgeData.removeData(JsonVisitor.EDGE_SOURCE_KEY)
                    edgeData.removeData(JsonVisitor.EDGE_TARGET_KEY)
                }
            }
            
            return parsedGraph
        } catch {
            throw JsonVisitorError.GraphConversionError
        }
    }
    
    public func jsonObjectToVertex(jo: [String : AnyObject]) throws -> Vertex {
        if (jo["type"] as! String != "Vertex") {
            throw JsonVisitorError.VertexExpectedError
        }
        
        // Data directly attached to vertex
        
        let v = Vertex()
        v.id = UInt64(String(jo["id"]!))!
        
        let vData = jo["data"] as! [String : AnyObject]
        v.data = jsonObjectToDictionaryElementData(vData)
        
        // Edges
        
        if let edges: [AnyObject]? = jo["edges"]! as? [AnyObject] {
            if (edges != nil) {
                for edge in edges! {
                    let eData = edge as! [String : AnyObject]
                    v.addEdge(try jsonObjectToEdge(eData, source: v))
                }
            }
        }
        
        return v
    }
    
    func jsonObjectToEdge(jo: [String : AnyObject], source: Vertex) throws -> Edge {
        if (jo["type"] as! String != "Edge") {
            throw JsonVisitorError.EdgeExpectedError
        }
        
        let eId = UInt64(String(jo["id"]!))!
        let targetId = UInt64(String(jo["target"]!))!
        let data = jo["data"] as! [String : AnyObject]
        
        let dictData = jsonObjectToDictionaryElementData(data)
        let edge: Edge = try Edge(sideA: source, sideB: source, direction: .Forward, data: dictData)
        edge.id = eId
        
        (edge.data as! DictionaryElementData).updateData(JsonVisitor.EDGE_SOURCE_KEY, value: PrimitiveData(l: source.id))
        (edge.data as! DictionaryElementData).updateData(JsonVisitor.EDGE_TARGET_KEY, value: PrimitiveData(l: targetId))
        
        return edge
    }
    
    func jsonObjectToDictionaryElementData(jo: [String : AnyObject]) -> DictionaryElementData {
        let dict = DictionaryElementData()
        
        for key in jo.keys {
            do {
                dict.updateData(key, value: try PrimitiveData(o: jo[key]!))
            } catch {}
        }
        
        return dict
    }
}
