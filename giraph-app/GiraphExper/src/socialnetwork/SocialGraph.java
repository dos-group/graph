package socialnetwork;

import java.io.IOException;

import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.giraph.edge.Edge;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.log4j.Logger;

public class SocialGraph extends BasicComputation<LongWritable, HashWritable, HashWritable, DoubleWritable> {
	protected static Logger log = Logger.getLogger(SocialGraph.class);
	
	
	@Override
	public void compute(
			Vertex<LongWritable, HashWritable, HashWritable> vertex,
			Iterable<DoubleWritable> messages) throws IOException {

		// Basic log at a vertex
		log.info("At: super step " + getSuperstep() + " vertex " + vertex.toString());
				
		
		// Send message with random number along each outgoing
		// edge.
		if (getSuperstep() == 0) {
			for (Edge<LongWritable, HashWritable> edge : vertex.getEdges()) {
				double randomNumber = Math.random();
				sendMessage(edge.getTargetVertexId(), 
					new DoubleWritable(randomNumber));
				log.info("Sent random number " + randomNumber 
						+ " at vertex " + vertex.toString());
			}
		}
		
		// Just log message you received.
		for (DoubleWritable message : messages) {
			log.info("Got random number " + message.get() 
					+ " at vertex " + vertex.toString());
		}
	
		vertex.voteToHalt();
	}

}
