package dlx;

import java.io.IOException;

import org.apache.giraph.graph.BasicComputation;
import org.apache.giraph.graph.Vertex;
import org.apache.hadoop.io.DoubleWritable;
import org.apache.hadoop.io.FloatWritable;
import org.apache.hadoop.io.LongWritable;
import org.apache.log4j.Logger;

public class FirstGraph extends BasicComputation<LongWritable, DoubleWritable, FloatWritable, DoubleWritable> {
	protected static Logger log = Logger.getLogger(FirstGraph.class);
	
	
	@Override
	public void compute(
			Vertex<LongWritable, DoubleWritable, FloatWritable> vertex,
			Iterable<DoubleWritable> messages) throws IOException {

		log.info("Super step: " + getSuperstep() + ": current vertex: " + vertex.toString());
		
		vertex.voteToHalt();
	}

}
