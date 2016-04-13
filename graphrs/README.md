# **graphrs**
graphrs is a project to make graphr, which is implemented in Java, available for Swift projects.
Currently graphrs is including libraries for OS X as well as iOS.

A port for [Linux Swift](https://swift.org/) is planned but this version of Swift isn't very stable and managing larger projects isn't very easy yet.

## *Getting started*
Using graphrs in your project is fairly easy using Xcode. Just insert the library into your project by clicking on the target you want to use graphrs with. The adding the **graphrs.framework** to the **Embedded Binaries** tab by clicking on the small plus in the left bottom of the tab.

In the source code you want to use graphrs in, just import the module by adding this line:
<pre><code>import graphrs</code></pre>

Now you can use the power of graphrs.

## ***Usage***
### *Graph creation*
graphrs is using a factory pattern to create objects, so just use one of the following factory classes:

 - <code>GraphFactory</pre>
 - <code>VertexFactory</pre>
 - <code>EdgeFactory</pre>
 - <code>PrimitiveDataFactory</pre>

### *Running agents*
For running agents on the graph you just have to create one of the predefined <code>AgentPopulator</code>s. After that a <code>AgentManager</code> have to be initialized by providing a <code>Graph</code> to run on, the populator created before and a <code>Direction</code> to be considered when running the graph.

To start the process just call the function <code>runProcessing(numberOfBulkSteps: UInt64)</code> by defining the number of steps the agents should make in graph.